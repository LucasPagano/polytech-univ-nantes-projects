package DESSIN_VECTORIEL;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class DashedFigure implements Figure{

	Figure figure;
	Stroke stroke;
	
	public DashedFigure(Figure fig) {
		figure = fig;
		stroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4}, 0);;
	}

	@Override
	public void draw(Graphics g) {
		Stroke tempStroke = ((Graphics2D)g).getStroke();
		((Graphics2D)g).setStroke(stroke);
		figure.draw(g);
		((Graphics2D)g).setStroke(tempStroke);
	}

}
