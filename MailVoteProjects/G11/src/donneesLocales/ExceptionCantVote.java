package donneesLocales;

public class ExceptionCantVote extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ExceptionCantVote(String nom){
		
		super("Le client " + nom + " n'a plus assez de jeton pour voter.");
	}
	
}
