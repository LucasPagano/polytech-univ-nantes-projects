
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class Controler {

	public Vue vue;
	private Model model;
	
	public Controler(Model model){
		
		this.model = model;
		this.vue = new Vue();
		
		ActionInit actionInit = new ActionInit();
		UpdatePosition updatePosition = new UpdatePosition();
		MotionListener mouseListener = new MotionListener();
		
		this.vue.buttonInit.setAction(actionInit);
		this.model.addObserver(updatePosition);
		this.vue.panelMove.addMouseMotionListener(mouseListener);
	}
	
	public class ActionInit extends AbstractAction {

		public ActionInit(){
			super(I18n.INIT);
			this.putValue(Action.SHORT_DESCRIPTION, I18n.resourceBundle.getString(I18n.HELPBUTTON));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.setPoint(new Point(0,0));
		}
		
	}
	
	public class UpdatePosition implements Observer {

		@Override
		public void update(Observable arg0, Object arg1) {
			vue.textField.setText(model.toString());
		}
		
	}
	
	public class MotionListener implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			
			model.setPoint(arg0.getPoint());
			
		}
		
	}
}
