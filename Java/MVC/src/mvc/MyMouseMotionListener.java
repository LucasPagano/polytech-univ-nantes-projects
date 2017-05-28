package mvc;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MyMouseMotionListener implements MouseMotionListener {

	Model model;

	public MyMouseMotionListener(Model model) {
		this.model = model;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.model.addPosition(e.getPoint());
	}

}
