package liste_chainee;

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


}
