package Exceptions;

public class CommandNotAvailableException extends Exception {
	private String errorMessage = "One of your commands doesn't exist or is not written in an available way. "
			+ "Please refer yourself to the documentation. "
			+ "CAUTION : The other commands of your email won't be take into consideration.";

	public String getErrorMessage() {
		return errorMessage;
	}
	
	
	//=> SIGNIFIE que la personne n'a pas les droits
	//TODO: METTRE COMMANDE QUI A FOIREE
	
	
	//TODO: - on renvoie un exemple de mail valide (peut-être)
	//      - on rappelle qu'une commande doit n'être que sur une ligne (p-e)
}
