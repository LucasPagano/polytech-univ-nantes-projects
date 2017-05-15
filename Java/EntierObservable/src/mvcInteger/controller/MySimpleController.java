package mvcInteger.controller;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import mvcInteger.model.MyInteger;
import mvcInteger.model.MyRangeException;
import mvcInteger.view.SimpleView;

public class MySimpleController implements Observer {
	SimpleView view;
	MyInteger model;

	public MySimpleController(MyInteger model, SimpleView view) {
		this.view = view;
		this.model = model;

		this.view.getLabel().setText(
				Integer.toString(this.model.getVal()));
		this.view.pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MyInteger) {
			this.view.getLabel().setText(Integer.toString(this.model.getVal()));
			this.view.pack();
		}
	}

}
