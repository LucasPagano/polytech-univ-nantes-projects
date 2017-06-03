package sendMail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

	private String username = "projet.javamail@gmail.com";
	private String password = "javamail";
	private Session session;

	public SendMailTLS() {
		this.session = Session.getInstance(sendMail.SendProperties.getProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

	}
/**
 * Envoie un mail
 * @param to Adresse destinataire
 * @param subjet Sujet du mail
 * @param content Contenu
 */
	public void send(String to, String subjet, String content) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("projet.javamail@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subjet);
			message.setText(content);

			Transport.send(message);

			System.out.println("Message sent\n\n");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}