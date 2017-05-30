
import java.awt.Point;
import java.util.Observable;

public class Model extends Observable {
	private Point point;
	
	public Model(){
		this.point = new Point();
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		
		if(this.point.x != point.x || this.point.y != point.y){
			this.point = point;
			this.setChanged();
		}
		
		this.notifyObservers();
	}
	
	@Override
	public String toString() {
		return "("+this.point.x+", "+this.point.y+")";
	}
	
	public void init(){
		this.setChanged();
		this.notifyObservers();
	}
}
