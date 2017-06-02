package serverCommunication;

import java.util.TimerTask;

import parser.Parser;

/**
 * Class used to collect the new email
 * @author Wilson Vernard
 */
public class GetNewMails extends TimerTask {
	String username;
	String password;
	Parser parser;

	public GetNewMails(String username, String password, Parser parser) {
		this.username = username;
		this.password = password;
		this.parser = parser;
	}

	@Override
	public void run() {
		// Call method fetch and get unseen mails
		this.parser.setReadOnly(false);
		MailFetcher.fetch(username, password, parser, "notSeen");
	}
}
