package DESSIN_VECTORIEL;

import javax.swing.* ;
import java.awt.* ;


class MaFenetre extends JFrame {
    MaFenetre(Figure r){
	setSize(320,200+50);
	setContentPane(new Paneau(r)) ;
    }
}


class Paneau extends JPanel {

    Figure p ;

    Paneau(Figure p){
	this.p=p ;
    }

    @Override
    public void paintComponent (Graphics g){
	p.draw(g) ;
    }
    
}


public class Test {

    public static void main(String[] args){

//	Figure fig = new ColoredFigure(new Cercle(10, 10, 100), Color.BLUE);
//	Figure fig = new BoldFigure(new Cercle(10, 10, 100));
//    Figure fig = new Groupe(new BoldFigure(new ColoredFigure(new Cercle(10, 10, 100), Color.BLUE)), new ColoredFigure(new Rectangle(0, 0, 50, 100), Color.RED));
//    Figure fig = new Groupe(new BoldFigure(new Rectangle(3, 0, 50, 100)),
//    		new ColoredFigure(new Cercle(10, 10, 100), Color.RED));

//    Figure fig = new ColoredFigure (new BoldFigure(new Rectangle(3, 0, 50, 100)), Color.red);
    Figure fig = new DashedFigure (new ColoredFigure(new Cercle(3, 0, 50), Color.red));
    	
 	MaFenetre fen = new MaFenetre(fig);
    	
	fen.setVisible(true);
	fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    
}
