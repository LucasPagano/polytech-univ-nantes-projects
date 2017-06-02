package serverCommunication;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import util.Email;

/**
 * class used to send email
 * @author Wilson Vernard
 */
public class SendMail {

	/**
	 * Send a list of email with the given account
	 * @param mailList the list of email to send
	 */
	public static void sendMail(List<Email> mailList) {
		final String username = "john.doe.mailvote@gmail.com";
		final String password = "mailvote";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			for (Email mail : mailList) {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getFrom().getAddress()));
				message.setSubject(mail.getSubject());
				message.setText(mail.getBody());
				Transport.send(message);
			}

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Send an email with the given account
	 * @param mail the email to send
	 */
	public static void sendMail(Email mail) {
		final String username = "john.doe.mailvote2@gmail.com";
		final String password = "mailvote";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getFrom().getAddress()));
			message.setSubject(mail.getSubject());
			message.setText(mail.getBody());
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
