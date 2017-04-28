package DESSIN_VECTORIEL;

import java.awt.Graphics;

public class Groupe implements Figure{
	
	protected Figure figure1;
	protected Figure figure2;
	
	public Groupe(Figure fig1, Figure fig2){
		figure1 = fig1;
		figure2 = fig2;
	}

	@Override
	public void draw(Graphics g) {
		figure1.draw(g);
		figure2.draw(g);
	}
}
