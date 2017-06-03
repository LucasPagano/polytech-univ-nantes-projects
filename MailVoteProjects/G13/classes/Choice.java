package classes;

import java.io.Serializable;

public class Choice implements Serializable {
	private int id;
	private String name;

	public Choice(String name) {
		this.id = 0;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	protected void changeID(int id) {
		this.id = id;
	}

}
