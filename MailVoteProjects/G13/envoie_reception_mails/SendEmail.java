package envoie_reception_mails;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

	public void sendMail(String address, String subject, String body) throws Exception {

		String smtpHost = "smtp.gmail.com";
		String from = "mailvotejavaproject@gmail.com";
		String to = address;
		String username = "mailvotejavaproject@gmail.com"; // mettre son
															// username
		String password = "mailvote1"; // mettre son mot de passe

		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");

		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props);
		session.setDebug(false);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setText(body);

		Transport tr = session.getTransport("smtp");
		tr.connect(smtpHost, username, password);
		message.saveChanges();

		// tr.send(message);
		/**
		 * Genere l'erreur. Avec l authentification, oblige d utiliser
		 * sendMessage meme pour une seule adresse...
		 */

		tr.sendMessage(message, message.getAllRecipients());
		tr.close();

	}
}