package Commands;

/**
 * This class represents an Initiator
 * @author Maraval Nathan
 */
public class Initiator extends User {

	/**
	 * @param id the identifier of the user
	 * @param email the e-mail of the user
	 */
	public Initiator(int id, String email) {
		super(id, email);
	}

	@Override
	public void hasVoted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ationVote() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInitiator() {
		return true;
	}

}
