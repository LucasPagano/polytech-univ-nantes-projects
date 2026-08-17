public class Palet implements MovingObject {
	float x;
	float y;

	float vx;
	float vy;

	boolean sortie_x (float px) {return px<0 || px>320;}
	boolean sortie_y (float py) {return py<0 || py>200;}

	public Palet(){
		x = 0;
		y = 0;

		vx = 10;
		vy = 10;
	}

	static int arrondi(float x){return Math.round(x);}

	public int getX(){return arrondi(this.x);}
	public int getY(){return arrondi(this.y);}

	public void deplace(){
		if (sortie_x(this.x + this.vx +5)) {
			this.vx = (-1)*this.vx;
		} else {
			this.x += this.vx;
		}
		if (sortie_y(this.y + this.vy +5)) {
			this.vy = (-1)*this.vy;
		} else {
			this.y += this.vy;
		}

	}

}