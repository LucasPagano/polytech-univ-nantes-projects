package DESSIN_VECTORIEL;

import java.awt.Color;
import java.awt.Graphics;

public class ColoredFigure implements Figure{

	Figure figure;
	Color color;
	
	public ColoredFigure(Figure fig, Color c) {
		figure = fig;
		color = c;
	}
	
	@Override
	public void draw(Graphics g) {
		Color temp = g.getColor();
		g.setColor(color);
		figure.draw(g);
		g.setColor(temp);
	}
	
}
