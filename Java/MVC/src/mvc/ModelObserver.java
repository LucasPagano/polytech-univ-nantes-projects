package mvc;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class ModelObserver implements Observer {

	private Complex_view complexView;
	private View view;

	public ModelObserver(Complex_view complex_view, View view) {
		this.complexView = complex_view;
		this.view = view;
	}

	@Override
	public void update(Observable o, Object arg) {
		Point p = ((Model) o).getLast();

		this.complexView.textField.setText("(" + p.x + ", " + p.y + ")");
		this.complexView.listModel.addElement(p.toString());

		this.view.setLocation(p);

	}
}
