package Exceptions;

public class ChoiceNotFoundException extends Exception {
	public String getErrorMessage() {
		return errorMessage;
	}

	private String errorMessage = "One of your choices doesn't exist. "
			+ "CAUTION : The other commands of your email won't be take into consideration.";
	
	//TODO: dire quel choix n'existe pas :
	//-> possible mais chiant : à faire 
}
