package exceptions;

public class VoteTotalOfTokensIncorrect extends Exception implements ExceptionMessage {

	private int runMaxToken;
	private int userTokensUsed;
	private static final long serialVersionUID = -7778095199749855479L;

	private String errorMessage;

	public VoteTotalOfTokensIncorrect(int runMaxToken, int userTokensUsed) {
		this.runMaxToken = runMaxToken;
		this.userTokensUsed = userTokensUsed;
		this.errorMessage = "Whith the commands VOTE you have used" + this.userTokensUsed + " tokens in the email.\n"
				+ "You should have used exactly " + this.runMaxToken + " in the email.\n"
				+ "You can access the number of tokens assigned in the run with the command STATUS at any time.\n"
				+ "Please refer to the documentation.";

	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
