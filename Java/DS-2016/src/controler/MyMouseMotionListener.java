package controler;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.SimpleModel;
import view.SimpleView;

public class MyMouseMotionListener implements MouseMotionListener {

	SimpleModel model;
	SimpleView view;

	public MyMouseMotionListener(SimpleModel model, SimpleView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();
		this.model.setP(p);
	}

}
