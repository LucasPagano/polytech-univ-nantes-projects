package Exceptions;

public class RunNotFoundException extends Exception {
	private String errorMessage = "One of the runs that you have selected doesn't exist. "
			+ "CAUTION : The other commands of your email won't be take into consideration.";

	public String getErrorMessage() {
		return errorMessage;
	}
	
	//TODO: - dire quelle run n'existe pas 
	//		- renvoyer la liste des runs existantes : question de droits, peut-être pas...
}
