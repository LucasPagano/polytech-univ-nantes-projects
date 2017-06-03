import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import receiveMail.CheckMail;
import receiveMail.NoNewMessage;
import receiveMail.ReceiveProperties;
/**
 * Afficher tous les drapeaux de tous les mails dans la boite "inbox"
 * @author Felix
 *
 */
public class DisplayFlag {
	public static void main(String[] args) {
		boolean go = true;

		Flags flag_session = new Flags(Long.toString(System.currentTimeMillis()));
		Message msg;
		CheckMail CM = null;
		try {
			CM = new CheckMail(flag_session);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (go) {
			try {

				msg = CM.getMail(false);
				
//				if(Arrays.asList(msg.getFlags().getSystemFlags()).contains(CheckMail.DONE))
//					System.out.println("coucou gnette");
				for (String str : msg.getFlags().getUserFlags()) {
					System.out.println(str);
				}
				
				System.out.println("mail "+Arrays.asList(msg.getFlags().getUserFlags()).contains("DONE"));
				System.out.println("--NewMail--");
			} catch (Exception e) {
				go = false;
			}

		}

	}
}