package controler;

import java.util.Observable;
import java.util.Observer;

import model.ComplexModel;
import model.SimpleModel;
import view.ComplexView;

public class ComplexControler implements Observer {

	ComplexModel model;
	ComplexView view;

	public ComplexControler(ComplexModel model, ComplexView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof SimpleModel){
			this.model.p.setLocation(this.model.calcMean());
			
			StringBuilder str = new StringBuilder();
			str.append("(");
			str.append(this.model.p.getX());
			str.append(", ");
			str.append(this.model.p.getY());
			str.append(")");

			this.view.getField().setText(str.toString());
		}
	}
}
