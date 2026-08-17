package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;

import util.Email;
import util.RandomId;
import decision.DecisionMaker;
import decision.hungarian.HungarianDecision;
import exceptions.ChoiceNotFoundException;
import exceptions.UserNotFoundException;

/**
 * This class represents a run.
 * 
 * @author Maraval Nathan
 */
public class Run implements Cloneable {

	@Override
	public String toString() {
		return "Run of id " + id + " : \n  maxToken=" + maxToken + "\n  description is " + description
				+ "\n  choices are " + choices + "\n  clients are " + clients + "\n  initiators are " + initiators;
	}

	private double id;
	private int maxToken;
	private String description;
	private List<Choice> choices;
	private List<Client> clients;
	private List<Initiator> initiators;
	private String lastDecision;
	private RandomId randomId;
	

	/**
	 * @param id
	 *            the identifier of the run
	 * @param initiatorEmail
	 *            the e-mail of the original initiator
	 */
	public Run(long seed) {
		super();
		this.randomId = new RandomId(seed);
		this.id = this.randomId.generateNewId();
		this.maxToken = 0;
		this.description = "";
		this.choices = new ArrayList<Choice>();
		this.initiators = new ArrayList<Initiator>();
		this.clients = new ArrayList<Client>();
		this.lastDecision = "";
	}
	
	// For the tests
	public Run(double id, long seed) {
		super();
		this.id = id;
		this.maxToken = 0;
		this.description = "";
		this.choices = new ArrayList<Choice>();
		this.initiators = new ArrayList<Initiator>();
		this.clients = new ArrayList<Client>();
		this.lastDecision = "";
		this.randomId = new RandomId(seed);
	}

	// Getters and setters

	/**
	 * Getter of the id
	 * 
	 * @return the identifier of this run
	 */
	public double getId() {
		return this.id;
	}

	public int getMaxToken() {
		return maxToken;
	}

	// Getters for the tests

	public String getDescription() {
		return description;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	private List<Initiator> getInitiators() {
		return initiators;
	}


	/**
	 * Setter of choices
	 * 
	 * @param choices
	 *            the new variable choices
	 */
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	/**
	 * Setter of clients
	 * 
	 * @param clients
	 *            the new variable clients
	 */
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	/**
	 * Setter of initiators
	 * 
	 * @param initiators
	 *            the new variable initiators
	 */
	public void setInitiators(List<Initiator> initiators) {
		this.initiators = initiators;
	}

	// utils

	/**
	 * Access a User by the identifier
	 * 
	 * @param id
	 *            the identifier of the user
	 * @return the user
	 * @throws UserNotFoundException
	 */
	public User getUser(double id) throws UserNotFoundException {
		User user = null;

		try {
			user = getClient(id);
			return user;
		} catch (UserNotFoundException e) {
			for (Initiator initiator : initiators) {
				if (initiator.getId() == id)
					return initiator;
			}
			throw new UserNotFoundException(id);
		}

	}

	/**
	 * Access a Client by the identifier
	 * 
	 * @param id
	 *            the identifier of the client
	 * @return the client
	 * @throws UserNotFoundException
	 */
	private Client getClient(double id) throws UserNotFoundException {
		for (Client client : clients) {
			if (client.getId() == id)
				return client;
		}
		throw new UserNotFoundException(id);
	}

	/**
	 * Check if a choice exist in this run
	 * 
	 * @param idChoice
	 *            the identifier of the choice
	 * @return true if the choice exist, false if not
	 */
	public boolean choiceExist(double idChoice) {
		for (Choice choice : choices) {
			if (choice.getId() == idChoice)
				return true;
		}
		return false;
	}

	/**
	 * Add an initiator to the run
	 * 
	 * @param email
	 *            the e-mail address of the new initiator
	 * @return the initiator added to the run
	 * @throws AddressException 
	 */
	public Initiator addInitiator(String email) throws AddressException {
		Initiator initiator = new Initiator(this.randomId.generateNewId(), email);
		this.initiators.add(initiator);
		return initiator;
	}
	
	// General commands

	/**
	 * The response for an initiator contains the configuration
	 * parameters for the RUN, the list of all configured CLIENTS and their CHOICES, and the
	 * current DECISION.
	 * @return the status for an initiator
	 */
	public String initiatorStatus() {
		String emailBody = runStatus();
		emailBody += "\n";
		
		emailBody += "List of the clients : \n";
		for (Client client : clients) {
			emailBody += "ID : " + client.getId() + ", email : " + client.getEmail() + "\n";
			if(client.hasVoted()) {
				emailBody += "This client has voted :" + "\n";
				emailBody += client.choicesToString();
			} else {
				emailBody += "This client hasn't voted" + "\n";
			}
		}
		emailBody += "\n";
		
		if (!this.lastDecision.isEmpty()) {
			emailBody += "The last decision is :" + "\n";
			emailBody += this.lastDecision;
		} else {
			emailBody += "No decisions were made" + "\n";
		}
		
		return emailBody;
	}

	/**
	 *  The response for a client contains the configuration parameters of the RUN, the client’s CHOICE, 
	 *  and potential FOLLOWER information. 
	 * @param client
	 *            the client to look for the informations
	 * @return the status for a client
	 */
	public String clientStatus(Client client) {
		// Client tokenRepartition, or follower tokenRepartition

		String s = this.runStatus()
				+ "\n"
				+ "Client status :"
				+ client.toString();

		return s;
	}

	/**
	 * Return the configuration of the run in a String
	 * 
	 * @return a String with the configuration of this run
	 */
	public String runStatus() {
		// General configuration of the run
		String s = "\n\n" + "Description of the run : " + "\n";
		s += "id : " + id + "\n";
		s += "Number of tokens per user : " + maxToken + "\n";

		// Configuration of the choices
		if (choices.isEmpty()) {
			s += "No choices are available" + "\n";
		} else {
			s += "List of the choices : " + "\n";
			for (Choice choice : choices) {
				s += choice.toString() + "\n";
			}
		}
		return s;
	}

	// Client commands

	/**
	 * The client votes for a particular CHOICE by placing a number of tokens on that choice’s identifier.
	 * 
	 * @param client
	 *            the client voting
	 * @param idChoice
	 *            the identifier of the choice
	 * @param numberOfTokens
	 *            the number of tokens the client place on the choice
	 * @throws ChoiceNotFoundException
	 */
	public void vote(Client client, double idChoice, int numberOfTokens) throws ChoiceNotFoundException {
		// Check if the choice exist
		if (!this.choiceExist(idChoice)) {
			throw new ChoiceNotFoundException(idChoice);
		}

		client.vote(idChoice, numberOfTokens);
		client.setTokensUsed(numberOfTokens + client.getTokensUsed());
	}

	/**
	 * Set the follower of a client
	 * 
	 * @param idClient
	 *            the id of the client to follow
	 * @param client
	 *            the client who wants to follow another client
	 * @throws UserNotFoundException
	 */
	public void follow(double idClient, Client client) throws UserNotFoundException {
		Client clientToFollow = getClient(idClient);
		client.setFollowed(clientToFollow);
	}

	// Initiator commands

	/**
	 * Set the number of tokens that a client holds in order to express his preference in this run.
	 * 
	 * @param numberTokens
	 *            the new number of Tokens in this run
	 */
	public void tokenCount(int numberTokens) {
		this.maxToken = numberTokens;
	}

	/**
	 * Set the description of this run.
	 * 
	 * @param text
	 *            the new description of this run
	 */
	public void description(String text) {
		this.description = text;
	}

	/**
	 * Adds a client to this run.
	 * 
	 * @param clientEmail
	 *            the e-mail address of the new client
	 * @return the new client
	 * @throws AddressException 
	 */
	public Client addClient(String clientEmail) throws AddressException {
		Client client = new Client(this.randomId.generateNewId(), clientEmail);
		this.clients.add(client);
		return client;
	}

	public List<Client> getClients() {
		return clients;
	}

	/**
	 * Deletes a client from this run.
	 * 
	 * @param idClient
	 *            the identifier of the client
	 * @throws UserNotFoundException
	 */
	public void delClient(double idClient) throws UserNotFoundException {
		for (Client client : clients) {
			if (client.getId() == idClient) {
				clients.remove(client);
				return;
			}
		}
		throw new UserNotFoundException(idClient);
	}

	/**
	 * Adds the provide text of the choice to the list of choices.
	 * 
	 * @param text
	 *            the description of the new choice
	 */
	public void addChoice(String text) {
		Choice choice = new Choice(this.randomId.generateNewId(), text);
		choices.add(choice);
	}

	/**
	 * Deletes the choice corresponding to the identifier from the list of choices.
	 * 
	 * @param idChoice
	 *            the identifier of the choice
	 * @throws ChoiceNotFoundException
	 */
	public void delChoice(double idChoice) throws ChoiceNotFoundException {
		for (Choice choice : choices) {
			if (choice.getId() == idChoice) {
				choices.remove(choice);
				return;
			}
		}
		throw new ChoiceNotFoundException(idChoice);
	}
	
	/**
	 * Sends a decision mail to all clients, informing each client which choice was assigned to him.
	 * @return the mails to send with the decision
	 */
	public List<Email> sendDecision() {
		lastDecision = "";
		DecisionMaker decisionMaker = new HungarianDecision(this.clients, this.choices);
		HashMap<Double, Double> decision = decisionMaker.makeDecision();
		
		//Creation of the emails
		List<Email> mails = new ArrayList<Email>();
		for(Map.Entry<Double, Double> entry : decision.entrySet()) {
			Client client = null;
			try {
				client = this.getClient(entry.getKey());
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
			String decisionText = "You have the choice : " + entry.getValue();
			
			Email mail = new Email(client.getEmail(), "", decisionText);
			mails.add(mail);
			
			lastDecision += "Client (" + entry.getKey() + ") has choice (" + entry.getValue() + ")" + "\n"; 
		}
		
		return mails;
	}

	/**
	 * Clone the run
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Run runClone = (Run) super.clone();

		List<Choice> choicesClone = new ArrayList<Choice>();
		for (Choice choice : choices) {
			choicesClone.add((Choice) choice.clone());
		}
		runClone.setChoices(choicesClone);

		// clone of clients (with circular dependencies)
		List<Client> clientsClone = new ArrayList<Client>();
		for (Client client : clients) {
			clientsClone.add((Client) client.clone());
		}
		for (Client client : clientsClone) {
			client.cloneFollower(clientsClone);
		}
		runClone.setClients(clientsClone);

		List<Initiator> initiatorsClone = new ArrayList<Initiator>();
		for (Initiator initiator : initiators) {
			initiatorsClone.add((Initiator) initiator.clone());
		}
		runClone.setInitiators(initiatorsClone);

		return runClone;
	}

	@Override
	public boolean equals(Object obj) {
		Run run = (Run) obj;

		if (id == run.getId() && maxToken == run.getMaxToken() && description == run.getDescription()) {

			if (choices.size() == run.getChoices().size()) {
				for (int i = 0; i < choices.size(); i++) {
					if (!choices.get(i).equals(run.getChoices().get(i)))
						return false;
				}
			} else {
				return false;
			}

			if (clients.size() == run.getClients().size()) {
				for (int i = 0; i < clients.size(); i++) {
					if (!clients.get(i).equals(run.getClients().get(i)))
						return false;
				}
			} else {
				return false;
			}

			if (initiators.size() == run.getInitiators().size()) {
				for (int i = 0; i < initiators.size(); i++) {
					if (!initiators.get(i).equals(run.getInitiators().get(i)))
						return false;
				}
			} else {
				return false;
			}

		} else {
			return false;
		}

		return true;

	}
	
	
	//For the tests
	public Client addClient(String clientEmail, double id) throws AddressException {
		Client client = new Client(id, clientEmail);
		this.clients.add(client);
		return client;
	}
	
	public Initiator addInitiator(String email, double id) throws AddressException {
		Initiator initiator = new Initiator(id, email);
		this.initiators.add(initiator);
		return initiator;
	}
	
	public void addChoice(String text, double id) {
		Choice choice = new Choice(id, text);
		choices.add(choice);
	}

}
