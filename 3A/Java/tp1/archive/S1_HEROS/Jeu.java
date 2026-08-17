class Victoire extends Exception { 
    String nom_gagnant;
    Victoire(String s){ nom_gagnant = s ; }
}


public class Jeu {
    
    static String joueurA ;
    static String joueurB ;

    static Heros[] equipeA = new Heros[3] ;
    static Heros[] equipeB = new Heros[3] ;

    static boolean actif(Heros[] equipe){
	for (Heros h : equipe) {
	    if (h.actif()) return true ;
	}
	return false ;
    }

    static boolean perdA(){
	return !actif(equipeA) ;
    }

    static boolean perdB(){
	return !actif(equipeB) ;
    }
    
    static void init(){
	equipeA[0] = new Heros ("TwentyA", 20, 20, 15, 20) ;
	equipeA[1] = new Heros ("EighteenA", 18, 20, 15, 12) ;
	equipeA[2] = new Heros ("ToneA", 20, 21, 18, 18) ;
	equipeB[0] = new Heros ("EightenB", 18, 20, 15, 12) ;
	equipeB[1] = new Heros ("TwentyB", 20, 20, 15, 20) ;
	equipeB[2] = new Heros ("ToneB", 20, 21, 18, 18) ;
    }

    static Heros choose_in_team(Heros[] tab){
	Heros tmp = null ;
	for (int i=0 ; i<tab.length ; i++){
	    if (tab[i].actif()) {
		tmp = tab[i] ;
		message(i + " : " + tab[i]) ;
	    }
	}
	if (tmp == null) { throw new RuntimeException(); }
	return tmp ;
	
    }

    static void message(String s){
	System.out.println(s) ;
    }

    static void newline(){
	System.out.println("");
    }

    static void tourA() throws Victoire {
	message("Tour de " + joueurA + ".");
	message("Choisir attaquant.");
	Heros a = choose_in_team(equipeA);
	message("Choisir cible.");
	Heros c = choose_in_team(equipeB);
	message (a + " attaque " + c + ".");
	a.attaque(c);
	newline();

	if (perdA()) throw new Victoire(joueurB);
	if (perdB()) throw new Victoire(joueurA);
    }

    static void tourB() throws Victoire {
	message("Tour de " + joueurB + ".");
	message("Choisir attaquant.");
	Heros a = choose_in_team(equipeB);
	message("Choisir cible.");
	Heros c = choose_in_team(equipeA);
	message (a + " attaque " + c + ".");
	a.attaque(c);
	newline();

	if (perdB()) throw new Victoire(joueurA);
	if (perdA()) throw new Victoire(joueurB);

    }
	

    public static void main(String[]args){
	if (args.length < 2) {
	    System.out.println("Arguments : noms des deux joueurs");
	    System.exit(-1);
	}
	joueurA = args[0] ;
	joueurB = args[1] ;
	init();
	
	try{
	    while(true){
		tourA();
		
		tourB();
	    }
	}
	catch (Victoire v) {
	    message("Fin de partie, victoire de " + v.nom_gagnant + " !");
	}

    }
}
	
