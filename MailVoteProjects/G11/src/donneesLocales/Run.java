package donneesLocales; 

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import algoDecision.AlgoM;

/**
 * gestion d'un run avec opérations associées
 * @author Eva Bourgeais--Boon
 *
 */

public class Run {
	
	
	//INSTANCE
	
	int id, nbTokenParClient; //id du run
	String name, description; //nom du run et sa description
	List<Choix> tabChoix; //liste des choix
	List<Client> tabClients, tabInitiators; //liste des clients et des initiateurs
	List<List<Integer>> matriceChoix; //matrice des choix des clients 
	
	ArrayList<Integer> idDispoClients = new ArrayList<Integer>(); //les id des clients supprimés seront stockés dans ce tableau
	ArrayList<Integer> idDispoChoix = new ArrayList<Integer>(); //les id des choix supprimés seront stockés dans ce tableau
	ArrayList<Integer> idDispoInitiators = new ArrayList<Integer>(); //les id des initiateurs supprimés seront stockés dans ce tableau
	
	
	//CONSTRUCTEUR 
	
	/**
	 * déclaration d'un nouveau run via le constructeur. 
	 * @param id_run
	 * @param name_run
	 * @param description_run
	 * @param nbTokenParClient_run
	 */
	
	public Run(int id_run, String name_run, String description_run, int nbTokenParClient_run) {
		
		this.id = id_run;
		this.name = name_run;
		this.description = description_run;
		this.nbTokenParClient = nbTokenParClient_run;
		

		this.tabChoix = new ArrayList<Choix>();
		this.tabClients = new ArrayList<Client>();
		this.tabInitiators = new ArrayList<Client>();
																		// Choix 	 1  2         k
		this.matriceChoix = new ArrayList<List<Integer>>();				// Client 1 [x, x , ... , x ]
																		// Client 2 [x, x , ... , x ]
																		// 		   			.
																		// 		   			.
																		// 		   			.
																		// Client n [x, x , ... , x ]
	}
	
	
	//GETTERS & SETTERS
	
	/**
	 * retourne le nom du run
	 * @return nom du run
	 */
	public String getName() {
		return this.name;
	}
	
	void setName(String nom) {	//modifie le nom du run
		this.name = nom;
	}
	
	/**
	 * retourne l'id du run
	 * @return id du run
	 */
	public int getId() {
		return id;
	}
	void setId(int id) {			//modifie l'id du run
		this.id = id;
	}
	
	/**
	 * retourne la description du run
	 * @return description du run
	 */
	public String getDescription(){	
		return this.description;
	}
	void setDescritpion(String txt){		//modifie la description du run
		this.description = txt;
	}
	
	/**
	 * retourne le nombre de jetons par client configuré pour le run
	 * @return nombre de jetons par client
	 */
	public int getNbTokenPerClient(){	
		return this.nbTokenParClient;
	}
	void setNbTokenPerClient(int nb){	//modifie le nombre de token par client configuré pour le run
		this.nbTokenParClient = nb;
	}
	
	/**
	 * renvoie la matrice des choix courante
	 * @return matrice des choix
	 */
	public List<List<Integer>> getMatrice(){
		return this.matriceChoix;
	}
	
	/**
	 * retourne l'ensemble des clients participant au run
	 * @return tableau des clients
	 */
	public List<Client> getTabClients(){
		return this.tabClients;
	}
	
	
	/**
	 * retourne tous les choix possibles du run
	 * @return un tableau des choix
	 */
	public List<Choix> getTabChoix(){
		return this.tabChoix;
	}
	

	/**
	 * retourne tous les initiators du run
	 * @return tableau d'initiator
	 */
	public List<Client> getTabInitiators(){	
		return this.tabInitiators;
	}
	
	
	
	
	//GETTERS & SETTERS ==> GESTION DES SUPPRESSIONS ET DES RECHERCHES DE CLIENTS 
	
	/**
	 * retourne un choix du run s'il existe, à partir de son id.
	 * tous les choix contenus dans le tableau de choix tabChoix ayant le même id que celui renseigné en paramètre sont stockés dans une liste (c_result).
	 * Cette liste est soit de taille 0 (pas de choix trouvé), soit de taille 1 (id unique).
	 * Si c'est de taille 0, alors on lance une erreur. Sinon, c'est qu'il y a une solution, donc on prend le premier et unique élément de la liste de résultats
	 * @param id_choix
	 * @return ce choix s'il est trouvé
	 * @throws ExceptionNotFound sinon
	 */
	public Choix getChoix(int id_choix) throws ExceptionNotFound {
		List<Choix> c_result = (this.tabChoix.stream().filter(x->x.getId()==id_choix).collect(Collectors.toList())); 
		if (c_result.size() > 0) {
			return c_result.get(0);
		}
		else {
			throw new ExceptionNotFound("Le choix dont l'id est " + id_choix);
		}
	}
	
	/**
	 * retourne un choix du run s'il existe, mais cette fois-ci à partir de sa description (surcharge permettant la polyvalence)
	 * @param description
	 * @return ce choix s'il est trouvé
	 * @throws ExceptionNotFound sinon
	 */
	public Choix getChoix(String description) throws ExceptionNotFound {			//retourne un choix du run s'il existe
		List<Choix> c_result = (this.tabChoix.stream().filter(x->x.getDescription().equals(description)).collect(Collectors.toList()));
		if (c_result.size() > 0) {
			return c_result.get(0);
		}
		else {
			throw new ExceptionNotFound("Le choix dont la description est " + description);
		}
	}
	
	/**
	 * retourne un client du run s'il existe, à partir de son id.
	 * tous les clients contenus dans le tableau de client tabClient ayant le même id que celui renseigné en paramètre sont stockés dans une liste (c_result).
	 * Cette liste est soit de taille 0 (pas de client trouvé), soit de taille 1 (id unique).
	 * Si c'est de taille 0, alors on lance une erreur. Sinon, c'est qu'il y a une solution, donc on prend le premier et unique élément de la liste de résultats
	 * @param id_client
	 * @return ce client s'il est trouvé
	 * @throws ExceptionNotFound sinon
	 */
	public Client getClient(int id_client) throws ExceptionNotFound {			//retourne un client du run s'il existe
		List<Client> c_result = (this.tabClients.stream().filter(x->x.getId()==id_client).collect(Collectors.toList())); //affiche les clients dont l'id est égal à  id_client
		if (c_result.size() > 0) { //on a trouvé un élément
			return c_result.get(0);
		}
		else {
			throw new ExceptionNotFound("Le client dont l'id est " + id_client);
		}
	}
	
	
	/**
	 * retourne un client du run s'il existe, mais cette fois-ci à partir de son adresse mail.
	 * @param address
	 * @return ce client s'il existe
	 * @throws ExceptionNotFound sinon
	 */
	public Client getClient(String address) throws ExceptionNotFound {			//retourne un client du run s'il existe
		List<Client> c_result = (this.tabClients.stream().filter(x->x.getAddress().equals(address)).collect(Collectors.toList())); //affiche les clients dont l'adresse mail est égale à address
		if (c_result.size() > 0) { //on a trouvé un élément
			return c_result.get(0);
		}
		else {
			throw new ExceptionNotFound("Le client dont l'adresse mail est " + address);
		}
	}
	
	/**
	 * retourne un initiator du run s'il existe, grâce à son id.
	 * tous les initiator contenus dans le tableau d'initiator tabInitiator ayant le même id que celui renseigné en paramètre sont stockés dans une liste (c_result).
	 * Cette liste est soit de taille 0 (pas d'initiator trouvé), soit de taille 1 (id unique).
	 * Si c'est de taille 0, alors on lance une erreur. Sinon, c'est qu'il y a une solution, donc on prend le premier et unique élément de la liste de résultats
	 * @param id_initiator
	 * @return cet initiator s'il existe
	 * @throws ExceptionNotFound sinon
	 */
	public Client getInitiator(int id_initiator) throws ExceptionNotFound {	//retourne un initiateur du run s'il existe
		List<Client> i_result = (this.tabInitiators.stream().filter(x->x.getId()==id_initiator).collect(Collectors.toList())); //affiche les initiateurs dont l'id est égal à  id_initiator
		if (i_result.size() > 0) {
			return i_result.get(0);
		}
		else {
			throw new ExceptionNotFound("L'initiateur dont l'id est " + id_initiator);
		}
	}
	
	/**
	 * retourne un initiator du run s'il existe, mais cette fois-ci grâce à son adresse mail.
	 * @param address
	 * @return cet initiator s'il existe
	 * @throws ExceptionNotFound sinon
	 */
	public Client getInitiator(String address) throws ExceptionNotFound {	//retourne un initiateur du run s'il existe
		List<Client> i_result = (this.tabInitiators.stream().filter(x->x.getAddress().equals(address)).collect(Collectors.toList())); //affiche les initiateurs dont l'adresse mail est égale à address
		if (i_result.size() > 0) {
			return i_result.get(0);
		}
		else {
			throw new ExceptionNotFound("L'initiateur dont l'adresse mail est " + address);
		}
	}
	
	//GESTION DES AJOUTS REDONDANTS
	
	
	/**
	 * ici on cherche un choix avec sa description, mais cette fonction lève une erreur seulement s'il existe déjà dans le run.
	 * Le but ici est d'utiliser cette fonction lorsque l'on tente d'ajouter un choix qui existe déjà.
	 * Donc s'il existe déjà, on rejette une exception. Sinon, on ne fait rien.
	 * @param description
	 * @throws ExceptionAlreadyExists
	 */
	void findChoixWithDescription(String description) throws ExceptionAlreadyExists {			//retourne une erreur si un choix existe déjà
		List<Choix> c_result = (this.tabChoix.stream().filter(x->x.getDescription().equals(description)).collect(Collectors.toList()));
		if (c_result.size() > 0) {
			throw new ExceptionAlreadyExists("Le choix ayant la description \""+ description + "\"");
		}
	}
	
	/**
	 * de même, on cherche un client avec son adresse mail, mais cette fonction lève une erreur seulement s'il existe déjà dans le run.
	 * Le but ici est d'utiliser cette fonction lorsque l'on tente d'ajouter un client qui existe déjà.
	 * Donc s'il existe déjà, on rejette une exception. Sinon, on ne fait rien.
	 * @param adresse
	 * @throws ExceptionAlreadyExists
	 */
	void doesClientExist(String adresse) throws ExceptionAlreadyExists {			//retourne une erreur si un client existe déjà
		List<Client> c_result = (this.tabClients.stream().filter(x->x.getAddress().equals(adresse)).collect(Collectors.toList())); //affiche les clients dont l'adresse mail est égal à celle renseignée
		if (c_result.size() > 0) { //on a trouvé un élément
			throw new ExceptionAlreadyExists("Le client ayant l'adresse \""+ adresse + "\"");
		}
	}
	
	/**
	 * de même, on cherche un initiator avec son adresse mail, mais cette fonction lève une erreur seulement s'il existe déjà dans le run.
	 * Le but ici est d'utiliser cette fonction lorsque l'on tente d'ajouter un initiator qui existe déjà.
	 * Donc s'il existe déjà, on rejette une exception. Sinon, on ne fait rien.
	 * @param adresse
	 * @throws ExceptionAlreadyExists
	 */
	void doesInitiatorExist(String adresse) throws ExceptionAlreadyExists {	//retourne une erreur si un initiator existe déjà
		List<Client> i_result = (this.tabInitiators.stream().filter(x->x.getAddress().equals(adresse)).collect(Collectors.toList())); //affiche les initiateurs dont l'adresse mail est égal à celle renseignée
		if (i_result.size() > 0) {
			throw new ExceptionAlreadyExists("L'initiator ayant l'adresse \""+ adresse + "\"");
		}
	}
	
	
	//GESTION DE LA MATRICE
	
	/**
	 * Le but de cette fonction est de faire correspondre l'id d'un élément (choix ou client) avec la matrice des choix
	 * en effet, l'id reste constant lorsque l'on supprime un client ou un choix, alors que les indices des choix ou des clients varient lors de la suppression.
	 * du coup, on liste les id des choix (ou clients) supprimés ayant un indice inférieur à celui du choix (ou de l'id) que l'on cherche. 
	 * Par exemple, si on considère la table de client : 0 1 2 3 4, on supprime 3; il restera  0 1 2 4. Donc le client d'id 4 aura un indice 4 - 1 (élément supprimé, le client 3) = 3 dans la matrice des choix.
	 * Aussi, si on supprime le client 2, le client 4 aura comme indice 4 - 2 (éléments supprimés, le client 3 et le client 2) = 2 dans la matrice.
	 * @param id de l'élément concerné
	 * @param tab des id des éléments supprimés
	 * @return l'index de l'élément dans la matrice des choix
	 */
	int findIndexWithIDElement(int id, ArrayList<Integer> tab){
		List<Integer> c_result = (tab.stream().filter(x->x<id).collect(Collectors.toList())); 
		int index = id - c_result.size();
		return index;
	}
	
	/**
	 * Reconfigure la matrice des choix pour ajouter une nouvelle colonne initialisée à  0
	 * nb_lignes : Nombre d'éléments dans une colonne (= nombre de clients = nombre de lignes)
	 * @param id_choix ajouté dans le run
	 */
	void addChoixMatrix(int id_choix){ 
		int nb_lignes = this.tabClients.size();
		for (int i=0; i<nb_lignes; i++){
			this.matriceChoix.get(i).add(id_choix, new Integer(0));		//On initialise à 0
		}
	}
	
	/**
	 * Reconfigure la matrice des choix pour ajouter une nouvelle ligne initialisée à 0
	 * nb_colonnes : Nombre d'éléments dans une ligne (= nombre de choix = nombre de colonnes)
	 */
	void addClientMatrix(int id_client){
		int nb_colonnes = this.tabChoix.size();
		List<Integer> ligne = new ArrayList<Integer>();
		for (int i=0; i<nb_colonnes; i++){
			ligne.add(new Integer(0));			//On initialise à 0
		}
		this.matriceChoix.add(id_client, ligne);
	}
	
	/**
	 * Reconfigure la matrice des choix pour supprimer une colonne (= supprimer un choix)
	 * nb_lignes : Nombre d'éléments dans une colonne (= nombre de clients = nombre de lignes)
	 * @param id_choix que l'on veut supprimer
	 */
	void deleteChoixMatrix(int id_choix){ 
		int index = findIndexWithIDElement(id_choix, this.idDispoChoix); 
		int nb_lignes = this.matriceChoix.size();  
		for (int i=0; i<nb_lignes; i++){
			this.matriceChoix.get(i).remove(index);				//On initialise à  0
		}
	}
	
	/**
	 * Reconfigure la matrice des choix pour supprimer une ligne ( = supprimer un client)
	 * @param id_client que l'on veut supprimer
	 */
	void deleteClientMatrix(int id_client){
		int index = findIndexWithIDElement(id_client, this.idDispoClients); 
		this.matriceChoix.remove(index);
	}
	
	/**
	 * Retrouve le nombre de token placés d'un client particulier pour un choix particulier
	 * @param l id du client
	 * @param c id du choix
	 * @return le nombre de token placés
	 */
	public int getElementMatrix(int l, int c){
		int i = findIndexWithIDElement(l, this.idDispoClients);
		int j = findIndexWithIDElement(c, this.idDispoChoix);
		return this.matriceChoix.get(i).get(j);
	}
	
	
	/**
	 * change la valeur du nombre de token placés pour un client particulier à un choix particulier
	 * @param val le nombre de token placés par le client
	 * @param l l'id du client
	 * @param c l'id du choix
	 */
	void setElementMatrix(int val, int l, int c){
		int i = findIndexWithIDElement(l, this.idDispoClients);
		int j = findIndexWithIDElement(c, this.idDispoChoix);
		this.matriceChoix.get(i).set(j, val);
	}
	
	
	/**
	 * Reconfigure la matrice des choix pour mettre les votes à -1 pour les followers
	 * @param id_follower
	 */
	void setFollowerVotesMatrix(int id_follower){
		int nb_colonnes = this.tabChoix.size();
		int index = findIndexWithIDElement(id_follower, this.idDispoClients);
		for (int i=0; i<nb_colonnes; i++){
			this.matriceChoix.get(index).set(i, 0);			//On initialise à -1
		}
	}
	
	//GESTION DES CHOIX
	
	/**
	 * ajoute un choix possible au run
	 * indice : va servir à donner un identifiant unique au nouveau choix ajouté
	 * Si le tabChoix est plein (pas de case vide), alors le tableau idDispoChoix est vide, donc les indices sont tous pris ==> le choix aura l'indice ((nombre de choix existants - 1) + 1)
	 * Sinon, on prend l'indice du choix supprimé en premier (par défaut) et on supprime cet indice du tableau des id disponibles
	 * Ensuite, on ajoute ce choix au tableau des choix -tabChoix- à l'index correspondant à l'id attribué au nouveau choix.
	 * @param description_choix
	 */
	void addChoix(String description_choix){
		int indice; 
		
		if (this.idDispoChoix.size()==0){
			indice = this.tabChoix.size();
		}
		else {
			indice = this.idDispoChoix.get(0); 
			this.idDispoChoix.remove(0); 
		}
		Choix newChoix = new Choix(indice,description_choix);
		this.tabChoix.add(indice, newChoix);
		this.addChoixMatrix(newChoix.getId());
		
	}
	
	/**
	 * Supprime un choix possible au run
	 * il faut également gérer la matrice des choix, puis on ajoute l'id du choix supprimé dans le tableau d'id des choix supprimés (idDispoChoix)
	 * @param choix
	 */
	void deleteChoix(Choix choix){
		this.deleteChoixMatrix(choix.getId());
		this.idDispoChoix.add(choix.getId());
		this.tabChoix.remove(choix);
	}
	
	
	//GESTION DES CLIENTS 
	
	
	/**
	 * ajoute un client au run
	 * indice : va servir à donner un identifiant unique au nouveau client ajouté
	 * Si le tabClient est plein (pas de case vide), alors le tableau idDispoClients est vide, donc les indices sont tous pris ==> le client aura l'indice ((nombre de clients existants - 1) + 1)
	 * Sinon, on prend l'indice du client supprimé en premier (par défaut) et on supprime cet indice du tableau des id disponibles
	 * Ensuite, on ajoute ce client au tableau des clients -tabClients- à l'index correspondant à l'id attribué au nouveau client
	 * @param nom_client
	 * @param adresseMail_client
	 */
	void addClient(String nom_client, String adresseMail_client){		//ajoute un client au run
		
		int indice;
		
		if (this.idDispoClients.size()==0){ 
			indice = this.tabClients.size();
		}
		else {
			indice = this.idDispoClients.get(0); 
			this.idDispoClients.remove(0); 
		}
		
		Client newClient = new Client(indice, nom_client, this.nbTokenParClient, adresseMail_client);
		this.tabClients.add(indice, newClient);
		this.addClientMatrix(indice);
		
	}
	
	/**
	 * supprime un client du run
	 * il faut également gérer la matrice des choix, puis on ajoute l'id du client supprimé dans le tableau d'id des clients supprimés (idDispoClients)
	 * @param client
	 */
	void deleteClient(Client client){	
		this.deleteClientMatrix(client.getId());
		this.idDispoClients.add(client.getId());
		this.tabClients.remove(client);
	}

	
	
	//GESTION DES INITIATEURS 
	
	/**
	 * ajoute un client au run
	 * indice : va servir à donner un identifiant unique au nouvel initiator ajouté
	 * Si le tabInitiator est plein (pas de case vide), alors le tableau idDispoInitiator est vide, donc les indices sont tous pris ==> l'initiator aura l'indice ((nombre d'initiators existants - 1) + 1)
	 * Sinon, on prend l'indice de l'initiator supprimé en premier (par défaut) et on supprime cet indice du tableau des id disponibles
	 * Ensuite, on ajoute cet initiator au tableau des initiator -tabInitiator- à l'index correspondant à l'id attribué au nouvel initiator
	 * @param nom_initiateur
	 * @param adresseMail_initiateur
	 */
	
	void addInitiator(String nom_initiateur, String adresseMail_initiateur){
		
		int indice;
		
		if (this.idDispoInitiators.size()==0){ 
			indice = this.tabInitiators.size();	
		}
		else {
			indice = this.idDispoInitiators.get(0); 
			this.idDispoInitiators.remove(0);
		}
		
		Client newInitiator = new Client(indice, nom_initiateur, 0, adresseMail_initiateur); 
		this.tabInitiators.add(indice, newInitiator);
		
	}
	/**
	 * supprime un initiateur du run
	 * il faut également rajouter l'id de l'initiateur au tableau des initiators supprimés (idDispoInitioators)
	 * @param initiator
	 */
	void deleteInitiators(Client initiator){
		this.idDispoInitiators.add(initiator.getId());
		this.tabInitiators.remove(initiator);
	}
		
	
	
	//GESTION DES FOLLOWERS
	
	/**
	 * ajoute un follower à un client recherché par son id.
	 * Si le client qui est followé existe et n'est pas un follower, alors on ajoute au client qui follow la personne qu'il souhaite suivre, et au client suivi la personne qui émet la demande de follow
	 * Le client followé hérite du nombre de jetons restant et les followers du client qui le follow, qui du coup perd tous ses jetons et ses followers. On met à jour la matrice à ce sujet
	 * On met à jour le statut de votant à non votant (définitif)
	 * Des excepions sont levées par getClient et addFollow. addFollower gère déjà les exceptions dans sa fonction.
	 * @param id_followed
	 * @param client_who_follows
	 */
	void addFollower(int id_followed, Client client_who_follows){
		
		try { //si le client qui est followé existe
			
			try { //si le client followé n'est pas un follower
				
				Client client_who_is_followed = this.getClient(id_followed);
				client_who_follows.addFollow(client_who_is_followed);
				client_who_is_followed.addFollower(client_who_follows); //alors on peut finalement l'envoyer dans la liste des followers du client trouvé
				
				
				client_who_is_followed.setNbToken(client_who_is_followed.getNbToken() + client_who_follows.getNbToken()); //le client suivi totalise les tokens dispo
				client_who_follows.setNbToken(0); 			//le client qui follow n'a plus de token
				client_who_follows.setStatusToFollower();	//son statut est définitivement changé à voter=false (ne peut plus voter)
				
				setFollowerVotesMatrix(client_who_follows.getId());
				
				for (int i=0; i<client_who_follows.getFollowers().size(); i++){ //HERITAGE DES FOLLOWERS
					client_who_is_followed.addFollower(client_who_follows.getFollowers().get(i)); //même démarche pour tous les followers de la personne qui souhaite follower (C==>B==>A)
					client_who_is_followed.setNbToken(client_who_is_followed.getNbToken() + client_who_follows.getFollowers().get(i).getNbToken());
					client_who_follows.getFollowers().get(i).setNbToken(0);
					client_who_follows.getFollowers().get(i).setStatusToFollower();
				}
				

				client_who_follows.resetFollowers(); //plus de follower pour le client qui veut follower
				
			}
			catch (ExceptionCantFollowFollower ex) {
				System.out.println(ex.getMessage());
			}
		}
		catch (ExceptionNotFound e){
			System.out.println(e.getMessage()); //client n'existe pas
		}
	}
	
	
	/**
	 * ajoute un follower à un client recherché par son adresse mail. (surcharge)
	 * Si le client qui est followé existe et n'est pas un follower, alors on ajoute au client qui follow la personne qu'il souhaite suivre, et au client suivi la personne qui émet la demande de follow
	 * Le client followé hérite du nombre de jetons restant et les followers du client qui le follow, qui du coup perd tous ses jetons et ses followers. On met à jour la matrice à ce sujet
	 * On met à jour le statut de votant à non votant (définitif)
	 * Des excepions sont levées par getClient et addFollow. addFollower gère déjà les exceptions dans sa fonction.
	 * @param id_followed
	 * @param client_who_follows
	 */
	void addFollower(String address_followed, Client client_who_follows){
		
		try { //si le client qui est followé existe
			
			Client client_who_is_followed = this.getClient(address_followed);
			
			client_who_is_followed.addFollower(client_who_follows); //alors on peut finalement l'envoyer dans la liste des followers du client trouvé
			client_who_is_followed.setNbToken(client_who_is_followed.getNbToken() + client_who_follows.getNbToken()); //le client suivi totalise les tokens dispo
			client_who_follows.setNbToken(0); 			//le client qui follow n'a plus de token
			client_who_follows.setStatusToFollower();	//son statut est définitivement changé à voter=false (ne peut plus voter)
			
			setFollowerVotesMatrix(client_who_follows.getId());
			
			for (int i=0; i<client_who_follows.getFollowers().size(); i++){ //HERITAGE DES FOLLOWERS
				client_who_is_followed.addFollower(client_who_follows.getFollowers().get(i)); //même démarche pour tous les followers de la personne qui souhaite follower (C==>B==>A)
				client_who_is_followed.setNbToken(client_who_is_followed.getNbToken() + client_who_follows.getFollowers().get(i).getNbToken());
				client_who_follows.getFollowers().get(i).setNbToken(0);
				client_who_follows.getFollowers().get(i).setStatusToFollower();
			}
			
			client_who_follows.resetFollowers(); //plus de follower pour le client qui veut follower
		}
		catch (ExceptionNotFound e){
			System.out.println(e.getMessage()); //client n'existe pas
		}
	}
	
	
	/**
	 * Ajoute un nombre de jetons de la part d'un client particulier à un choix 
	 * Si le client n'a pas assez de jetons, la fonction lève une exception.
	 * Sinon, on modifie la matrice correctement et on déduit le nombre de jetons placés.
	 * On ajoute également la description et le nombre de jetons placés dans les données du clients pour être récupérés dans un STATUS
	 * @param c : le client qui vote
	 * @param choix : le choix visé
	 * @param nb_jetons : le nombre de jetons à appliquer
	 * @throws ExceptionCantVote
	 */
	void addVote(Client c, Choix choix, int nb_jetons) throws ExceptionCantVote {
		
		if (c.getNbToken() < nb_jetons) {
			
			throw new ExceptionCantVote(c.getNom());
		}
		
		else {

			setElementMatrix(nb_jetons, c.getId(), choix.getId());

			c.setNbToken(c.getNbToken() - nb_jetons);
			c.addChoixClient(choix.getDescription(), nb_jetons);

		}
	}
	
	
	/**
	 * permet de retourner la décision temporaire du run (commande STATUS)
	 * Sous réserve qu'il n'y ait pas assez de projet pour les clients, on calcule la matrice finale grace à l'algorithme hongrois.
	 * Puis, on parcourt la table des clients et on déduit que s'il est un voteur = pas follower, alors on peut lui associer la décision (dans la matrice), puis on parcourt la table de ses follower qui héritent de son choix.
	 * Enfin, on parcourt une deuxieme fois la matrice de décision pour ajouter à la liste des résultats temporaire les décisions pour chaque client
	 * @return la liste des décisions temporaire (chaines de caractères)
	 * @throws ExceptionImpossible
	 */
	List<String> getDecision() throws ExceptionImpossible {
		
		if (this.tabClients.size() > tabChoix.size()) { //L'ALGORITHME NE FONCTIONNE PAS S'IL Y A TROP DE CLIENTS POUR DES CHOIX
			throw new ExceptionImpossible(); 
		}
		else {
			int[][]m = AlgoM.algo(this.matriceChoix);
			AlgoM.afficherMat(m);
			
			int i = 0;
			List<String> list = new ArrayList<String>();
			
			for (Client c : this.tabClients){
				if (c.getStatus() == true) {
					m[i][0] = c.getId();
					for(Client c1 : c.getFollowers()){
						int j = findIndexWithIDElement(c1.getId(), this.idDispoClients);
						m[j][1] = m[i][1];
					}
				}
		
				i++;
			}
			
			
			i = 0;
			
			for(Client c : this.tabClients){
				
				list.add("Le client " + c.getNom() + " aura le choix " + m[i][1]);
				i++;
			}
			return list;
		}
	}
	
	/**
	 * permet de retourner la décision finale du run (après l'avoir clos avec la commande ENDRUN)
	 * Sous réserve qu'il n'y ait pas assez de projet pour les clients, on calcule la matrice finale grace à l'algorithme hongrois.
	 * Puis, on parcourt la table des clients et on déduit que s'il est un voteur = pas follower, alors on peut lui associer la décision (stockée dans Client.decision), puis on parcourt la table de ses follower qui héritent de son choix.
	 * Enfin, on appelle la fonction décision qui enverra toutes les décisions finales stockées dans une liste de String
	 * @return
	 * @throws ExceptionImpossible
	 */
	List<String> sendDecision()  throws ExceptionImpossible {
		
		if (this.tabClients.size() > this.tabChoix.size()) {
			throw new ExceptionImpossible();
		}
		else {
			int[][]m = AlgoM.algo(this.matriceChoix);
			int i = 0;
			for (Client c : this.tabClients){ //on parcourt les clients de la base de donnée
				
				if (c.getStatus() == true) { //si ce client est un voteur != follower
					c.setDecision(m[i][1]);  //alors on peut lui attribuer la décision établie par l'algorithme
					for(Client c1 : c.getFollowers()){ //et on regarde s'il y a des follower
						c1.setDecision(c.getDecision()); //et on donne le même choix final
					}
				}
			i++; //parcours de la matrice
			}
			
			return this.decisions();
		}
	}
	
	/**
	 * Fonction appelée à la fin d'un run pour ajouter la décision finale pour chaque client
	 * @return la liste des décisions finales (description du choix associé à un nom de client)
	 */
	private List<String> decisions(){
		List<String> l = new ArrayList<String>();
		for(Client c : this.tabClients){
			l.add(c.finalStatus());
		}
		
		return l;
	}
}
