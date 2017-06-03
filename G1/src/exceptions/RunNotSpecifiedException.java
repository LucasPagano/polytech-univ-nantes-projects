package exceptions;

public class RunNotSpecifiedException extends Exception implements ExceptionMessage {

	private String nameCommand;
	private static final long serialVersionUID = -6599987091887147863L;

	private String errorMessage;

	public RunNotSpecifiedException(String command) {
		this.nameCommand = command;
		this.errorMessage = "In order to use the command " + this.nameCommand
				+ " you must specify the run with the command RUN before.\n" + "Please refer to the documentation.";
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
