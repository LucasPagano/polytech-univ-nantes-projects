package exceptions;

import util.HelpMessage;

public class CommandNotAvailableException extends Exception implements ExceptionMessage {

	private static final long serialVersionUID = 1369033840516363546L;

	private String nameCommandNotAvalaible;

	private String errorMessage;

	public CommandNotAvailableException(String command) {
		this.nameCommandNotAvalaible = command;
		this.errorMessage = "Your command " + this.nameCommandNotAvalaible
				+ " doesn't exist or is not written in an available way. \n"
				+ "Please refer to the following help message \n" + HelpMessage.getHelpMessage();

	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
