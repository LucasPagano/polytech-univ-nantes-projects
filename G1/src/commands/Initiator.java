package commands;

import javax.mail.internet.AddressException;

/**
 * This class represents an Initiator
 * @author Maraval Nathan
 */
public class Initiator extends User implements Cloneable{

	/**
	 * @param id the identifier of the user
	 * @param email the e-mail of the user
	 * @throws AddressException 
	 */
	public Initiator(double id, String email) throws AddressException {
		super(id, email);
	}

	@Override
	public boolean isInitiator() {
		return true;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
