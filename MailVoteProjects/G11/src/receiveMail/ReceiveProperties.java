package receiveMail;

import java.util.Properties;
/**
 * Fichier regroupant les properties
 * @author Felix
 *
 */
public class ReceiveProperties {
	public static Properties getProperties(){
		Properties props = new Properties();
		props.setProperty("mail.imap.ssl.enable", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.imap.usesocketchannels","true");
		
		props.put("mail.imap.ssl.trust", "*");
		
		//props.put("mail.imap.ssl.socketFactory", sf);
		return props;
	}
}
