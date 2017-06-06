import server.*;
import java.util.*;
import main.*;

public class MailVote {
	
	private Server server;
	private DecisionAlgorithm algo;

	public MailVote() {
		System.setProperty("mail.mime.charset", "utf-8");
		server = new MailServer("polymailvote@gmail.com", "polytech3a");
		//server = new TestServer("/home/mok33/MailVote@test.com");
		algo = new Hungarian();

		server.createFolder("Runs");
		server.createFolder("Runs/Seeds");
		server.createFolder("Runs/Mails");
	}
	
	public void start() {
		for(Email m: server.readInbox()){
			if(m.subject.get(0).toLowerCase().contains(new String("mailvote"))){
				EmailHandler handler = new EmailHandler(m, algo, server, true);
				handler.interpret();
				//System.out.println(m.body);
			}
		}
		if(server instanceof TestServer){
			server.clearInbox();
		}
	}
	
	public static void main(String[] args){
		MailVote m = new MailVote();
		m.start();
	}
}