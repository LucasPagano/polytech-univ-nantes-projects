package sendMail;

import java.util.Properties;
/**
 * Properties de la boite d'envoie
 * @author Felix
 *
 */
public class SendProperties {
	public static Properties getProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		return props;
	}
}
