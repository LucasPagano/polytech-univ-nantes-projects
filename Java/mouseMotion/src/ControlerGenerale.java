
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

public class ControlerGenerale {
	
	private List<Model> models;
	private VueGenerale vueGenerale;
	
	public ControlerGenerale(List<Model> models){
		this.models = models;
		
		this.vueGenerale = new VueGenerale();
		
		Controler controler;
		for(Model model : this.models){
			controler = new Controler(model);
			this.vueGenerale.addVue(controler.vue);
		}
		
		ActionPlus actionPlus = new ActionPlus();
		ActionMoins actionMoins = new ActionMoins();
		UpdatePosition updatePosition = new UpdatePosition();
		
		this.vueGenerale.menu.add(actionPlus);
		this.vueGenerale.menu.add(actionMoins);
		
		this.vueGenerale.buttonPlus.setAction(actionPlus);
		this.vueGenerale.buttonMinus.setAction(actionMoins);
		
	
		
		this.vueGenerale.frame.pack();
		
		for(Model model: this.models){
			model.addObserver(updatePosition);
			model.init();
		}
	}
	
	
	public class ActionPlus extends AbstractAction {
		public ActionPlus(){
			super(I18n.resourceBundle.getString(I18n.PLUS));
			//this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			for(Model model: models){
				model.setPoint(new Point(model.getPoint().x+10, model.getPoint().y+10));
			}
			
		}
	}
	
	public class ActionMoins extends AbstractAction {
		public ActionMoins(){
			super(I18n.resourceBundle.getString(I18n.MINUS));
			//this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			for(Model model: models){
				model.setPoint(new Point(model.getPoint().x-10, model.getPoint().y-10));
			}
			
		}
	}
	
	public class UpdatePosition implements Observer {

		@Override
		public void update(Observable o, Object arg) {
			Point average = new Point();
			Point pointModel = null;
			
			for(Model model : models){
				pointModel = model.getPoint();
				average.x += pointModel.x;
				average.y += pointModel.y;
			}
			
			average.x = (int)((float)average.x) / models.size();
			average.y = (int)((float)average.y) / models.size();
			
			vueGenerale.textField.setText("("+average.x+", "+average.y+")");
		}
		
		
	}
}
