package exceptions;

public class UserNotSpecifiedException extends Exception implements ExceptionMessage {

	private String nameCommand;
	private static final long serialVersionUID = -2471319699588236858L;

	private String errorMessage;

	public UserNotSpecifiedException(String command) {
		this.nameCommand = command;
		this.errorMessage = "In order to use the command " + this.nameCommand
				+ " you must identificate yourself with the command USER before.\n"
				+ "Please refer yourself to the documentation.";

	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
