package Exceptions;

public class UserNotFoundException extends Exception {
	private String errorMessage = "One of the users that you have selected doesn't exist. "
			+ "CAUTION : The other commands of your email won't be take into consideration.";

	public String getErrorMessage() {
		return errorMessage;
	}
	
	//TODO: - dire quel user n'existe pas 
	//		- renvoyer la liste des users existantes : question de droits, peut-être pas...
	
}
