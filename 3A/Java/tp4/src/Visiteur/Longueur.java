package Visiteur;

import liste_chainee.Cellule;
import liste_chainee.Vide;

public class Longueur implements Visiteur<Integer>{

	@Override
	public Integer visit(Vide l) {
		return 0;
	}

	@Override
	public Integer visit(Cellule l) {
		return 1 + l.getNext().admit(this);
	}
}
