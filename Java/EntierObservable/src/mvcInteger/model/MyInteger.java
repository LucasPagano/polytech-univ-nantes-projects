package mvcInteger.model;

import java.util.Observable;

public class MyInteger extends Observable{
	int val;
	static int upperLimit = 10;
	static int lowerLimit = 0;
	
	public MyInteger() {
		this.val = 5;
	}

	public int getVal() {
		return val;
	}
	
	public void plus() throws MyRangeException{
		this.setVal(this.getVal()+1);
	}
	
	public void minus() throws MyRangeException{
		this.setVal(this.getVal()-1);
	}
	
	protected void setVal(int val) throws MyRangeException{
		if (val<lowerLimit || val>upperLimit){
			throw new MyRangeException("Out of bounds");
		} else {
			this.val = val;
			this.setChanged();
			this.notifyObservers();
		}
	}
}
