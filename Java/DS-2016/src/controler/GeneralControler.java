package controler;

import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractAction;

import model.SimpleModel;
import view.SimpleView;

public class GeneralControler {
	
	SimpleModel model;
	SimpleView view;
	
	public GeneralControler(SimpleModel model) {
		this.model = model;
		this.view = new SimpleView();
		
		MouseMotionListener mouseListener = new MyMouseMotionListener(model, view);
		SimpleControler simpleControler = new SimpleControler(this.view, this.model);
		InitAction init = new InitAction();
		
		view.getInit().setAction(init);
		view.getPositionPanel().addMouseMotionListener(mouseListener);
		model.addObserver(simpleControler);
	}
	
	
	private class InitAction extends AbstractAction{
		
		public InitAction() {
			this.putValue(NAME, "init");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			model.init();
		}
		
	}
}
