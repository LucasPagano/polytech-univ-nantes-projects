package mvcInteger.controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import mvcInteger.model.MyInteger;
import mvcInteger.model.MyRangeException;
import mvcInteger.view.SimpleView;

public class GeneralController {
	
	MyInteger model;
	SimpleView view;
	
	public GeneralController(MyInteger model) {
		this.model = model;
		view = new SimpleView();
		MySimpleController simpleController = new MySimpleController(model, view);
		model.addObserver(simpleController);
		
		view.getPlusButton().addActionListener(new PlusController());
		view.getMinusButton().addActionListener(new MinusController());
	}

	private class PlusController extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.plus();
			} catch (MyRangeException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class MinusController extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.minus();
			} catch (MyRangeException e1) {
				e1.printStackTrace();
			}
		}
	}
}
