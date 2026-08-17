package donneesLocales;

public class ExceptionAlreadyFollows extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExceptionAlreadyFollows(String s) {
		super("****Le client  " + s + " ne peut follower qu'une seule personne.");
	}
	

}
