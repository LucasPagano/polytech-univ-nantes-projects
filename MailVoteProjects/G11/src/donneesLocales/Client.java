package donneesLocales;

import java.util.ArrayList;
import java.util.List;

/**
 * classe gérant les clients et les initiators
 * @author Eva Bourgeais--Boon
 *
 */
public class Client {
	
	
	//INSTANCES 
	
	
	private int id, nbToken;
	private boolean voter;
	private String nom;
	private String adresseMail;
	private List<Client> followers, follow;
	private List<String> choix;
	
	private int decision;
	
	
	//CONSTRUCTEUR
	
	/**
	 * constructeur d'un client ou inititateur
	 * @param id_client
	 * @param nom_client
	 * @param nbToken_client
	 * @param adresseMail_client
	 */
	public Client (int id_client, String nom_client, int nbToken_client, String adresseMail_client) {
		
		this.id = id_client;
		this.nom = nom_client;
		this.nbToken = nbToken_client;
		this.adresseMail = adresseMail_client;
		this.choix = new ArrayList<String>();
		
		this.voter = true;
		this.followers = new ArrayList<Client>();
		this.follow = new ArrayList<Client>();
		
		this.decision = -1; //id du choix décidé pour le client
		
	}
	
	
	//AFFICHAGE
	
	
	/**
	 * surcharge de la méthode toString pour permettre un affichage console plus rapide
	 */
	@Override
	public String toString(){
		return "Client[id: " + this.id + "; nom: " + this.nom + "; adresse mail: " + this.adresseMail + "]";
	}
	
	
	//GETTERS & SETTERS
	
	/**
	 * getteur retournant le nom du client ou initiateur
	 * @return nom du client ou initiateur
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * getteur retournant l'id du client ou initiateur
	 * @return id du client ou initiateur
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * setteur permettant de configurer l'id du client
	 * @param i id du client
	 */
	void setId(int i){
		this.id = i;
	}
	
	/**
	 * getteur retournant l'adresse mail du client
	 * @return l'adresse mail du client
	 */
	public String getAddress(){
		return this.adresseMail;
	}
	
	/**
	 * getteur retournant le nombre de token restants au client
	 * @return nombre de jetons restants
	 */
	public int getNbToken(){
		return this.nbToken;
	}
	
	/**
	 * setteur modifiant le nombre de token du client dans le run
	 * @param nb : le nouveau nombre de jetons restant au client
	 */
	void setNbToken(int nb){		
		this.nbToken = nb;
	}
	
	/**
	 * getteur retournant true si le client peut voter, false si le client est un follower
	 * @return true ou false (resp. pas follower ou follower)
	 */
	public boolean getStatus(){
		return this.voter;
	}
	
	/**
	 * setteur permettant de convertir un client en follower (définitif)
	 */
	void setStatusToFollower(){
		this.voter = false;
	}
	
	/**
	 * getteur retournant la liste des followers du client
	 * @return liste des clients suivant le client concerné
	 */
	public List<Client> getFollowers(){
		return this.followers;
	}
	
	/**
	 * getteur retournant l'ensemble des choix des clients
	 * si la liste des choix est vide, cela signifie que le client n'a pas voté pour l'instant
	 * @return une liste de chaine de caractères avec la description des choix avec le nombre de jetons placés dessus (ou un message "le client n'a pas encore voté sinon)
	 */
	public List<String> getChoices(){
		if (this.choix.size()>0) {
			return this.choix;
		}
		else {
			List<String> err = new ArrayList<String>();
			err.add("Le client n'a pas encore voté");
			return err;
		}
	}
	
	/**
	 * getteur renseignant sur la personne followée par le client
	 * @return la liste de clients followés par le client concerné (0 ou 1)
	 */
	public List<Client> getFollow() {
		return this.follow;
	}
	
	/**
	 * getteur retournant l'id du choix de la décision du client (temporaire ou définitive)
	 * @return id du choix décidé par l'algorithme de décision
	 */
	public int getDecision(){
		return this.decision;
	}
	
	/**
	 * setteur modifiant la valeur de la décision (temporaire ou définitive) du client
	 * @param i : l'id du choix décidé par l'algorithme de décision
	 */
	public void setDecision(int i){
		this.decision = i;
	}
	
	
	
	/**
	 * ajoute le choix du client et le nombre de tokens placés dessus à la liste des choix effectués par l'utilisateur à l'issue d'un vote.
	 * @param description_choix
	 * @param nb_jetons
	 */
	void addChoixClient(String description_choix, int nb_jetons){
		
		this.choix.add(description_choix + " : " + nb_jetons);
	}
	
	/**
	 * fonction rejettant une erreur si l'utilisateur est déjà en train de follower quelqu'un
	 * @throws ExceptionAlreadyFollows
	 */
	void isFollower() throws ExceptionAlreadyFollows{
		
		if (this.voter == false){
			throw new ExceptionAlreadyFollows(this.nom);
		}
	}
	
	/**
	 * ajoute un follower à la liste des followers du client
	 * fonctionne si le client désirant follower n'est pas déjà un follower
	 * @param client ==> le nouveau follower du client
	 */
	void addFollower(Client client){
		try { 
			client.isFollower();
			this.followers.add(client);
		}
		catch (ExceptionAlreadyFollows e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * ajoute le client à follower à la liste des clients followés (vide normalement)
	 * fonctionne uniquement si le client à follower n'est pas déjà un follower (car sinon la situation se bloque)
	 * @param client
	 * @throws ExceptionCantFollowFollower
	 */
	void addFollow(Client client) throws ExceptionCantFollowFollower{
		if (client.getStatus() == true) { //si le client à follower n'est pas un follower
			this.follow.add(client); //on peut valider l'ajout
		}
		else {
			throw new ExceptionCantFollowFollower(this.nom); //sinon non.
		}
	}
	
	/**
	 * fonction réinitialisant la liste des followers.
	 * quand un client décide de follower, il perd tous ses followers
	 */
	void resetFollowers(){ //
		for (int i=0; i<this.followers.size(); i++){
			this.followers.remove(i);
		}
	}
	
	/**
	 * fonction retourne le choix final du client
	 * @return une chaine de caractère du style : "Le client Eva Bourgeais--Boon a le choix final : les madeleines"
	 */
	String finalStatus(){
		return this.nom + " a le choix final : " + this.decision;
	}
	

}
