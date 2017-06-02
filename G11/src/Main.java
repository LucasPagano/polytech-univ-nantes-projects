import java.io.IOException;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;

import parser.Parser;
import receiveMail.CheckMail;
import receiveMail.Controlleur;
import receiveMail.NoNewMessage;

public class Main {
	/**
	 * Vérifie les mail sur la boite de réception, est lancé dans un premier
	 * temps, avant "boucle"
	 * 
	 * @param CM
	 *            La variable doit être initialisée avant d'être utilisée
	 * @param parser
	 *            La variable doit être initialisée avant d'être utilisée
	 */
	public static void lancement(CheckMail CM, Parser parser) {
		System.out.println("Lancement application");
		boolean continuer = true;
		while (continuer) {
			try {

				Message msg = CM.getMail();
				// System.out.println("mail : " + msg.getSubject());
				parser.newMail(msg);

			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoNewMessage e) {
				// TODO Auto-generated catch block
				continuer = false;
			}
		}
	}

	/**
	 * Surveille en continu la boite de réception dans l'attente d'un nouveau
	 * mail à triater
	 * 
	 * @param CM
	 * @param parser
	 */
	public static void boucle(CheckMail CM, Parser parser) {
		new Controlleur(CM, parser);
	}

	public static void main(String[] args) {
		Flags flag_session = new Flags(Long.toString(System.currentTimeMillis()));
		CheckMail checker = null;
		Parser parser = null;
		try {
			checker = new CheckMail(flag_session);
			parser = new Parser(checker);
//			 Main.lancement(checker, parser);
			Main.boucle(checker, parser);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
