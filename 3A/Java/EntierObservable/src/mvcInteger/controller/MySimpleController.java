package mvcInteger.controller;

import java.text.Format;
import java.util.Observable;
import java.util.Observer;

import mvcInteger.format.RomanFormat;
import mvcInteger.model.MyInteger;
import mvcInteger.view.SimpleView;

public class MySimpleController implements Observer {
	SimpleView view;
	MyInteger model;
	Format format;

	public MySimpleController(MyInteger model, SimpleView view) {
		this.view = view;
		this.model = model;
		this.format = new RomanFormat();

		this.view.getLabel().setText(
				this.format.format(this.model.getVal()));

		this.view.pack();
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof MyInteger) {
			this.view.getLabel().setText(
					this.format.format(this.model.getVal()));

			this.view.pack();
		}
	}

}
