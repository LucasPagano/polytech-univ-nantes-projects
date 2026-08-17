package server;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Flags;
import javax.mail.search.FlagTerm;
public class MailServer implements Server{
	
		final String username;
		final String password;
		private Properties props;//smtp
		private Properties props2;//imap
		public Session session;

		private PrintStream originalOut;
	    private ByteArrayOutputStream collectedOut;
	    Store store;
	
		public MailServer(String username, String password) {
			this.username = username;
			this.password = password;
			//connexion serveur d'envoi
			props = new Properties();
	  		props.put("mail.smtp.auth", "true");
	  		props.put("mail.smtp.starttls.enable", "true");
	  		props.put("mail.smtp.host", "smtp.gmail.com");
	  		props.put("mail.smtp.port", "587");
	  		session = Session.getInstance(props,
	  		new javax.mail.Authenticator() {
	  			protected PasswordAuthentication getPasswordAuthentication() {
	  				return new PasswordAuthentication(username, password);
	  			}
	  		}); 

	  		// connexion imap
	  		props2 = System.getProperties();
			props2.setProperty("mail.store.protocol", "imap");

			session.setDebug(false);
			try {
				store = Session.getInstance(props2, null).getStore("imaps");
			} catch (NoSuchProviderException e) {
				System.out.println("erreur à la connexion imap");
				e.printStackTrace();
			}
			try {
				store.connect("imap.gmail.com", username, password);
			} catch (MessagingException e) {
				System.out.println("erreur à la connexion imap");
				e.printStackTrace();
			}
		}
		public void clearInbox(){

		}

		public ArrayList<Email> readInbox() {

	    	ArrayList<Email> mails  = new ArrayList<Email>();
	    	//collectSystemOut();
			Folder inbox = null;
			try {
				inbox = store.getFolder("Inbox");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				inbox.open(Folder.READ_WRITE);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message messages[] = null;
			try {
				messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Message message:messages) {	
				mails.add(new Email(message));
			}
			//restoreOriginalSystemOut();
	
			return mails;
		}
		public List<String> foldersList(String foldername) throws Exception{
			ArrayList<String> names = new ArrayList<String>();
			
			for(Folder f: store.getFolder(foldername).list()){
				names.add(f.getName());
			}
			return names;
		}

		public ArrayList<Email> readFolder(String nom) throws MessagingException, IOException {
	    	ArrayList<Email> mails  = new ArrayList<Email>();
	    	//collectSystemOut();
			Folder inbox = store.getFolder(nom);
			inbox.open(Folder.READ_WRITE);
			Message messages[] = inbox.getMessages();
			System.out.println("Total :" + messages.length);
			for(Message message:messages) {	
				mails.add(new Email(message));
			}
			inbox.close(false);
			//restoreOriginalSystemOut();
			return mails;
		}
		
	    
		public void collectSystemOut() {
	        originalOut = System.out;
	        collectedOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(collectedOut));
	    }

	    public void restoreOriginalSystemOut() {
	        System.setOut(originalOut); //Pour de nouveau pouvoir afficher nos print sur la sortie standard initiale
	    }

		

		@Override
		public void send(Email m) {
			// TODO Auto-generated method stub
			//collectSystemOut(); //Pour ne pas afficher sur notre sortie les prints que font les fonctions suivantes
	  		try {
	 			//System.out.println("to " + m.to);
	  			Transport.send(emailToMessage(m));
	  			//restoreOriginalSystemOut();
	  			//System.out.println("Mail sent");
	  		} catch (MessagingException e) {
	  			e.printStackTrace();
	  		}
			
		}

		@Override
		/*  
		 * Note that in Gmail folder hierarchy is not maintained.  
		 * */  
		public boolean createFolder(String folderName)   
		{   
		    boolean isCreated = true;   

		    try  
		    {   			
		    	Folder parent = store.getDefaultFolder();

		        Folder newFolder = parent.getFolder(folderName);   
		        isCreated = newFolder.create(Folder.HOLDS_MESSAGES);   
		        //System.out.println("created: " + isCreated);   

		    } catch (Exception e)   
		    {   
		        System.out.println("Error creating folder: " + e.getMessage());   
		        e.printStackTrace();   
		        isCreated = false;   
		    }   
		    return isCreated;   
		}
		
		public Message emailToMessage(Email m){
			Message message = null;
			if(m.id == -1){

				try {
					message = new MimeMessage(this.session);
					message.setFrom(new InternetAddress(m.from));

					for(int i = 0; i < m.to.size(); i++){
						message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(m.to.get(i)));
					}

					message.setSubject("mailvote");
					MimeBodyPart content = new MimeBodyPart();
					content.setText(m.listToString(m.body));
					MimeMultipart multipart = new MimeMultipart();
					multipart.addBodyPart(content);
					message.setContent(multipart);

					//Message ms[] = {message};
					//store.getFolder("[Gmail]/Brouillons").appendMessages(ms);
				} 
				catch (MessagingException ex) {
					ex.printStackTrace();
				}
			}
			else{
				try{
					Folder from = store.getFolder(m.foldername);
					from.open(Folder.READ_WRITE);
					message = from.getMessage(m.id);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			/*
				
*/
			return message;
		}

		@Override
		public void move(Email mail, String foldername) {
			// TODO Auto-generated method stub
			Folder destination =  null;
			Message[] m = new Message[1];
			Folder from = null;

			m[0] = emailToMessage(mail);

			try {
				//System.out.println(mail.foldername);
				//from = store.getFolder(mail.foldername);
				destination = store.getFolder(foldername);
				try {
					destination.appendMessages(m);
				} 
				catch (MessagingException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (MessagingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}//[Gmail]/Drafts
		    

			
		}
		
		public String getAdress(){
			return "polymailvote@gmail.com";
		}
}

