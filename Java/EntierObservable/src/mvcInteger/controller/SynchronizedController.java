package mvcInteger.controller;

import java.util.Observable;
import java.util.Observer;

import mvcInteger.model.MyInteger;
import mvcInteger.model.MyRangeException;

public class SynchronizedController implements Observer {
	MyInteger model1;
	MyInteger model2;

	public SynchronizedController(MyInteger model1, MyInteger model2) {
		this.model1 = model1;
		this.model2 = model2;
		model1.addObserver(this);
		model2.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (model1.getVal() + model2.getVal() != 10) {
			if (o == model1) {
				try {
					((MyInteger)model2).setVal(MyInteger.upperLimit - model1.getVal());
				} catch (MyRangeException e) {
					System.err.println(e.getMessage());
				}
			} else if (o == model2) {
				try {
					((MyInteger)model1).setVal(MyInteger.upperLimit - model2.getVal());
				} catch (MyRangeException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
