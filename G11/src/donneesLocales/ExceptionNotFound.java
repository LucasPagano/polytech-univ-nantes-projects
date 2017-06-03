package donneesLocales;

public class ExceptionNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ExceptionNotFound(String s){
		
		super(s + " n'existe pas.");
	}
	
}
