package Commands;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Exceptions.ChoiceNotFoundException;
import Exceptions.UserNotFoundException;

/**
 * This class represents a run.
 * @author Maraval Nathan
 */
public class Run {

	//TODO : id should be a long, and a random number
	//TODO : for Run, Choice, Client and Initiator
	private int id;
	private int maxToken;
	private String description;
	private List<Choice> choices;
	private List<Client> clients;
	private List<Initiator> initiators;
	//Tableau de décisions
	private int state;
	//0 mean not launched
	int idClient;
	int idInitiator;
	int idChoice;
	
	/**
	 * @param id the identifier of the run
	 * @param initiatorEmail the e-mail of the original initiator
	 */
	public Run(int id, String initiatorEmail) {
		super();
		this.id = id;
		this.maxToken = 0;
		this.description = "";
		this.choices = new ArrayList<Choice>();
		this.initiators = new ArrayList<Initiator>();
		this.idInitiator = 0;
		this.initiators.add(new Initiator(this.idInitiator, initiatorEmail));
		this.idInitiator++;
		this.clients = new ArrayList<Client>();
		this.state = 0;	
		this.idClient = 0;
		this.idChoice = 0;
	}
	
	//Getter
	
	/**
	 * Getter of the id
	 * @return the identifier of this run
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Access a Client by the identifier
	 * @param id the the identifier of the client
	 * @return the client
	 * @throws UserNotFoundException
	 */
	private Client getClient(int id) throws UserNotFoundException{
		for (Client client : clients) {
			if(client.getId() == id) return client;
		}
		throw new UserNotFoundException();
	}	
	

	/**
	 * Access a Client by the email
	 * @param email the e-mail address of the client
	 * @return the client
	 * @throws UserNotFoundException
	 */
	private Client getClient(String email) throws UserNotFoundException{
		for (Client client : clients) {
			if(client.getEmail() == email) return client;
		}
		throw new UserNotFoundException();
	}
	
	//TODO : doc
	private Initiator getInitiator(String email) throws UserNotFoundException{
		for (Initiator initiator : initiators) {
			if(initiator.getEmail() == email) return initiator;
		}
		throw new UserNotFoundException();
	}
	
	/**
	 * Check if the user is an initiator of this run
	 * @param email the e-mail address of the user
	 * @return true if the user is an initiator, false if not
	 * @throws UserNotFoundException 
	 */
	public boolean isInitiator(String email) throws UserNotFoundException {
		try {
			getClient(email);
			return false;
		}catch (UserNotFoundException e) {
			getInitiator(email);
			return true;
		}
	}
	
	/**
	 * Check if a choice exist in this run
	 * @param idChoice the identifier of the choice
	 * @return true if the choice exist, false if not
	 */
	public boolean choiceExist(int idChoice) {
		for (Choice choice : choices) {
			if (choice.getId() == idChoice) return true;
		}
		return false;
	}

	
	/*public void addInitiator(Initiator initiator) {
		this.initiators.add(initiator);
	}
	
	public boolean clientExist(String email){
		boolean exist = false;
		for (Client client : clients) {
			if(client.getEmail().equals(email)) exist = true; 
		}
		return exist;
	}*/

	//General commands
	
	public String initiatorStatus(String email) {
		// TODO Auto-generated method stub
		return configuration();
	}
	
	public String clientStatus(String email) throws UserNotFoundException {
		//Client tokenRepartition, or follower tokenRepartition
		Client client = getClient(email);

		String s = this.configuration() ;
		s += client.followerToString();
		
		return s;
	}
	
	/**
	 * Return the configuration of the run in a String
	 * @return a String with the configuration of this run
	 */
	private String configuration(){
		//General configuration of the run
		String s = "Description de la run : " + "\n";
		s += "id : " + id + "\n";
		s += "Nombre de jetons par utilisateur : " + maxToken + "\n";
		
		//Configuration of the choices
		if (choices.isEmpty()) {
			s += "Aucun choix n'est disponible" + "\n";
		} else {
			s += "Liste des choix : " + "\n";
			for (Choice choice : choices) {
				s += choice.toString();
			}
		}
		return s;
	}
	
	//Client commands
	
	/**
	 * Set the follower of a client
	 * @param idClient the id of the client to follow
	 * @param email the e-mail address of the user who wants to follow someone
	 * @throws UserNotFoundException
	 */
	public void follow(int idClient, String email) throws UserNotFoundException {
		Client client = getClient(email);
		Client clientToFollow = getClient(idClient);
		client.setFollower(clientToFollow);
	}
	
	//Initiator commands
	
	/**
	 * Set the number of tokens that a client holds in order to express his preference in this run.
	 * @param numberTokens the new number of Tokens in this run
	 */
	public void tokenCount(int numberTokens) {
		this.maxToken = numberTokens;
	}

	/**
	 * Set the description of this run. 
	 * @param text the new description of this run
	 */
	public void description(String text) {
		this.description = text;
	}

	/**
	 * Adds a client to this run.
	 * @param clientEmail the e-mail address of the new client
	 */
	public void addClient(String clientEmail) {
		//TODO: check the email is free
		this.clients.add(new Client(this.idClient, clientEmail));
		this.idClient++;
	}
	
	/**
	 * Deletes a client from this run.
	 * @param idClient the identifier of the client
	 * @throws UserNotFoundException
	 */
	public void delClient(int idClient) throws UserNotFoundException{		
		for (Client client : clients) {
			if (client.getId() == idClient){
				clients.remove(client);
				return;
			}
		}
		throw new UserNotFoundException();
	}

	/**
	 * Adds the provide text of the choice to the list of choices.
	 * @param text the description of the new choice
	 */
	public void addChoice(String text) {
		Choice choice = new Choice(this.idChoice);
		choices.add(choice);
		this.idChoice++;
	}

	/**
	 * Deletes the choice corresponding to the identifier from the list of choices.
	 * @param idChoice the identifier of the choice
	 * @throws ChoiceNotFoundException
	 */
	public void delChoice(int idChoice) throws ChoiceNotFoundException {
		for (Choice choice : choices) {
			if (choice.getId() == idChoice){
				clients.remove(choice);
				return;
			}
		}
		throw new ChoiceNotFoundException();
	}

}
