package Graphe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
class Vertex implements Serializable, Cloneable {
	String name;
	Map<Vertex, Double> map;

	public Vertex(String name) {
		this.name = name;
		this.map = new HashMap<Vertex, Double>();
	}

	public Vertex add(Vertex v, Double d) {

		// On s'ajoute à l'autre
		if (!v.map.containsKey(this)) {
			v.map.put(this, d);
		}

		// On ajoute l'auter
		if (!this.map.containsKey(v)) {
			this.map.put(v, d);
		}
		return this;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer(this.name);
		if (this.map.isEmpty()) {
			buf.append(" est isolée.");
		} else {
			buf.append(" est à :\n");
			for (Entry<Vertex, Double> v : this.map.entrySet()) {
				buf.append(v.getValue());
				buf.append("km de ");
				buf.append(v.getKey().name);
				buf.append("\n");
			}
		}
		return buf.toString();
	}
	
	@Override
	protected Object clone() {
		return this;
		
	}
}
