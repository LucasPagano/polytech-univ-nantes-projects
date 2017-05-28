package mvc;

import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

public class Model extends Observable {

	public List<Point> positionList;
	public boolean changed;

	public Model() {
		this.positionList = new Vector<Point>();
		this.changed = false;
		System.out.println("Model construit et la liste des positions est : "
				+ this.positionList.toString());
	}

	public void addPosition(Point p) {
		this.positionList.add(p);
		this.changed = true;
		this.setChanged();
		this.notifyObservers(this);
	}

	public Point getLast() {
		return this.positionList.get(this.positionList.size() - 1);
	}
}
