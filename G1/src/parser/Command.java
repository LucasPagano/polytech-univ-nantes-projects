package parser;

import java.util.Scanner;

/**
 * A class which represents a command (found in an e-mail) and contains the eventual parameters.
 * 
 * @author Monvoisin Mathilde
 */

public class Command {
	private String name = "0";
	private String attribute = null;
	private Integer numberOfTokens = 0;

	public Command() {
	}

	public Command(String name) {
		this.name = name;
	}

	public Command(String name, String attribute) {
		this.name = name;
		this.attribute = attribute;
	}
	
	/**
	 * Check if the command has to have an attribute
	 * 
	 * @return a boolean, true means that the command has no attribute
	 */
	public boolean isCommandToBeUsedAlone() {
		boolean hasAttribute = false;
		switch (this.getName()) {
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

	/**
	 * Check if the command exists
	 * 
	 * @return a boolean, true means that the command exists
	 */
	public boolean isCommand() {
		boolean isCommand = false;
		switch (this.getName()) {
			case "HELP":
				isCommand = true;
				break;
			case "RUN":
				isCommand = true;
				break;
			case "USER":
				isCommand = true;
				break;
			case "STATUS":
				isCommand = true;
				break;
	
				// client commands
			case "VOTE":
				isCommand = true;
				break;
			case "FOLLOW":
				isCommand = true;
				break;
	
				// initiator commands
			case "CREATERUN":
				isCommand = true;
				break;
			case "TOKENCOUNT":
				isCommand = true;
				break;
			case "DESCRIPTION":
				isCommand = true;
				break;
			case "ADDCLIENT":
				isCommand = true;
				break;
			case "DELCLIENT":
				isCommand = true;
				break;
			case "ADDINITIATOR":
				isCommand = true;
				break;
			case "ADDCHOICE":
				isCommand = true;
				break;
			case "DELCHOICE":
				isCommand = true;
				break;
			case "SENDINVITATION":
				isCommand = true;
				break;
			case "SENDDECISION":
				isCommand = true;
				break;

		}
		return isCommand;
	}

	/**
	 * Methods that contains the type of the attribute of each command
	 * 
	 * @return the type of the attribute of the command on which it is used
	 */
	public String typeOfAttribute() {
		String typeOfAttribute = null;

		switch (this.getName()) {

		// general commands
		case "HELP":
			typeOfAttribute = "null";
			break;
		case "RUN":
			typeOfAttribute = "double";
			break;
		case "USER":
			typeOfAttribute = "double";
			break;
		case "STATUS":
			typeOfAttribute = "null";
			break;

		// client commands
		case "VOTE":
			typeOfAttribute = "double";
			break;
		case "FOLLOW":
			typeOfAttribute = "double";
			break;

		// initiator commands
		case "CREATERUN":
			typeOfAttribute = "address";
			break;
		case "TOKENCOUNT":
			typeOfAttribute = "integer";
			break;
		case "DESCRIPTION":
			typeOfAttribute = "string";
			break;
		case "ADDCLIENT":
			typeOfAttribute = "address";
			break;
		case "DELCLIENT":
			typeOfAttribute = "double";
			break;
		case "ADDINITIATOR":
			typeOfAttribute = "address";
			break;
		case "ADDCHOICE":
			typeOfAttribute = "string";
			break;
		case "DELCHOICE":
			typeOfAttribute = "double";
			break;
		case "SENDINVITATION":
			typeOfAttribute = "string";
			break;
		case "SENDDECISION":
			typeOfAttribute = "string";
			break;
		default:
			typeOfAttribute = "undefined";
			break;
		}
		return typeOfAttribute;
	}

	/**
	 * Check if the attribute of the command has the correct type of attribute
	 * 
	 * @return true if the command has the correct type of attribute
	 */
	public boolean hasCorrectTypeOfAttribute() {
		boolean hasCorrectTypeOfAttribute = true;

		switch (this.typeOfAttribute()) {

		case "integer":
			try {
				// try if it is possible to parse it
				Integer.parseInt(this.getAttribute());
			} catch (NumberFormatException e) {
				hasCorrectTypeOfAttribute = false;
			}
			break;
		case "double":
			try {
				Double.parseDouble(this.getAttribute());
			} catch (NumberFormatException e) {
				hasCorrectTypeOfAttribute = false;
			}
			break;
		case "string":
			break;
		case "address":
			if (!isEmail(this.getAttribute())) {
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

	/**
	 * Check if the parameter has the format of an mail : text@text.text
	 * 
	 * @return true if the parameter has the format on an email
	 */
	public boolean isEmail(String email) {
		boolean isEmail = true;

		// searching for the @
		Scanner scanner = new Scanner(email);
		scanner.useDelimiter("@");

		if (scanner.hasNext()) {
			String postAt = scanner.next();

			// searching for the .
			Scanner scanner2 = new Scanner(postAt);
			scanner2.useDelimiter("\\.");

			if (scanner2.hasNext()) {
			} else {
				isEmail = false;
			}

			scanner2.close();

			// if there's no "@", then it's not an email address
		} else {
			isEmail = false;
		}

		scanner.close();

		return isEmail;
	}
	
	/**
	 * @return the attribute of the command
	 */
	public String getAttribute() {
		return attribute;
	}
	
	/**
	 * Set the attribute of the command
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the name of the command
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the command
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the number of tokens of the command - has to be used only if the command is VOTE
	 */
	public Integer getNumberOfTokens() {
		return numberOfTokens;
	}

	/**
	 * Set the number of tokens of the command - has to be used only if the command is VOTE
	 */
	public void setNumberOfTokens(Integer number) {
		this.numberOfTokens = number;
	}
}
