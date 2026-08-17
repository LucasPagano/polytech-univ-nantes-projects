package S3_FREEMIUM;

import java.util.NoSuchElementException ;

public class BanqueMusique {
    
    private BanqueMusique(){}


    /** Get music files based on their index.

	@exception Forbidden The requested file is not available in the country.

	@exception NoSuchElementException The requested file does not exist.
    */

    public static String getFile(int i) throws Forbidden {

	if (i==4) throw new Forbidden() ;
	if (i>10) throw new NoSuchElementException() ;

	return "File number " + i + "." ;
	    }

    static final String messagePub = "Devenez membre Premium, c'est mieux !" ;

    public String ajoutePublicite(String s){
	return messagePub + s ;
}

} 
	
	
