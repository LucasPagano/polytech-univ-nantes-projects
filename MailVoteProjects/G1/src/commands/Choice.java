package commands;

/**
 * This class represents a Choice.
 * @author Maraval Nathan
 */
public class Choice implements Cloneable{
	private double id;
	private String text;
	
	public Choice(double id, String name) {
		super();
		this.id = id;
		this.text = name;
	}


	/**
	 * Getter of the id
	 * @return the identifier of this choice
	 */
	public double getId() {
		return id;
	}

	/**
	 * Getter of the text
	 * @return the text of this choice
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter of the text
	 * @param text the new description of this choice
	 */
	public void setText(String text) {
		this.text = text;
	}



	@Override
	public String toString() {
		return text + " (id=" + id + ")";
	}

	/**
	 * Clone a Choice
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		Choice choice = (Choice) obj;
		
		if (id == choice.getId() &&
				text.equals(choice.getText())) {
			return true;
		}
		return false;
	}
	
	
}
