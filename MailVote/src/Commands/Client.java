package Commands;

import java.util.Map;

/**
 * This class represents a Client
 * @author Maraval Nathan
 */
public class Client extends User {
	private Client follower;
	private Map<Integer, Integer> tokenRepartition;
	//private ? constraints;
	
	/**
	 * @param id the identifier of the user
	 * @param email the e-mail of the user
	 */
	public Client(int id, String email) {
		super(id, email);
		this.follower = null;
	}
	
	/**
	 * Setter of follower
	 * @param follower the new follower
	 */
	public void setFollower(Client follower) {
		this.follower = follower;
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
		return false;
	}
	
	public String followerToString() {
		String s = "";
		if (this.follower == null) {
			s += "Le client ne suit pas un autre client :" + "\n";
		} else {
			s += "Le client suit un autre client :" + "\n";
			s += this.follower.toString();
		}
		return s;
	}
}
