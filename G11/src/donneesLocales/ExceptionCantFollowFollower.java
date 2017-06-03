package donneesLocales;

public class ExceptionCantFollowFollower extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExceptionCantFollowFollower(String s) {
		super("****Le client  " + s + " ne peut pas follower un client déjà follower.");
	}
	

}
