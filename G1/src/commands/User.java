package commands;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * This class represents an User
 * @author Maraval Nathan
 */
public abstract class User {
	private double id;
	private InternetAddress email;
	
	/**
	 * @param id the identifier of the user
	 * @param email the e-mail of the user
	 * @throws AddressException 
	 */
	public User(double id, String email) throws AddressException {
		super();
		this.id = id;
		this.email = new InternetAddress(email);
		this.email.validate();
	}
	
	public abstract boolean isInitiator();
	
	/**
	 * Getter of the id
	 * @return the identifier of the user
	 */
	public double getId() {
		return id;
	}
	
	/**
	 * Getter of the email
	 * @return the e-mail of the user
	 */
	public InternetAddress getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return email + " (id=" + id + ")";
	}

	@Override
	public boolean equals(Object obj) {
		User user = (User) obj;
		
		if (id == user.getId() &&
				email.equals(user.getEmail())) {

			return true;
		}
		return false;
	}
	
	
}
