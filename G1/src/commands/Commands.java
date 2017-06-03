package commands;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import util.Email;
import util.HelpMessage;
import util.RandomSeed;
import exceptions.ChoiceNotFoundException;
import exceptions.CommandNotAvailableException;
import exceptions.RunNotFoundException;
import exceptions.RunNotSpecifiedException;
import exceptions.UserNotFoundException;
import exceptions.UserNotSpecifiedException;
import exceptions.VoteTotalOfTokensIncorrect;

/**
 * A class with all the commands usable in an e-mail.
 * @author Maraval Nathan
 */
public class Commands {
	private List<Run> runs;
	private User currentUser;
	private Run currentRun;

	// clientsVoting is used in order to check if the user have used all his tokens in the mail
	// the key is the client voting, and the value is tokens of the run
	private Map<Client, Integer> clientsVoting; 
	
	private List<Run> runsCloned;

	
	public Commands() {
		super();
		this.runs = new ArrayList<Run>();
		this.currentUser = null;
		this.currentRun = null;
		this.clientsVoting = new HashMap<Client, Integer>();
		
		this.runsCloned = new ArrayList<Run>();
	}
	
	
	public User getCurrentUser() {
		return currentUser;
	}

	public Run getCurrentRun() {
		return currentRun;
	}

	public List<Run> getRuns() {
		return runs;
	}

	/**
	 * Method to call at the opening of an email
	 * @param email the email address of the user who sent the email
	 */
	public void initMail() {
		currentUser = null;
		currentRun = null;
		runsCloned.clear();
		
		for(Client client : clientsVoting.keySet()) {
			client.setTokensUsed(0);
		}
		clientsVoting.clear();
	}
	
	/**
	 * Method to call at the end of an email with no error
	 * @throws VoteTotalOfTokensIncorrect 
	 */
	public void endMail() throws VoteTotalOfTokensIncorrect {
		// Check votes number of tokens 
		for(Map.Entry<Client, Integer> entry : clientsVoting.entrySet()) {
			if(entry.getKey().getTokensUsed() != entry.getValue()) {
				throw new VoteTotalOfTokensIncorrect(entry.getKey().getTokensUsed(), entry.getValue());
			}
		}
		
		// Update the runs
		for (Run runCloned : runsCloned) {
			boolean newRun = true;
			for (int i = 0; i < runs.size(); i++) {
				Run run = runs.get(i);
				if(run.getId() == runCloned.getId()) {
					runs.remove(i);
					runs.add(runCloned);
					newRun = false;
					break;
				}
			}
			
			if(newRun) {
				runs.add(runCloned);
			}
		}
	}

	/**
	 * Return the run associate to the id given
	 * @param id identifier of the run
	 * @return the run with the id given
	 * @throws RunNotFoundException
	 */
	public Run getRun(double id) throws RunNotFoundException{
		for (Run run : runs) {
			if(run.getId() == id) return(run); 
		}
		
		throw new RunNotFoundException(id);
	}
	
	private void checkRunAndUser(String commandName) throws RunNotSpecifiedException, UserNotSpecifiedException {
		if(this.currentRun == null) {
			throw new RunNotSpecifiedException(commandName);
		}
		if(this.currentUser == null) {
			throw new UserNotSpecifiedException(commandName);
		}
	}
	
	//General commands


	/**
	 * Selects a specific run with the given identifier. 
	 * Shall always precede any other command except 'createRun' and 'help'.
	 * @param id the identifier of the run
	 * @throws RunNotFoundException
	 */
	public void run(double id) throws RunNotFoundException {
		//Check if the run exist
		Run run = getRun(id);
		
		//Check if the run was already cloned
		Run clone = null;
		for (Run runCloned : runsCloned) {
			if(run.getId() == id) clone = runCloned; 
		}
		
		//Creation of the clone
		if (clone == null) {
			try {
				clone = (Run) run.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			runsCloned.add(clone);
		}
		
		currentRun = clone;
	}
	
	public void user(double id) throws UserNotFoundException, RunNotSpecifiedException {
		if(this.currentRun == null) throw new RunNotSpecifiedException("USER");
		
		User user = currentRun.getUser(id);
		this.currentUser = user;
	}
	
	/**
	 * The help message is returned
	 * @param address the address where to send the email
	 * @return a list with the email to send
	 */
	public List<Email>  helpMessage(InternetAddress address){
		Email email = new Email(address, "IMAP server", HelpMessage.getHelpMessage());
		List<Email> emails = new ArrayList<Email>();
		emails.add(email);
		return emails;
	}
	
	/**
	 * Returns the status of the current evaluation for this particular client or initiator. The response
     * for a client contains the configuration parameters of the RUN, the client’s CHOICE, and
     * potential FOLLOWER information. The response for an initiator contains the configuration
     * parameters for the RUN, the list of all configured CLIENTS and their CHOICES, and the
     * current DECISION.
	 * @param email the address where to send the email
	 * @return a list with the email to send
	 * @throws RunNotSpecifiedException
	 * @throws UserNotSpecifiedException
	 */
	public List<Email> status(InternetAddress email) throws RunNotSpecifiedException, UserNotSpecifiedException {
		this.checkRunAndUser("STATUS");
		
		String stringMail;
		if(currentUser.isInitiator()){
			stringMail = currentRun.initiatorStatus();
		} else {
			Client client = (Client) currentUser;
			stringMail =  currentRun.clientStatus(client);
		}
		Email mail = new Email(email, "IMAP server", stringMail);
		List<Email> mails = new ArrayList<Email>();
		mails.add(mail);
		return mails;
	}
	
	//Client commands
	
	/**
	 * The client votes for a particular CHOICE by placing a number of tokens on that choice’s identifier.
	 * @param idChoice the identifier of the choice
	 * @param numberOfTokens the number of tokens the client place on the choice
	 * @throws ChoiceNotFoundException
	 * @throws CommandNotAvailableException 
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public void vote(double idChoice, int numberOfTokens) throws ChoiceNotFoundException, CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException {
		this.checkRunAndUser("VOTE");
		
		if(!currentUser.isInitiator()){
			Client client = (Client) currentUser;
			currentRun.vote(client, idChoice, numberOfTokens);

			
			//Check if this client have already voted in this run
			boolean clientAlreadyVoted = clientsVoting.containsKey(currentUser);
			if (!clientAlreadyVoted) {
				clientsVoting.put(client, currentRun.getMaxToken());
			}
			
		} else {
			throw new CommandNotAvailableException("VOTE");
		}
	}
	
	/**
	 * The client delegates the voting to another client that is specified in the identifier. 
	 * @param idClient the identifier of the client
	 * @throws UserNotFoundException 
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public void follow(double idClient) throws UserNotFoundException, RunNotSpecifiedException, UserNotSpecifiedException {
		this.checkRunAndUser("FOLLOW");
		
		if(!currentUser.isInitiator()){
			Client client = (Client) currentUser;
			currentRun.follow(idClient, client);
		}
	}
	
	//Initiator commands
	
	/**
	 * Creates and selects a new run. 
	 * All further commands are configuring the created run. 
	 * The system answers with the initiators version of STATUS.
	 * @email the e-mail address of the first initiator of the run created
	 * @return the initiators version of STATUS.
	 * @throws AddressException 
	 */
	public List<Email> createRun(String email) throws AddressException {
		
		Run run = new Run(RandomSeed.generateNewSeed());
		runsCloned.add(run);
		currentRun = run;
		
		Initiator initiator = currentRun.addInitiator(email);
		currentUser = initiator;
		
		List<Email> mails = null;
		try {
			mails = this.status(initiator.getEmail());
		} catch (Exception e) {
			//Should not happen
			e.printStackTrace();
		}
		String textBefore = "A new run has been created." + "\n"
				+ "You are the initiator with the id : " + initiator.getId() +"\n"
				+ "\n"
				+ "Status of the new run :" + "\n";
		mails.get(0).addTextBefore(textBefore);

		return mails;
	}
	
	/**
	 * Add an initiator to the run
	 * @param email the email address of the initiator
	 * @return a list with the email to send to the new initiator
	 * @throws RunNotSpecifiedException
	 * @throws UserNotSpecifiedException
	 * @throws AddressException
	 * @throws CommandNotAvailableException
	 */
	public List<Email> addInitiator(String address) throws RunNotSpecifiedException, UserNotSpecifiedException, AddressException, CommandNotAvailableException {
		this.checkRunAndUser("ADDINITIATOR");
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("ADDCLIENT");
		
		Initiator initiator = currentRun.addInitiator(address);
		String body = "You have been added to a Run as an initiator."
				+ "\n"
				+ "Initiator status : "
				+ initiator.toString()
				+ "\n"
				+ "Run status :"
				+ currentRun.initiatorStatus();
		Email email = new Email(initiator.getEmail(), "", body);
		List<Email> emails = new ArrayList<Email>();
		emails.add(email);
		
		return emails;
	}
	
	public User getUser() {
		return currentUser;
	}

	/**
	 * Set the number of tokens that a client holds in order to express his preference in the run.
	 * @p0aram numberTokens the new number of Tokens in the run
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public void tokenCount(int numberTokens) throws CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException{
		this.checkRunAndUser("TOKENCOUNT");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("TOKENCOUNT");
		
		currentRun.tokenCount(numberTokens);
	}
	
	/**
	 * Set the description of the run as presented to the clients.
	 * @param text the description of the run
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public void description(String text) throws CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException{
		this.checkRunAndUser("DESCRIPTION");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("DESCRIPTION");
		
		currentRun.description(text);
	}
	
	/**
	 * Adds a client to the current run.
	 * @param clientEmail the e-mail address of the new client
	 * @return a list with the email to send to the new client
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 * @throws AddressException 
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public List<Email> addClient(String clientEmail) throws CommandNotAvailableException, AddressException, RunNotSpecifiedException, UserNotSpecifiedException{
		this.checkRunAndUser("ADDCLIENT");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("ADDCLIENT");
		
		Client client = currentRun.addClient(clientEmail);
		String body = "You have been added to a Run as a client."
				+ "\n"
				+ "Client status : "
				+ client.toString()
				+ "\n"
				+ "Run status :"
				+ currentRun.runStatus();
		Email email = new Email(client.getEmail(), "", body);
		List<Email> emails = new ArrayList<Email>();
		emails.add(email);
		
		return emails;
	}
	
	/**
	 * Deletes a client from the current run.
	 * @param id the identifier of the client
	 * @throws UserNotFoundException
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public void delClient(double id) throws UserNotFoundException, CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException{
		this.checkRunAndUser("DELCLIENT");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("DELCLIENT");
		
		currentRun.delClient(id);
	}
	
	/**
	 * Adds the provide text of the choice to the list of choices.
	 * @param text the description of the new choice
	 * @throws ChoiceNotFoundException
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public void addChoice(String text) throws CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException{
		this.checkRunAndUser("ADDCHOICE");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("ADDCHOICE");
		
		currentRun.addChoice(text);
	}
	
	/**
	 * Deletes the choice corresponding to the identifier from the list of choices.
	 * @param idChoice the identifier of the choice
	 * @throws ChoiceNotFoundException
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public void delChoice(double idChoice) throws ChoiceNotFoundException, CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException{
		this.checkRunAndUser("DELCHOICE");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("DELCHOICE");

		currentRun.delChoice(idChoice);
	}
	
	/**
	 * Sends an invitation to all clients providing the description of the current run and instructions on how to perform the 
voting with preconfigured commands for the answer. 
	 * @param text additional text at the beginning of the email 
	 * @return the mails to send
	 * @throws CommandNotAvailableException
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public List<Email> sendInvitation(String text) throws CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException {
		this.checkRunAndUser("SENDINVITATIONS");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("SENDINVITATION");
		
		String invitationText = text + "\n\n" + currentRun.runStatus();
		
		List<Email> mails = new ArrayList<Email>();
		List<Client> clients = currentRun.getClients();
		for (Client client : clients) {
			Email mail = new Email(client.getEmail(), "IMAP server", invitationText);
			mails.add(mail);
		}
		return mails;
	}
	
	/**
	 * Sends a decision mail to all clients, informing each client which choice was assigned to him.
	 * @param text this additional text is sent to the clients in the mail
	 * @return the mails to send with the decision
	 * @throws CommandNotAvailableException 
	 * @throws UserNotSpecifiedException 
	 * @throws RunNotSpecifiedException 
	 */
	public List<Email> sendDecision(String text) throws CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException {
		this.checkRunAndUser("SENDDECISION");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("SENDDECISION");
		
		List<Email> decision = currentRun.sendDecision();
		for (Email mail : decision) {
			String decisionText = text + "\n\n";
			mail.addTextBefore(decisionText);
		}
		return decision;
	}
	
	
	// For the tests
	
	public String createRun(String email, double idRun, double idInitiator) throws AddressException {
		
		Run run = new Run(idRun, 0);
		runsCloned.add(run);
		currentRun = run;
		
		Initiator initiator = currentRun.addInitiator(email, idInitiator);

		currentUser = initiator;
		
		return "";
	}
	
	public List<Email> addInitiator(String address, double id) throws RunNotSpecifiedException, UserNotSpecifiedException, AddressException, CommandNotAvailableException {
		this.checkRunAndUser("ADDINITIATOR");
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("ADDCLIENT");
		
		Initiator initiator = currentRun.addInitiator(address, id);
		String body = "You have been added to a Run as an initiator."
				+ "\n"
				+ "Initiator status : "
				+ initiator.toString()
				+ "\n"
				+ "Run status :"
				+ currentRun.initiatorStatus();
		Email email = new Email(initiator.getEmail(), "", body);
		List<Email> emails = new ArrayList<Email>();
		emails.add(email);
		
		return emails;
	}
	
	public List<Email> addClient(String clientEmail, double id) throws RunNotSpecifiedException, UserNotSpecifiedException, CommandNotAvailableException, AddressException {
		this.checkRunAndUser("ADDCLIENT");
		
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("ADDCLIENT");
		
		Client client = currentRun.addClient(clientEmail, id);
		String body = "You have been added to a Run as a client."
				+ "\n"
				+ "Client status : "
				+ client.toString()
				+ "\n"
				+ "Run status :"
				+ currentRun.runStatus();
		Email email = new Email(client.getEmail(), "", body);
		List<Email> emails = new ArrayList<Email>();
		emails.add(email);
		
		return null;
	}
	
	public void addChoice(String text, double id) throws CommandNotAvailableException{
		if(!currentUser.isInitiator()) throw new CommandNotAvailableException("ADDCHOICE");
		
		currentRun.addChoice(text, id);
	}

}
