package server;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.*;
import java.io.*;


import javax.mail.Address;
import javax.mail.BodyPart;
//import javax.mail.Message;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

public class Email {
	
	public List<String> body;
	public List<String> to;
	public List<String> subject;
	public String from;

	private PrintStream originalOut;
	private ByteArrayOutputStream collectedOut;
	public int id;
	public String foldername;

	public Email(){
		id = -1;
	}

	public Email(Message m) {
		//Initialisation des attributs de la classe
		initBody(m);
		initTo(m);
		initFrom(m);
		initSubject(m);
		this.id = m.getMessageNumber();
		this.foldername = m.getFolder().getName();
	}

	//Transforme le corps du message en une liste de string où chaque String représente une ligne
	//Initialisation de l'attribut body de la classe
	public void initBody(Message m) {
		String str = "";
		try {
			str = getText(m);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] commandTab = str.split(System.getProperty("line.separator"));
		this.body = new ArrayList<String>(Arrays.asList(commandTab));
	}
	private boolean textIsHtml = false;

    /**
     * Return the primary text content of the message.
     */
    private String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }
	
	//Retourne une chaîne de caractère contenant le corps du mails
	private String getTextFromMessage(Message message) throws Exception {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}
	
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception{
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + html + "\n" ;
			} else if (bodyPart.getContent() instanceof MimeMultipart){
				result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
			}
		}
		return result;
	}

	//Initialisation de l'attribut from de la classe
	private void initTo(Message message) {
		this.to = new ArrayList<String>();

		Address[] tos = new Address[1];
		try {
			//collectSystemOut();
			tos = message.getAllRecipients();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(tos != null){
			////restoreOriginalSystemOut();
			for (Address a:tos) {
				this.to.add(((InternetAddress)a).getAddress());
			}	
		}
	}

	//Initialisation de l'attribut from de la classe
	private void initFrom(Message message) {
		Address[] froms = new Address[1];
		try {
			//collectSystemOut();
			froms = message.getFrom();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//restoreOriginalSystemOut();
		this.from = ((InternetAddress) froms[0]).getAddress();
	}

	//Initialisation de l'attribut subject de la classe
	private void initSubject(Message message) {
		String subject;
		this.subject = new ArrayList<String>();
		try {
			subject = message.getSubject();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			subject = "";
		}
		this.subject.add(subject);
	}

	public String toString(){
		String acc = "";
		for(String ch: toArray()){
			acc += ch + "\n";
		}
		return acc;
	}

	public ArrayList<String> toArray(){
		ArrayList<String> mail = new ArrayList<String>();
		
		mail.add("FROM 1");
		mail.add(from);
		
		mail.add("TO " + to.size());
		mail.addAll(to);
		
		mail.add("SUBJECT " + subject.size());
		mail.addAll(subject);
		
		mail.add("BODY " + body.size());
		mail.addAll(body);
		
		return mail;
	}

	//Concatène les éléments d'une liste de chaîne de caractère en les séparant par un retour à la ligne
	public static String listToString(List<String> liste){
		String chaine ="";
		for(String cmd : liste){
			chaine += cmd + "\n";
		}
		return chaine;
	}

	public static Email stringsToEmail(List<String> mails){		
		
		int i = 0;
		mails.remove(0);
		String from = mails.get(0);
		mails.remove(0);
		ArrayList<String> to = new ArrayList<String>();
		int nbTo = Integer.parseInt(mails.get(0).split(" ")[1]);
		mails.remove(0);

		for(i = 0; i < nbTo; i++){
			to.add(mails.get(0));
			mails.remove(0);
		}
		ArrayList<String> subject = new ArrayList<String>();
		int nbSubject = Integer.parseInt(mails.get(0).split(" ")[1]);
		mails.remove(0);

		for(i = 0; i < nbSubject; i++){
			subject.add(mails.get(0));
			mails.remove(0);
		}
		ArrayList<String> body = new ArrayList<String>();
		int nbLineBody = Integer.parseInt(mails.get(0).split(" ")[1]);
		
		mails.remove(0);

		for(i = 0; i < nbLineBody; i++){
			body.add(mails.get(0));
			mails.remove(0);
		}
		
		Email mail = new Email();
		mail.from = from;
		mail.to = to;
		mail.subject = subject;
		mail.body = body;
		
		return mail;
	}

	//Accesseur de l'adresse mail de l'expéditeur du mail
	public String getFrom() {
		return from;
	}

	//Accesseur du sujet du mail
	public List<String> getSubject() {
		return subject;
	}
	
	//Accesseur de la liste
	public List<String> getBody() {
		return body;
	}
	//Accesseur de la liste des adresses mail des desinataires du mail
	public List<String> getTo() {
		return to;
	}


	public void collectSystemOut() {
		originalOut = System.out;
		collectedOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(collectedOut));
	}

	public void restoreOriginalSystemOut() {
        System.setOut(originalOut); //Pour de nouveau pouvoir afficher nos print sur la sortie standard initiale
    }
}
