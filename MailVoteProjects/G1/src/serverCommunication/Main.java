package serverCommunication;

import java.util.Timer;

import parser.Parser;

public class Main {

	public static void main(String[] args) {
		String username = "john.doe.mailvote@gmail.com";// change accordingly
		String password = "mailvote";// change accordingly

		Parser parser = new Parser(true);
		MailFetcher.fetch(username, password, parser, "seen");

		// Periodically get all unseen mails and send them to the parser
		Timer t = new Timer();
		t.schedule(new GetNewMails(username, password, parser), 1000 * 2, 1000 * 2);

	}
}
