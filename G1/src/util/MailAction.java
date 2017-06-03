package util;

public class MailAction {

	private boolean mailTobeSaved;
	private boolean newRun;
	private double runID;

	public MailAction() {
		this.mailTobeSaved = false;
		this.newRun = false;
		this.runID = 0;
	}

	public boolean isNewRun() {
		return newRun;
	}

	public void setNewRun(boolean newRun) {
		this.newRun = newRun;
	}

	public double getRunID() {
		return runID;
	}

	public void setRunID(double runID) {
		this.runID = runID;
	}

	public boolean isMailTobeSaved() {
		return mailTobeSaved;
	}

	public void setMailTobeSaved(boolean mailTobeSaved) {
		this.mailTobeSaved = mailTobeSaved;
	}

}
