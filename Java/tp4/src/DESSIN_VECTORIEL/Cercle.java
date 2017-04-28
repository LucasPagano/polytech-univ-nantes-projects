package DESSIN_VECTORIEL;

import java.awt.Graphics;
public class Cercle implements Figure {
	
	private int x;
	private int y;
	private int rayon;
	
	Cercle(int x, int y, int rayon){
		this.x = x;
		this.y = y;
		this.rayon = rayon;
	}

	@Override
	public void draw(Graphics g){
		g.drawOval(this.x, this.y, this.rayon*2, this.rayon*2);
		
	}
}
