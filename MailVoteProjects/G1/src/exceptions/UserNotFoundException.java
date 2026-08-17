package exceptions;

public class UserNotFoundException extends Exception implements ExceptionMessage {

	private Double idUserNotFound = 0.0;
	private static final long serialVersionUID = 6521127576443969529L;
	private String emailUserNotFound = "";

	private String errorMessage;

	public UserNotFoundException(double id) {
		this.idUserNotFound = id;
		this.errorMessage = "The user " + this.idUserNotFound + " that you have selected doesn't exist. "
				+ "CAUTION : The other commands of your email won't be taken into consideration.";

	}

	public UserNotFoundException(String email) {
		this.emailUserNotFound = email;
		this.errorMessage = "The user " + this.emailUserNotFound + " that you have selected doesn't exist. "
				+ "CAUTION : The other commands of your email won't be taken into consideration.";
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
