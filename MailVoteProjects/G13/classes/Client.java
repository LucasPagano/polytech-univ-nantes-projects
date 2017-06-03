package classes;

import java.io.Serializable;

public class Client extends Person implements Serializable{

	public Client(int id, String name, String surname, String address) {
		super(id, name, surname, address);
	}

	public void addRunName(Run run, String name) {
		this.helpMessages();
	}

	public void addRunDescription(Run run, String description) {
		this.helpMessages();
	}

	public void addRunTokenNumber(Run run, int tokenNumber) {
		this.helpMessages();
	}

	public void addRunAddress(Run run, String address) {
		this.helpMessages();
	}

	public void addRunChoice(Run run, Choice choice) {
		this.helpMessages();
	}

	public void removeRunChoice(Run run, Choice choice) {
		this.helpMessages();
	}

	public void makeRunArrayVote(Run run) {
		this.helpMessages();
	}

	void addRunClient(Run run, String name, String surname, String address) {
		this.helpMessages();
	}

	void addRunInitiator(Run run, String name, String surname, String address) {
		this.helpMessages();
	}

	void removeRunClient(Run run, int idClient) {
		this.helpMessages();

	}

	void removeRunInitiator(Run run, int idInitiator) {
		this.helpMessages();
	}

	void sendInvitation(Run run) {
		this.helpMessages();
	}

	void sendDecision(Run run) {
		this.helpMessages();
	}

	public static void voteRun(Run run, Client client, Choice choice, int nbToken) {
		run.vote(client, choice, nbToken);
	}

}
