package liste_chainee;

public class Cellule extends Liste {
	Integer val;
	Liste next;

	public Cellule(int val, Liste next){
		this(val);
		this.next = next;
	}

	public Cellule(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public Liste getNext() {
		return next;
	}

	public void setNext(Liste next) {
		this.next = next;
	}

	public int size() {
		return 1 + this.next.size();

	}

	public boolean find(int n) {
		if (this.val == n){
			return true;
		} else{
			return this.next.find(n);
		}
	}

	public int max() {
		int tmp = this.next.max();
		if (tmp > this.val){
			return tmp;
		}else{
			return this.val;
		}
	}
}
