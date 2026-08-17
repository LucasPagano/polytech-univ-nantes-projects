package DESSIN_VECTORIEL;

import java.awt.Graphics ;


public class Rectangle implements Figure{

    int x  ;
    int y  ;

    int largeur  ;
    int hauteur ;

    public Rectangle(int _x, int _y, int l, int h){
	x = _x ;
	y = _y ;
	largeur = l;
	hauteur = h ;
    }

	@Override
    public void draw(Graphics g){
		g.drawRect(x,y,largeur,hauteur);
    }
    
}
