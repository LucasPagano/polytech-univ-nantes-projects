package classes;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.mail.Message;

import Parser.Parser;
import envoie_reception_mails.SendEmail;

public class TakeAction {

	public void makeAction(Message mail) throws Exception {
		String filePath = new File("").getAbsolutePath();
		String serpath = filePath.concat("/serfiles/runmaps.ser");
		String serdir = filePath.concat("/serfiles");
		RunMap runmap = null;
		try {
			FileInputStream fileIn = new FileInputStream(serpath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
				try {
					runmap = (RunMap) in.readObject();
				} catch (EOFException exc) {
					System.out.println("end of file");
					in.close();
					fileIn.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Class not found");
				}
		} catch (IOException i) {
			i.printStackTrace();
			System.out.println("no file");
			runmap = new RunMap();
			try {
				File dir = new File(serdir);
				boolean b = dir.mkdir();
				System.out.println(b);
				File ser = new File(serpath);
				FileOutputStream fileout = new FileOutputStream(ser);
				ObjectOutputStream out = new ObjectOutputStream(fileout);
				out.writeObject(runmap);
				System.out.println("file created");
				out.close();
				fileout.close();
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
		
		System.out.println(runmap.isEmpty());
		Parser mailParser = new Parser(mail);
		
		// faut récupérer le run
		
		// puis la personne qui fait les commandes

		if (!mailParser.mailValid(mailParser.getContents())&&!mailParser.mailValid(mailParser.getSubject())) {
			System.out.println("nottt");
			this.helpMessages(mailParser.getSender());
		}
		
		else if (mailParser.getCommand() == "CREATERUN") {
			Run run = Initiators.createRun(mailParser.getSendername(), mailParser.getSendersurname(),
					mailParser.getSender());
			runmap.put(run.getId(), run);
			String address = mailParser.getSender();
			Initiators.addRunTokenNumber(run, mailParser.getTokencount());
			Initiators.addRunInitiator(run, mailParser.getNamefromAddress(address),
					mailParser.getSurnamefromAddress(address), mailParser.getAddressfromAddress(address));
			Initiators.addRunDescription(run, mailParser.getRundescription());
			saveMap(runmap,serpath);
		}

		else if (mailParser.getCommand() == "ADDCLIENT") {
			for (String address : mailParser.getClienttab()) {
				System.out.println(address);
				Initiators.addRunClient(runmap.get(mailParser.getRunID()), mailParser.getNamefromAddress(address),
						mailParser.getSurnamefromAddress(address), mailParser.getAddressfromAddress(address));
				saveMap(runmap,serpath);
			}
		}

		else if (mailParser.getCommand() == "DELCLIENT") {
			for (Integer idClient : mailParser.getClientdelidx()) {
				Initiators.removeRunClient(runmap.get(mailParser.getRunID()), idClient);
				saveMap(runmap,serpath);
			}
		}

		else if (mailParser.getCommand() == "ADDCHOICE") {
			for (String choice : mailParser.getAddchoices()) {
				Initiators.addRunChoice(runmap.get(mailParser.getRunID()), choice);
				System.out.println(runmap.get(mailParser.getRunID()).getChoice());
				saveMap(runmap,serpath);
			}
		}

		else if (mailParser.getCommand() == "DELCHOICE") {
			for (Integer idChoice : mailParser.getClientdelchoix()) {
				Initiators.removeRunChoice(runmap.get(mailParser.getRunID()), idChoice);
				saveMap(runmap,serpath);
			}
		}

		else if (mailParser.getCommand() == "SENDINVITATION") {
			Initiators.sendInvitation(runmap.get(mailParser.getRunID()));
			saveMap(runmap,serpath);
		}

		else if (mailParser.getCommand() == "SENDDECISION") {
			Initiators.sendDecision(runmap.get(mailParser.getRunID()));
			saveMap(runmap,serpath);
		}

		else if (mailParser.getCommand() == "VOTE") {
			Iterator it = mailParser.getVotes().entrySet().iterator();
			Run run = runmap.get(mailParser.getRunID());
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();

				Client.voteRun(run, run.findClient(mailParser.getAddressfromAddress(mailParser.getSender())),
						run.findChoice((int) pair.getKey()), (int) pair.getValue());
			}
			saveMap(runmap,serpath);
		} else {
			this.helpMessages(mailParser.getSender());
			System.out.println("t");
		}
		System.out.println("command: "+mailParser.getCommand());
		System.out.println("run id :"+mailParser.getRunID());
	}

	public void helpMessages(String address) {
		SendEmail helpMail = new SendEmail();
		try {
			helpMail.sendMail(address, DefaultMessages.helpSubject, DefaultMessages.helpBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveMap(RunMap runmap,String serpath){
		try {
			FileOutputStream fileout = new FileOutputStream(serpath);
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(runmap);
			fileout.close();
			out.close();
			System.out.println("saved");
		} catch (IOException io) {
			System.out.println("save failed");
			io.printStackTrace();
		}
	}

}
