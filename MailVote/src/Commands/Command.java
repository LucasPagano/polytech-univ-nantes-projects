package Commands;

import java.util.Scanner;

/**
 * A class wich represents a command (found in an e-mail)
 *  and contains the eventual parameters.
 * @author Monvoisin Mathilde
 */

public class Command {
	private String name = "0";
	private String attribute = null;
	private Double numberOfTokens = 0.0;
	
	public Command(){
	}
	
	public Command(String name){
		this.name = name;
	}
	
	public Command(String name, String attribute){
		this.name = name;
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getNumberOfTokens() {
		return numberOfTokens;
	}

	public void setNumberOfTokens(Double number) {
		this.numberOfTokens = number;
	}
	
	public boolean isCommandToBeUsedAlone(){
		boolean hasAttribute = false;
		switch (this.getName()){
			case "HELP": 
				hasAttribute = true;
				break;
			case "STATUS": 
				hasAttribute = true;
				break;
			default:
				hasAttribute = false;
				break;
		}
		return hasAttribute;
	}
	
	public boolean isCommand(){
		boolean isCommand = false;
		switch (this.getName()){
			case "HELP": 
				isCommand = true;
			case "RUN": 
				isCommand = true;
			case "USER": 
				isCommand = true;
			case "STATUS": 
				isCommand = true;
				
			//client commands
			case "VOTE": 
				isCommand = true;
			case "FOLLOW": 
				isCommand = true;
				
			//initiator commands
			case "CREATERUN": 
				isCommand = true;
			case "TOKENCOUNT": 
				isCommand = true;
			case "DESCRIPTION": 
				isCommand = true;
			case "ADDCLIENT": 
				isCommand = true;
			case "DELCLIENT": 
				isCommand = true;
			case "ADDCHOICE": 
				isCommand = true;
			case "DELCHOICE": 
				isCommand = true;
			case "SENDINVITATION": 
				isCommand = true;
			case "SENDDECISION": 
				isCommand = true;
				
		}
		return isCommand;
	}
	
	public String typeOfAttribute(){
		String typeOfAttribute = null;
		
		switch (this.getName()){
		
		//general commands
		case "RUN": 
			typeOfAttribute = "number";
			break;
		case "USER": 
			typeOfAttribute = "number";
			break;
			
		//client commands
		case "VOTE": 
			typeOfAttribute = "number";
			break;
		case "FOLLOW": 
			typeOfAttribute = "number";
			break;
			
		//initiator commands
		case "CREATERUN": 
			typeOfAttribute = "address";
			break;
		case "TOKENCOUNT": 
			typeOfAttribute = "number";
			break;
		case "DESCRIPTION": 
			typeOfAttribute = "string";
			break;
		case "ADDCLIENT": 
			typeOfAttribute = "address";
			break;
		case "DELCLIENT": 
			typeOfAttribute = "number";
			break;
		case "ADDCHOICE": 
			typeOfAttribute = "string";
			break;
		case "DELCHOICE": 
			typeOfAttribute = "number";
			break;
		case "SENDINVITATION": 
			typeOfAttribute = "string";
			break;
		case "SENDDECISION": 
			typeOfAttribute = "string";
			break;
		default :
			typeOfAttribute = "undefined";
			break;
		}
	return typeOfAttribute;
	}
	
	public boolean hasCorrectTypeOfAttribute(){
		boolean hasCorrectTypeOfAttribute = true;
		
		switch (this.typeOfAttribute()){
		
		case "number": 
			try {
				Double attribute = Double.parseDouble(this.getAttribute());
			} catch (NumberFormatException e) {
				hasCorrectTypeOfAttribute = false;
			}
			break;
		case "string":
			break;
		case "address":
			if (!isEmail(this.getAttribute())){
				hasCorrectTypeOfAttribute = false;
			}
			break;
		case "undefined":
			hasCorrectTypeOfAttribute = false;
			break;
		default:
			System.out.println("cas non traité");
		}
		
		return hasCorrectTypeOfAttribute;
	}
	
	public boolean isEmail(String email){
		boolean isEmail = true;
		
		//searching for the @
		Scanner scanner = new Scanner(email);
		scanner.useDelimiter("@");
		String anteAt = scanner.next();
		
		if (scanner.hasNext()){
			String postAt = scanner.next();	
			
			
			//searching for the .
			Scanner scanner2 = new Scanner(postAt);
			scanner2.useDelimiter("\\.");
			String betweenAtAndDot = scanner2.next();
			
			if (scanner2.hasNext()){
				String afterDot = scanner2.next();
			} else {
				isEmail = false;
			}
			
		// if there's no "@", then it's not an email address
		} else {
			isEmail = false;
		}

		
		return isEmail;
	}
	
	public int attributeToInt(){
		return Integer.parseInt(this.getAttribute());
	}
	
	//correspondance fonction Commands -> Command
	/**
	 * USER -> public void initUser(String email)
	 * 
	 * VOTE -> ?
	 * FOLLOW -> ?
	 * 
	 * private Run getRun(int id) throws RunNotFoundException
	 * RUN -> public void run(int id) throws RunNotFoundException
	 * HELP -> public void helpMessage(String email)
	 * STATUS -> public String status(int idRun) throws RunNotFoundException
	 * CREATERUN -> public String createRun(String address)
	 * TOKENCOUNT -> public void tokenCount(int numberTokens) throws CommandNotAvailableException
	 * DESCRIPTION -> public void description(String text) throws CommandNotAvailableException
	 * ADDCLIENT -> public void addClient(String clientEmail) throws CommandNotAvailableException
	 * DELCLIENT -> public void delClient(int id) throws UserNotFoundException, CommandNotAvailableException
	 * ADDCHOICE -> public void addChoice(String text) throws CommandNotAvailableException
	 * DELCHOICE -> public void delChoice(int idChoice) throws ChoiceNotFoundException, CommandNotAvailableException
	 * 
	 * SENDINVITATION -> ?
	 * SENDDECISION -> ?
	 * 
	 */
	
}
