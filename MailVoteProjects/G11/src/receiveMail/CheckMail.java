package receiveMail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

public class CheckMail {
	public static Flags DELETE = new Flags("DELETE");
	public static Flags DONE = new Flags("DONE");

	private String[] info = { "projet.javamail@gmail.com", "javamail" };
	public Session session;
	private Store store;
	private Folder inbox;

	private Flags marque;
	// private Flags
	private boolean isFlag = false;
	private ArrayList<Message> messages = null;
	private int messageNumber = 0;
	private int stateMessage = 0;

	
	/**
	 * Initialise l'élément servant à la réception de mail
	 * @param flag Drapeau posé sur chaque mail lu pour éviter de le lire deux fois.
	 * @throws MessagingException
	 */
	public CheckMail(Flags flag) throws MessagingException {
		this.marque = flag;
		this.session = Session.getInstance(ReceiveProperties.getProperties());
		this.store = this.session.getStore("imap"); // Utile pour lire les mails
		this.store.connect("imap.gmail.com", this.info[0], this.info[1]);
		inbox = store.getFolder("inbox");
		inbox.open(Folder.READ_WRITE);
		try {
			this.setMessageList();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retourne le dossier inbox
	 * @return
	 */
	public Folder getFolder() {
		return this.inbox;
	}

	private Flags getFlag() {
		return this.marque;
	}

	private void setMessageList() throws MessagingException, IOException {
		this.messages = new ArrayList<Message>(Arrays.asList(this.inbox.getMessages()));
		this.messageNumber = this.inbox.getMessageCount();

	}
/**
 * Trasforme un message en ListeChainée de chaine de caractères
 * @param msg Message a transformer
 * @return L'objet du message est la première case du tableau
 * @throws MessagingException
 * @throws IOException
 */
	public static ArrayList<String> getChaineMsg(Message msg) throws MessagingException, IOException {
		// System.out.println(msg.getContent());
		ArrayList<String> lignes = null;
		try {
			BodyPart bp = ((Multipart) msg.getContent()).getBodyPart(0);

			lignes = new ArrayList<String>(Arrays.asList(bp.getContent().toString().split("\n")));
		} catch (Exception e) {
//			e.printStackTrace();
			lignes = new ArrayList<String>(Arrays.asList(((String) msg.getContent()).split("\n")));
		}
		lignes.add(0, msg.getSubject());
		lignes.add(0, getAddressToString(msg.getFrom()));
		return lignes;
	}

	/**
	 * Fonction retournant le prochain message a chaque appel. Apres un
	 * lancement de l'application renvoie le mail le plus ancien de la boite de
	 * récéption, après un second lancement renvoi le 2ème plus ancien ...
	 * 
	 * @return Message suivant dans la boite de réception
	 * @throws MessagingException
	 * @throws IOException
	 * @throws NoNewMessage
	 *             Exception levée si il n'y a pas de nouveau message
	 */
	public Message getMail() throws MessagingException, IOException, NoNewMessage {
		if ((this.stateMessage < this.messageNumber) && (this.messageNumber != 0)) {
			Message msg = this.messages.get(this.stateMessage++);
			return msg;
			
		} else {
			if (this.isThereNewMsg()) {
				this.setMessageList();
				return this.getMail();
			}
		}
		throw new NoNewMessage();
	}

	/**
	 * Fonction retournant le prochain message a chaque appel. Apres un
	 * lancement de l'application renvoie le mail le plus ancien de la boite de
	 * réception, après un second lancement renvoi le 2ème plus ancien ...
	 * 
	 * @param putFlag
	 *            Si vrai pose un flag unique sur chaque mail lu, si faux n'en
	 *            pose pas
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 * @throws NoNewMessage
	 */
	public Message getMail(boolean putFlag) throws MessagingException, IOException, NoNewMessage {
		if ((this.stateMessage <= this.messageNumber) && (this.messageNumber != 0)) {
			Message msg = this.messages.get(this.stateMessage++);

			// this.setFlags(msg, DONE);
			if (putFlag)
				this.setFlags(msg, this.getFlag());
			return msg;
		} else {
			if (this.isThereNewMsg()) {
				this.setMessageList();
				return this.getMail(putFlag);
			}
		}
		throw new NoNewMessage();
	}
	
	
	
	

	public void setFlags(Message msg, Flags flg) throws MessagingException {
		msg.setFlags(flg, true);
	}

	/**
	 * Fonction a appeler systématiquement après getMail() Fonction a appeler
	 * pour associer un numéro de run a un mail, fct essentielle pour pouvoir
	 * supprimer les mail associés a un run lorsque ce dernier est terminé
	 * 
	 * @param msg
	 *            Message renvoyé par la fonction getMail
	 * @param numRun
	 *            Numéro de run au quel le message fait réference
	 * @throws MessagingException
	 */
	public void setNumRunOnMsg(Message msg, int numRun) throws MessagingException {
		Flags flg = new Flags("NRun_" + Integer.toString(numRun));
		this.setFlags(msg, flg);
	}

	private boolean isThereNewMsg() throws MessagingException {
		return this.messageNumber < inbox.getMessageCount();
	}

	public static String getAddressToString(Address[] addresses) {
		String adr = InternetAddress.toString(addresses);
		adr = adr.split("<")[1];
		adr = adr.split(">")[0];
		return adr;

	}
/**
 * Termine la session en cours
 * @throws MessagingException
 */
	public void closeSession() throws MessagingException {
		this.inbox.close(true);
		this.store.close();
	}
}
