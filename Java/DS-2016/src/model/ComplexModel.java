package model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;

public class ComplexModel extends Observable{
	
	public ArrayList<SimpleModel> modelList;
	public Point2D.Double p;
	
	public ComplexModel(SimpleModel... models) {
		this.modelList = new ArrayList<SimpleModel>();
		this.p = new Point2D.Double(0,0);

		for (SimpleModel model : models){
			this.modelList.add(model);
		}		
	}
	
	public Point2D.Double calcMean(){
		double meanX = 0;
		double meanY = 0;
		for (SimpleModel model : this.modelList){
			meanX += model.getP().getX();
			meanY += model.getP().getY();
		}
		
		Point2D.Double result = new Point2D.Double();
		result.setLocation(meanX/this.modelList.size(), meanY/this.modelList.size());
		
		return result;
		
	}
	
}
