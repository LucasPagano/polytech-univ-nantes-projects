package Commands;

/**
 * This class represents a Choice.
 * @author Maraval Nathan
 */
public class Choice {
	private int id;
	private String text;
	
	/**
	 * @param id the identifier of the choice
	 */
	public Choice(int id) {
		super();
		this.id = id;
		this.text = "";
	}

	/**
	 * Getter of the id
	 * @return the identifier of this choice
	 */
	public int getId() {
		return id;
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
		String s = "Choix " + id + "\n";
		s += text + "\n";
		return s;
	}
	
	
		
}
