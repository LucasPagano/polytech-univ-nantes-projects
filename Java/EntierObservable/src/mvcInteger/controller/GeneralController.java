package mvcInteger.controller;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import mvcInteger.i18n.Constants;
import mvcInteger.model.IntegerWithUndo;
import mvcInteger.model.MyInteger;
import mvcInteger.model.MyRangeException;
import mvcInteger.view.SimpleView;

public class GeneralController {

	MyInteger model;
	SimpleView view;

	@SuppressWarnings("serial")
	public GeneralController(MyInteger model) {
		this.model = model;
		this.view = new SimpleView();
		MySimpleController simpleController = new MySimpleController(model,
				view);
		model.addObserver(simpleController);

		PlusController plusAction = new PlusController();
		MinusController minusAction = new MinusController();
		UndoController undoAction = new UndoController();
		RedoController redoAction = new RedoController();


		view.getPlusButton().setAction(plusAction);
		view.getMinusButton().setAction(minusAction);
		view.getUndoButton().setAction(undoAction);
		view.getRedoButton().setAction(redoAction);


		model.addObserver(plusAction);
		model.addObserver(minusAction);
		((IntegerWithUndo)model).getUndoManager().addObserver(undoAction);
		((IntegerWithUndo)model).getUndoManager().addObserver(redoAction);
		
		view.getLabel().setAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					try {
						model.setVal(Integer.parseInt(view.getLabel().getText()));
					} catch (NumberFormatException | MyRangeException e1) {
						System.err.println(e1.getMessage());
					}
				
			}
		});
		
	}

	@SuppressWarnings("serial")
	private class PlusController extends AbstractAction implements Observer {

		public PlusController() {
			this.putValue(NAME, Constants.PLUS_BUTTON_LABEL.translate());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.plus();
			} catch (MyRangeException e1) {
				System.err.println(e1.getMessage());
			}
		}

		@Override
		public void update(Observable o, Object arg) {
			if (o instanceof MyInteger) {
				this.setEnabled(((MyInteger) o).getVal() < MyInteger.upperLimit);
			}
		}
	}

	@SuppressWarnings("serial")
	private class MinusController extends AbstractAction implements Observer {
		public MinusController() {
			this.putValue(NAME, Constants.MINUS_BUTTON_LABEL.translate());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.minus();
			} catch (MyRangeException e1) {
				System.err.println(e1.getMessage());
			}
		}

		@Override
		public void update(Observable o, Object arg) {
			if (o instanceof MyInteger) {
				this.setEnabled(((MyInteger) o).getVal() > MyInteger.lowerLimit);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class UndoController extends AbstractAction implements Observer{

		public UndoController() {
			this.putValue(NAME, Constants.UNDO_BUTTON_LABEL.translate());
			this.setEnabled(false);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			((IntegerWithUndo)model).getUndoManager().undo();
			
		}

		@Override
		public void update(Observable o, Object arg) {
			this.setEnabled(((IntegerWithUndo)model).getUndoManager().canUndo());
		}
		
	}
	
	@SuppressWarnings("serial")
	private class RedoController extends AbstractAction implements Observer{

		public RedoController() {
			this.putValue(NAME, Constants.REDO_BUTTON_LABEL.translate());
			this.setEnabled(false);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			((IntegerWithUndo)model).getUndoManager().redo();
			
		}

		@Override
		public void update(Observable o, Object arg) {
			this.setEnabled(((IntegerWithUndo)model).getUndoManager().canRedo());
		}
		
	}
}
