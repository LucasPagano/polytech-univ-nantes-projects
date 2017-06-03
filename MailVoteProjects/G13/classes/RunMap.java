package classes;

import java.io.Serializable;
import java.util.HashMap;

public class RunMap extends HashMap<Integer, Run> implements Serializable{
	static final long serialVersionUID = 42L;
	
	public RunMap(){
		super();
	}
}
