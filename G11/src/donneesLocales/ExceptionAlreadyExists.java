package donneesLocales;

public class ExceptionAlreadyExists extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ExceptionAlreadyExists(String s){
		
		super(s + " existe déjà.\n");
	}
}