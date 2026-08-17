package Graphe;

import java.util.ArrayList;
import java.util.List;

public class Map{
	List<Vertex> townList;

	public Map(){
		townList = new ArrayList<Vertex>();
	}

	public void add(Vertex v){
		this.townList.add(v);
	}
	
}
