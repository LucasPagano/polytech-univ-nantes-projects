package commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;

/**
 * This class represents a Client
 * 
 * @author Maraval Nathan
 */

public class Client extends User implements Cloneable {
	private Client followed;
	private Map<Double, Integer> tokenRepartition;
	private int tokensUsed;

	// private ? constraints;

	/**
	 * @param id
	 *            the identifier of the user
	 * @param email
	 *            the e-mail of the user
	 * @throws AddressException 
	 */
	public Client(double id, String email) throws AddressException {
		super(id, email);
		this.followed = null;
		this.tokenRepartition = new HashMap<Double, Integer>();
		this.tokensUsed = 0;
	}
	
	public int getTokensUsed() {
		return this.tokensUsed;
	}
	
	public void setTokensUsed(int tokensUsed) {
		this.tokensUsed = tokensUsed;
	}

	/**
	 * Setter of followed
	 * 
	 * @param followed
	 *            the new followed
	 */
	public void setFollowed(Client followed) {
		this.followed = followed;
	}

	// For the equals
	public Map<Double, Integer> getTokenRepartition() {
		return tokenRepartition;
	}

	public void vote(double idChoice, int numberOfTokens) {
		Integer tokensBefore = tokenRepartition.get(idChoice);
		if (tokensBefore == null) {
			tokensBefore = 0;
		}

		tokenRepartition.put(idChoice, numberOfTokens + tokensBefore);
	}

	@Override
	public boolean isInitiator() {
		return false;
	}
	
	/**
	 * Check if this client has already voted
	 * @return true if this client has voted, false instead
	 */
	public boolean hasVoted() {
		if (tokenRepartition.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Return a String representation of the choices of this client
	 * @return a String representation of tokenRepartition
	 */
	public String choicesToString() {
		String str = "";
		for(Map.Entry<Double, Integer> entry : tokenRepartition.entrySet()) {
			str += "ID : " + entry.getKey() + ", tokens : " + entry.getValue();
		}
		return str;
	}

	public String followerToString() {
		String s = "";
		if (this.followed == null) {
			s += "The client doesn't follow another client :" + "\n";
		} else {
			s += "The client follow another client :" + "\n";
			s += this.followed.toString();
		}
		return s;
	}

	@Override
	public String toString() {
		if (followed == null){
			return this.getEmail() + "(id=" + this.getId() + ") tokenRepartition=" + tokenRepartition + "]";
		}
		return this.getEmail() + "(id=" + this.getId() + " follows " + followed + ", tokenRepartition=" + tokenRepartition + "]";
	}

	public Client getFollowed() {
		return followed;
	}

	/**
	 * Clone a Client but with a superficial clone on follower
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Client clientClone = (Client) super.clone();
		return clientClone;
	}

	public void cloneFollower(List<Client> clientsCloned) {
		if (followed != null) {
			for (Client client : clientsCloned) {
				if (this.followed.getId() == client.getId()) {
					this.followed = client;
					return;
				}
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		Client client = (Client) obj;

		if (super.equals(obj)
				&& tokenRepartition.equals(client.getTokenRepartition())) {

			if ((followed == null && client.getFollowed() != null)
					|| (followed != null && client.getFollowed() == null)) {
				return false;
			}

			if ((followed != null && client.getFollowed() != null)
					&& (followed.getId() != client.getFollowed().getId())) {
				return false;
			}

			return true;
		}
		return false;
	}

}
