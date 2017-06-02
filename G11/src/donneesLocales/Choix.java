package donneesLocales;

/**
 * classe gérant les données des choix
 * @author Eva Bourgeais--Boon
 *
 */
public class Choix {
	
	
	//INSTANCES

	private int id;
	private String description;
	
	
	//CONSTRUCTEUR
	
	/**
	 * constructeur d'un choix
	 * @param id_choix
	 * @param description_choix
	 */
	public Choix(int id_choix, String description_choix) {
		
		this.id = id_choix;
		this.description = description_choix;
	}
	
	
	
	//AFFICHAGE
	
	
	/**
	 * surcharge de la classe toString pur faciliter l'affichage d'un client sur la console
	 */
	@Override
	public String toString(){
		
		return "[id :" + this.id + "; description:" + this.description + "]";
	}
	
	
	//GETTERS & SETTERS
	
	/**
	 * getteur retournant l'id du choix
	 * @return l'id du choix dans le run
	 */
	public int getId(){
		return this.id;
	} 
	
	/**
	 * setteur configurant l'id du choix
	 * @param i l'id du choix
	 */
	public void setId(int i){
		this.id = i;
	}
	
	/**
	 * getteur retournant la description du choix
	 * @return la description du choix dans le run
	 */
	public String getDescription(){
		return this.description;
	}
	
	/**
	 * setteur retournant la description du choix
	 * @param txt la description du choix dans le run
	 */
	public void setDescription(String txt){		//modifie la description du choix
		this.description = txt;
	}

}
