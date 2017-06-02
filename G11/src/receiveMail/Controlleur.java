package receiveMail;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

import com.sun.mail.imap.IdleManager;

import parser.Parser;

/**
 * Controle l'arrivée de nouveaux mails et les traite (non fonctionnel)
 * 
 * @author Felix
 *
 */
public class Controlleur {
	public IdleManager idleManager;
	public Folder inbox;

	public Controlleur(CheckMail checkMail, Parser parser) {
		ExecutorService es = Executors.newCachedThreadPool();
		// IdleManager idleManager=null;
		try {
			inbox = checkMail.getFolder();

			ActionMail actionMail = new ActionMail(checkMail, parser);

			inbox.addMessageCountListener(actionMail);

			idleManager = new IdleManager(checkMail.session, es);
			System.out.println("Lancement boucle !");
			idleManager.watch(inbox);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Vérifie l'activité du dossier inbox. Affiche "nouveau mail !" dans la
	 * sortie standard a l'arrivé d'un mail Affiche "Mail supprimé !" lors de la
	 * suppression d'un mail
	 * 
	 * @author Felix
	 *
	 */
	public class ActionMail implements MessageCountListener {
		CheckMail checker;
		Parser parser;

		public ActionMail(CheckMail _checker, Parser _parser) {
			checker = _checker;
			parser = _parser;
		}

		@Override
		public void messagesAdded(MessageCountEvent arg0) {

			System.out.println("Nouveau mail !");
			try {
//				System.out.println("avant msg");
				Message msg = checker.getMail();
				System.out.println("apres message");
				// parser.newMail(msg);

			} catch (MessagingException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();

			} catch (NoNewMessage e) {
				e.printStackTrace();
			}
			try {
				idleManager.watch(inbox);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void messagesRemoved(MessageCountEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Mail supprimé");
		}

	}
}
