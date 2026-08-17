package Commands;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Exceptions.ChoiceNotFoundException;
import Exceptions.CommandNotAvailableException;
import Exceptions.RunNotFoundException;
import Exceptions.UserNotFoundException;

/**
 * A class with all the commands usable in an e-mail.
 * @author Maraval Nathan
 */
public class Commands {
	private List<Run> runs;
	private String emailCurrentUser;
	private boolean isInitiator;
	//TODO : isClient
	private Run currentRun;
	
	// voteNumberTokens is used in order to check if the user have used all his tokens in the mail
	// the first key is the id of the run, the second key is the id of the choice, and the value is the number of tokens used
	private Map<Integer, Map<Integer, Integer>> voteNumberTokens; 
	
	public Commands() {
		super();
		this.runs = new ArrayList<Run>();
		this.emailCurrentUser = "";
		this.isInitiator = false;
		this.currentRun = null;
		this.voteNumberTokens = new HashMap<Integer,Map<Integer,Integer>>();
	}
	
	/**
	 * Method to call at the opening of an email
	 * @param email the email address of the user who sent the email
	 */
	public void initMail(String email) {
		this.emailCurrentUser = email;
	}
	
	/**
	 * Method to call at the end of an email
	 */
	public void endMail() {
		//TODO : check voteNumberTokens 
		
		
	}

	/**
	 * Return the run associate to the id given
	 * @param id identifier of the run
	 * @return the run with the id given
	 * @throws RunNotFoundException
	 */
	private Run getRun(int id) throws RunNotFoundException{
		for (Run run : runs) {
			if(run.getId() == id) return(run); 
		}
		
		throw new RunNotFoundException();
	}
	
	//Utils
	/**
	 * add a number in the Map this.voteNumberTokens
	 * @param idChoice the second identifier of the Map (the first is the identifier of the current run)
	 * @param numberOfTokens the number to add with the older value
	 */
	private void addVoteNumberTokens(int idChoice, int numberOfTokens) {
		Map<Integer, Integer> choicesMap = voteNumberTokens.get(currentRun.getId());
		if (choicesMap == null) {
			choicesMap = new HashMap<Integer,Integer>();
			voteNumberTokens.put(currentRun.getId(), choicesMap);
		}
		Integer oldNumberOfTokens = choicesMap.get(idChoice);
		if (oldNumberOfTokens == null) oldNumberOfTokens = 0;
		choicesMap.put(idChoice, oldNumberOfTokens + numberOfTokens);
	}
	
	//General commands
	
	/**
	 * Selects a specific run with the given identifier. 
	 * Shall always precede any other command except 'createRun' and 'help'.
	 * @param id the identifier of the run
	 * @throws RunNotFoundException
	 * @throws UserNotFoundException 
	 */
	public void run(int id) throws RunNotFoundException, UserNotFoundException {
		currentRun = getRun(id);
		isInitiator = currentRun.isInitiator(emailCurrentUser);
	}
	
	//TODO :
	//public void user(int id) {	
	//}
	
	//TODO : Not sure this command is useful here
	public void helpMessage(String email){
		//send help message to client.getEmail()	
	}
	
	//TODO : check if UserNotFoundException can happen
	public String status() throws RunNotFoundException, UserNotFoundException{
		if(this.isInitiator){
			System.out.println("est initiateur");
			return currentRun.initiatorStatus(this.emailCurrentUser);
		} else {
			System.out.println("est client");
			return currentRun.clientStatus(this.emailCurrentUser);
		}
	}
	
	//Client commands
	
	/**
	 * The client votes for a particular CHOICE by placing a number of tokens on that choice’s identifier.
	 * @param idChoice the identifier of the choice
	 * @param numberOfTokens the number of tokens the client place on the choice
	 * @throws ChoiceNotFoundException
	 * @throws CommandNotAvailableException 
	 */
	public void vote(int idChoice, int numberOfTokens) throws ChoiceNotFoundException, CommandNotAvailableException {
		if(!this.isInitiator){
			if (currentRun.choiceExist(idChoice)) {
				addVoteNumberTokens(idChoice, numberOfTokens);
			} else {
				throw new ChoiceNotFoundException();
			}
		} else {
			throw new CommandNotAvailableException();
		}
	}
	
	/**
	 * The client delegates the voting to another client that is specified in the identifier. 
	 * @param idClient the identifier of the client
	 * @throws UserNotFoundException 
	 */
	public void follow(int idClient) throws UserNotFoundException {
		if(!this.isInitiator){
			currentRun.follow(idClient, emailCurrentUser);
		}
	}
	
	//Initiator commands
	
	//TODO : complete address in javadoc
	/**
	 * Creates and selects a new run. 
	 * All further commands are configuring the created run. 
	 * The system answers with the initiators version of STATUS.
	 * @param address I don't know
	 * @return the initiators version of STATUS.
	 */
	public String createRun(String address) {
		//TODO : What is address ?
		
		this.isInitiator = true;
		
		//TODO : create unique id
		Run run = new Run(0, emailCurrentUser);
		runs.add(run);
		currentRun = run;
		
		//TODO : The system answers with the initiators version of STATUS.
		return "";
	}
	
	/**
	 * Set the number of tokens that a client holds in order to express his preference in the run.
	 * @param numberTokens the new number of Tokens in the run
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 */
	public void tokenCount(int numberTokens) throws CommandNotAvailableException{
		if(!this.isInitiator) throw new CommandNotAvailableException();
		
		currentRun.tokenCount(numberTokens);
	}
	
	/**
	 * Set the description of the run as presented to the clients.
	 * @param text the description of the run
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 */
	public void description(String text) throws CommandNotAvailableException{
		if(!this.isInitiator) throw new CommandNotAvailableException();
		
		currentRun.description(text);
	}
	
	/**
	 * Adds a client to the current run.
	 * @param clientEmail the e-mail address of the new client
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 */
	public void addClient(String clientEmail) throws CommandNotAvailableException{
		if(!this.isInitiator) throw new CommandNotAvailableException();
		
		currentRun.addClient(clientEmail);
	}
	
	/**
	 * Deletes a client from the current run.
	 * @param id the identifier of the client
	 * @throws UserNotFoundException
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 */
	public void delClient(int id) throws UserNotFoundException, CommandNotAvailableException{
		if(!this.isInitiator) throw new CommandNotAvailableException();
		
		currentRun.delClient(id);
	}
	
	/**
	 * Adds the provide text of the choice to the list of choices.
	 * @param text the description of the new choice
	 * @throws ChoiceNotFoundException
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 */
	public void addChoice(String text) throws CommandNotAvailableException{
		if(!this.isInitiator) throw new CommandNotAvailableException();
		
		currentRun.addChoice(text);
	}
	
	/**
	 * Deletes the choice corresponding to the identifier from the list of choices.
	 * @param idChoice the identifier of the choice
	 * @throws ChoiceNotFoundException
	 * @throws CommandNotAvailableException the user need to be an Initiator
	 */
	public void delChoice(int idChoice) throws ChoiceNotFoundException, CommandNotAvailableException{
		if(!this.isInitiator) throw new CommandNotAvailableException();
		
		currentRun.delChoice(idChoice);
	}

}
