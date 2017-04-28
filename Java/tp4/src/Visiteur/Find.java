package Visiteur;

import liste_chainee.Cellule;
import liste_chainee.Vide;

public class Find implements Visiteur<Boolean>{

	int elem;
	public Find(int e) {
		elem = e;
	}
	
	@Override
	public Boolean visit(Vide l) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean visit(Cellule l) {
		return (l.getVal() == this.elem || l.getNext().admit(this));
	}

}
