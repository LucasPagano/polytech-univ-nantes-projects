package liste_chainee;

import java.util.List;

public class Main {

	public static void main(String[] args){
		Liste liste = new Cellule(2,new Cellule(4, new Vide()));
		System.out.println(liste);
	}
}
