package donneesLocales;

public class ExceptionImpossible extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ExceptionImpossible(){
		
		super("Impossible de calculer les décisions : il y a trop de clients par rapport au projet");
	}
}