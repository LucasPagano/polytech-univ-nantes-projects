package Commands;

/**
 * This class represents an User
 * @author Maraval Nathan
 */
public abstract class User {
	private int id;
	//private String name;
	private String email;
	
	/**
	 * @param id the identifier of the user
	 * @param email the e-mail of the user
	 */
	public User(int id, String email) {
		super();
		this.id = id;
		this.email = email;
	}
	
	public abstract void hasVoted();
	public abstract void ationVote();
	public abstract boolean isInitiator();
	
	/**
	 * Getter of the id
	 * @return the identifier of the user
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter of the email
	 * @return the e-mail of the user
	 */
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		String s = "id : " + id + "\n";
		s += "email : " + email + "\n";
		return s;
	}
	
	
}
