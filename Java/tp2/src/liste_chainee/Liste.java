package liste_chainee;

import java.util.Iterator;

public abstract class Liste implements Iterable<Integer> {
	abstract int size();
	abstract boolean find(int n);
	abstract int max();

	@Override
	public String toString(){
		return "Liste contenant " + this.size() + " éléments";
	}

	public boolean isEmpty(){return this.size()==0;}
	
	public Iterator<Integer> iterator() {
		return new ListeIter(this);
	}
}
