package liste_chainee;

import java.util.Iterator;

public class ListeIter implements Iterator<Integer> {
	
	Liste current;
	
	public ListeIter(Liste l){
		this.current = l;
	}

	public boolean hasNext() {
		return !(current.isEmpty());
	}

	public Integer next() throws UnsupportedOperationException{
		 if (this.hasNext()){
			 int temp = ((Cellule)current).val;
			 current = ((Cellule)current).next;
			 return temp;
		 } else {
			 throw new UnsupportedOperationException();
		 }
	}
}
