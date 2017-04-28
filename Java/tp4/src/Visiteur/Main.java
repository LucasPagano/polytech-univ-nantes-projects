package Visiteur;

import liste_chainee.Cellule;
import liste_chainee.Liste;
import liste_chainee.Vide;

public class Main {

	public static void main(String[] args) {
		Visiteur<?> longueur = new Longueur();
		Visiteur<?> find = new Find(2);
		
		Liste liste = new Cellule(2,new Cellule(4, new Vide()));
		
		System.out.println(liste.admit(longueur));
		System.out.println(liste.admit(find));

	}

}
