package model;

import java.awt.Point;
import java.util.Observable;

public class SimpleModel extends Observable {

	private Point p;

	public SimpleModel() {
		this.p = new Point(0, 0);
	}

	public SimpleModel(Point p) {
		this.p = p;
	}

	public void plus() {
		Point pPlus = new Point();
		pPlus.setLocation(p.getX()+10, p.getY()+10);
		this.setP(pPlus);
	}

	public void moins() {
		Point pMoins = new Point();
		pMoins.setLocation(p.getX()-10, p.getY()-10);
		this.setP(pMoins);
	}
	
	public void init(){
		this.setP(new Point(0,0));
	}
	
	public Point getP(){
		return this.p;
	}
	
	public void setP(Point p){
		this.p = p;
		this.setChanged();
		this.notifyObservers();
	}

}
