package controler;

import java.util.Observable;
import java.util.Observer;

import model.SimpleModel;
import view.SimpleView;

public class SimpleControler implements Observer{

	SimpleModel model;
	SimpleView view;
	
	public SimpleControler(SimpleView view, SimpleModel model) {
		this.view = view;
		this.model = model;
	}
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof SimpleModel){
			StringBuilder str = new StringBuilder();
			str.append("(");
			str.append(((SimpleModel) o).getP().getX());
			str.append(", ");
			str.append(((SimpleModel) o).getP().getY());
			str.append(")");

			this.view.getField().setText(str.toString());
		}
		
		
	}

}
