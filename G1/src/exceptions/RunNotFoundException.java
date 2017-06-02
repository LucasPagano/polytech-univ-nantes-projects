package exceptions;

public class RunNotFoundException extends Exception implements ExceptionMessage {

	private double idRunNotFound;
	private static final long serialVersionUID = -9047163014009406465L;

	private String errorMessage;

	public RunNotFoundException(double idRun) {
		this.idRunNotFound = idRun;
		this.errorMessage = "The run " + this.idRunNotFound + ", that you have selected doesn't exist. "
				+ "CAUTION : The other commands of your email won't be taken into consideration.";

	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
