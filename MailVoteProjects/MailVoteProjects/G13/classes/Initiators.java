package classes;

import java.io.IOException;
import java.io.Serializable;

import envoie_reception_mails.SendEmail;

public class Initiators extends Person implements Serializable{

	public Initiators(int id, String name, String surname, String mailAddress) {
		super(id, name, surname, mailAddress);
	}

	public static Run createRun(String name, String surname, String address) throws IOException {
		Run run = new Run(address);
		int idInitiator = run.generateIDPerson();
		Initiators i = new Initiators(idInitiator, name, surname, address);
		run.addInitiator(i);

		SendEmail acknowledge = new SendEmail();
		String body = "run id" + run.getId() + "\nVoici votre ID Initiator pour ce RUN : " + i.getId()
				+ "\n Vous trouverez ci dessous toutes les commandes a votre disposition\n\n"
				+ DefaultMessages.helpBody;
		try {
			acknowledge.sendMail(i.getAddress(), "acknowledge", body);
		} catch (Exception e) {
		}

		return run;
	}

	public void addRunName(Run run, String name) {
		run.addName(name);
	}

	public static void addRunDescription(Run run, String description) {
		run.addDescription(description);
	}

	public static void addRunTokenNumber(Run run, int tokenNumber) {
		run.tokenCount(tokenNumber);
	}

	public static void addRunAddress(Run run, String address) {
		run.addAddress(address);
	}

	public static void addRunChoice(Run run, String choice) {
		run.addChoice(choice);
	}

	public static void removeRunChoice(Run run, Integer choice) {
		run.removeChoice(choice);
	}

	public static void makeRunArrayVote(Run run) {
		run.makeArrayVote();
	}

	static void addRunClient(Run run, String name, String surname, String address) {
		int idClient = run.generateIDPerson();
		Client nouveau = new Client(idClient, name, surname, address);
		run.addClient(nouveau);
	}

	static void addRunInitiator(Run run, String name, String surname, String address) {
		int idInitiator = run.generateIDPerson();
		Initiators nouveau = new Initiators(idInitiator, name, surname, address);
		run.addInitiator(nouveau);
	}

	static void removeRunClient(Run run, int idClient) {
		run.removeClient(idClient);
	}

	void removeRunInitiator(Run run, int idInitiator) {
		run.removeInitiator(idInitiator);
	}

	static void sendInvitation(Run run) {
		run.sendInvitation();
	}

	static void sendDecision(Run run) {
		run.sendDecision();
	}

	public void voteRun(Run run, Client client, Choice choice, int nbToken) {
		this.helpMessages();
	}

}
