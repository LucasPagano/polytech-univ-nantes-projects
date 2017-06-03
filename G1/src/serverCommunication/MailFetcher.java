package serverCommunication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;

import parser.Parser;
import util.Email;

/**
 * Class used to read the email on the IMAP server
 * 
 * @author nathan
 */
public class MailFetcher {

	/**
	 * Methods which reads mails from a folder
	 * 
	 * @param user
	 *            the name of the mail account
	 * @param password
	 *            the password of the mail account
	 * @param parser
	 *            the parser to use
	 * @param seen
	 *            if value is all reads all the mails, else it reads only the unseen ones
	 */
	public static void fetch(String user, String password, Parser parser, String seen) {

		ArrayList<Message> messages = new ArrayList<Message>();
		// create properties field
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		try {
			Session emailSession = Session.getDefaultInstance(props, null);

			// create the store object and connect with the server
			Store store = emailSession.getStore("imaps");

			store.connect("imap.gmail.com", user, password);

			// create the important folder objects
			// inbox will be opened later
			Folder inbox = store.getFolder("INBOX");
			Folder runFolder = store.getFolder("RUN0");
			runFolder.open(Folder.READ_WRITE);

			// retrieve the messages from the folder in an arraylist
			List<Folder> foldersList = new ArrayList<Folder>();
			foldersList.add(runFolder);

			if (seen.equals("seen")) {

				MailFetcher.initRuns(runFolder, parser);

				Folder[] runFolders = store.getDefaultFolder().list();

				foldersList = new ArrayList<Folder>(Arrays.asList(runFolders));

				// Last folder is [Gmail]
				// No mail is received in there
				foldersList.remove(foldersList.size() - 1);

				for (Folder folder : foldersList) {
					folder.open(Folder.READ_WRITE);
					messages.addAll(Arrays.asList((folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), true)))));
				}

			} else {
				// We are in listening mode, and thus only need to listen to inbox
				inbox.open(Folder.READ_WRITE);
				foldersList.add(inbox);
				messages.addAll(Arrays.asList(inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false))));
			}

			// Parse mail
			for (Message message : messages) {
				Email mail;
				try {
					mail = MailFetcher.toMail(message);
					if (mail != null) {
						parser.parse(mail);
						if (!(mail.getAction().isMailTobeSaved())) {
							message.setFlag(Flags.Flag.DELETED, true);
						} else if (!(parser.isReadOnly())) {
							if (mail.getAction().isNewRun()) {

								// Create a new folder and move message inside the runFolder
								Folder newFolder = store.getFolder("RUN" + mail.getAction().getRunID());
								newFolder.create(Folder.HOLDS_MESSAGES);
								newFolder.open(Folder.READ_WRITE);
								foldersList.add(newFolder);

								try {
									MailFetcher.moveMessage(message, inbox, runFolder);
									// shouldn't happen
								} catch (MessagingException e) {
								}

							} else {
								// Get the existing folder and move message
								// inside
								Folder existingFolder = store.getFolder("RUN" + mail.getAction().getRunID());
								try {
									MailFetcher.moveMessage(message, inbox, existingFolder);
									// shouldn't happen
								} catch (MessagingException e) {
								}
							}
						}
						message.setFlag(Flags.Flag.SEEN, true);
					}
				} catch (MessagingException | IOException e) {
				}
			}

			// close the store and folder objects, as well as expunge deleted mails
			for (Folder folder : foldersList) {
				folder.close(true);
			}

			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Send to the parser all the mails from a folder Used for the folder with all the CREATERUN commands
	 * 
	 * @param runFolder
	 *            the folder to read
	 * @param parser
	 *            the parser to use
	 */
	private static void initRuns(Folder runFolder, Parser parser) {
		ArrayList<Message> createRunMessages = new ArrayList<Message>();
		try {
			createRunMessages.addAll(Arrays.asList(runFolder.getMessages()));

			for (Message message : createRunMessages) {
				Email mail;
				try {
					mail = MailFetcher.toMail(message);
					if (mail != null) {
						parser.parse(mail);
					}
					// Shouldn't happen
				} catch (IOException e) {
				}
			}

			// Shouldn't happen
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Move an email into another folder
	 * 
	 * @param message
	 *            the message to move
	 * @param source
	 *            the folder containing the email
	 * @param destination
	 *            the future folder of the email
	 * @throws MessagingException
	 */
	private static void moveMessage(Message message, Folder source, Folder destination) throws MessagingException {
		List<Message> tempList = new ArrayList<>();
		tempList.add(message);
		Message[] tempMessageArray = tempList.toArray(new Message[tempList.size()]);
		source.copyMessages(tempMessageArray, destination);
		// Set to deleted from source so it will be cleared on close

		source.setFlags(tempMessageArray, new Flags(Flags.Flag.DELETED), true);

	}

	/**
	 * This method transforms the message into an Email
	 * 
	 * @param m
	 *            the message to transform into an email
	 * @return the email created
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static Email toMail(Message m) throws MessagingException, IOException {
		Address[] from = null;
		String subject = null;
		String body = null;
		Email mail = null;

		from = m.getFrom();
		subject = m.getSubject();

		if (m.isMimeType("text/plain")) {
			body = m.getContent().toString();
		} else if (m.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) m.getContent();
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				if (bodyPart.isMimeType("text/plain")) {
					body = (String) bodyPart.getContent();
				}
			}
		}

		if ((from != null) && ((subject != null) || (body != null))) {
			mail = new Email((InternetAddress) from[0], subject, body);
		}
		return mail;

	}
}
