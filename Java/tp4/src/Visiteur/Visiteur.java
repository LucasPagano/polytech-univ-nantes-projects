package Visiteur;

import liste_chainee.Cellule;
import liste_chainee.Vide;

public interface Visiteur<T> {
	public T visit(Vide l);
	public T visit(Cellule l);
}
