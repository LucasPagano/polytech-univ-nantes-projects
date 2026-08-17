package exceptions;

public class ChoiceNotFoundException extends Exception implements ExceptionMessage {

	private double idChoiceNotFound;
	private static final long serialVersionUID = 8105967726642213932L;

	private String errorMessage;

	public ChoiceNotFoundException(double idChoice) {
		this.idChoiceNotFound = idChoice;
		this.errorMessage = "Your choice " + this.idChoiceNotFound + " doesn't exist. "
				+ "CAUTION : The other commands of your email won't be taken into consideration.";

	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
