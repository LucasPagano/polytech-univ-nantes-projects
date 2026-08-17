package DESSIN_VECTORIEL;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class BoldFigure implements Figure{

	Figure figure;
	Stroke stroke;
	
	public BoldFigure(Figure fig) {
		figure = fig;
		stroke = new BasicStroke(4f);
	}

	@Override
	public void draw(Graphics g) {
		Stroke tempStroke = ((Graphics2D)g).getStroke();
		((Graphics2D)g).setStroke(stroke);
		figure.draw(g);
		((Graphics2D)g).setStroke(tempStroke);
	}
}
