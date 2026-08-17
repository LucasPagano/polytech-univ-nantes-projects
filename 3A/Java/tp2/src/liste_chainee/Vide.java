package liste_chainee;

import Visiteur.Visiteur;

public class Vide extends Liste {

	public int size() {
		return 0;
	}

	public boolean find(int n) {
		return false;
	}

	public int max() {
		return Integer.MIN_VALUE;
	}

	@Override
	public <T> T admit(Visiteur<T> v) {
		return v.visit(this);
	}

}
