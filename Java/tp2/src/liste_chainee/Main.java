package liste_chainee;

public class Main {

	public static void main(String[] args){
		Liste liste = new Cellule(2,new Cellule(4, new Vide()));
		System.out.println(liste);
		
		System.out.println(Test.longueur(liste));
		System.out.println(Test.longueur2(liste));
	}
}
