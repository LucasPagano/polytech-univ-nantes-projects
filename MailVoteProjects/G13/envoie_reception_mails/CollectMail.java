package envoie_reception_mails;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPMessage;

public class CollectMail {

	public Message[] collectMail() throws Exception {
		IMAPFolder folder = null;
		Store store = null;
		String subject = null;
		Flag flag = null;
		Message[] messages = null;

		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(false);
		store = session.getStore("imaps");
		store.connect("imap.gmail.com", "mailvotejavaproject@gmail.com", "mailvote1");

		folder = (IMAPFolder) store.getFolder("inbox");

		if (!folder.isOpen()) {
			folder.open(Folder.READ_WRITE);
			messages = folder.getMessages();
		}

		return messages;

	}

	public void delMail() throws Exception {
		IMAPFolder folder = null;
		Store store = null;
		String subject = null;
		Flag flag = null;
		Message[] messages = null;

		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);

		store = session.getStore("imaps");
		store.connect("imap.gmail.com", "mailvotejavaproject@gmail.com", "mailvote1");

		folder = (IMAPFolder) store.getFolder("inbox");

		if (!folder.isOpen()) {
			folder.open(Folder.READ_WRITE);
			messages = folder.getMessages();
		}
		for (Message m : messages) {
			m.setFlag(Flags.Flag.DELETED, true);
		}

		folder.close(true);
	}
}