package donneesLocales;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * gestion des données traitées par le parseur
 * @author Eva Bourgeais--Boon
 *
 */

public final class Data { //données interprêtées par le parseur
	
	
	//ATTRIBUT DE NOTRE SINGLETON
	
	
	
    private static volatile Data instance = null;
    
	/**
	 * Constructeur prive pour le patron singleton.
	 */
	private Data() {
		super();
	}
	
    public final static Data getInstance() {
    	

        if (Data.instance == null) {
           synchronized(Data.class) {
             if (Data.instance == null) {
               Data.instance = new Data();
             }
           }
        }
        return Data.instance;
    }

    
	//INSTANCES 
	
	
	public ArrayList<Run> tabRun = new ArrayList<Run>(); //tableau des runs en cours, pour permettre l'unicitÃ© de leur id
	public ArrayList<Integer> idDispo = new ArrayList<Integer>(); //les id des Run supprimés seront stockés dans ce tableau
	
	
	//GETTERS
	
	 /**
	  * Retourne l'ensemble des run lancés sur la base de données
	  * @return le tableau des run
	  */
	public ArrayList<Run> getTabRun(){
		
		return this.tabRun;
	}
	
	
	//CREATERUN
	
	
	/**
	 * fonction créant un nouveau run à partir des paramètres renseignés
	 * @param name_run 
	 * 				l'intitulé du run
	 * @param description_run
	 * 				la description du run
	 * @param nbToken_run
	 * 				le nombre de jetons disponibles pour chaque client
	 * @param nom_initiators
	 * 				le nom des initiators
	 * @param adressesInitiators_run
	 * 				les adresses e-mail des inititateurs
	 */
	public int createRun(String name_run, String description_run, int nbToken_run, ArrayList<String> nom_initiators, ArrayList<String> adressesInitiators_run) {
		
		int indice; //va servir à donner un identifiant unique au nouveau Run créé
		
		if (idDispo.size()==0){ //Si le tabRun est plein (pas de case vide)
			indice = tabRun.size();	//les indices sont tous pris ==> le Run aura l'indice n+1;
		}
		else {
			indice = idDispo.get(0); //On prend l'indice du run fermÃ© en premier (par dÃ©faut)
			idDispo.remove(0); //on supprime la case du tableau
		}
		
		Run newRun = new Run(indice, name_run, description_run, nbToken_run); //crÃ©ation du Run
		this.tabRun.add(indice, newRun); //on ajoute ce Run au tableau des runs en cours, permet de garder le tableau triÃ©
		
		for (int i = 0; i<adressesInitiators_run.size(); i++){ //on renseigne le tableau des initiateurs avec les donnÃ©es nÃ©cessaires
			newRun.tabInitiators.add(new Client(i, nom_initiators.get(i), nbToken_run, adressesInitiators_run.get(i)));
		}
		
		return newRun.id;
	}
	
	
	//TROUVER UN RUN    //RUNIDENTIFIER
	
	/**
	 * cherche un run pour voir s'il existe
	 * Pour cela, on parcourt le tableau des run et on ajoute tous les run ayant le même id que celui recherché dans un nouveau tableau de run (tabRunTrie)
	 * Il y a soit 0 éléments (pas de correspondance, le run n'a pas été trouvé) ==> on rejet une erreur Exception not Found
	 * Soit 1 élément : on identifie le run concerné
	 * @param id_run
	 * @return le run s'il existe
	 * @throws ExceptionNotFound sinon
	 */
	public Run findRun(int id_run) throws ExceptionNotFound{
		
		List<Run> tabRunTrie = this.tabRun.stream().filter(x->x.getId()==id_run).limit(1).collect(Collectors.toList()); 
		
		if (tabRunTrie.size() > 0) {
			return tabRunTrie.get(0); 
		}
		
		else {
			throw new ExceptionNotFound("run ayant pour id " + id_run);
		}
	}
	

	//CHANGER NB TOKENS  //SETTOKENS
	
	/**
	 * met à jour le nombre de jetons par client pour un run particulier s'il existe. Sert UNIQUEMENT lors de l'initialisation du run (usage unique).
	 * Si le run n'existe pas, on affiche une erreur.
	 * @param id_run
	 * @param nbTokens
	 */
	public void setTokens(int id_run, int nbTokens) {
		
		try {
			
			Run run = findRun(id_run);
			run.setNbTokenPerClient(nbTokens);
		
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		}	
			
	}
	
	
	//NOUVEAU(X) CLIENT(S) //ADDCLIENT
	
	/**
	 * ajoute un ou plusieurs nouveau(x) client(s) à un run, s'il existe
	 * Si le run n'existe pas, on affiche une erreur.
	 * Si le client existe déjà pour ce run, on ne le rajoute pas et on affiche une erreur également.
	 * @param id_run
	 * @param noms_clients
	 * @param adressesMail_clients
	 */
	public void addClients(int id_run, ArrayList<String> noms_clients, ArrayList<String> adressesMail_clients) {
		
		try {
			
			Run run = findRun(id_run);
		
			for (int i=0; i < noms_clients.size(); i++) {
	
				try {
					run.doesClientExist(adressesMail_clients.get(i));
					run.addClient(noms_clients.get(i), adressesMail_clients.get(i));
				}
				catch (ExceptionAlreadyExists e){
					System.out.println(e.getMessage());
				}
			}
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//SUPPRIMER CLIENT //DELCLIENT
	
	
	/**
	 * fonction permettant de supprimer un ou plusieurs clients d'un run à partir de son ID
	 * Si le run n'existe pas et si le client n'existe pas dans le run, la fonction détecte une erreur et ne fait rien.
	 * Sinon, on cherche chaque client dans la liste et on le supprime.
	 * @param id_run
	 * @param ids_clients
	 */
	public void deleteClientWithID(int id_run, ArrayList<Integer> ids_clients) {
		
		try {
			
			Run run = findRun(id_run);
		
			for (int i=0; i < ids_clients.size(); i++){
				try {
					Client client_to_delete = run.getClient(ids_clients.get(i));
					run.deleteClient(client_to_delete);
				}
				catch (ExceptionNotFound e){
					System.out.println(e.getMessage());
				}
			}
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		} 
	}
	
	/**
	 * fonction permettant de supprimer un ou plusieurs clients d'un run à partir de son ID
	 * Si le run n'existe pas et si le client n'existe pas dans le run, la fonction détecte une erreur et ne fait rien.
	 * Sinon, on cherche chaque client dans la liste et on le supprime.
	 * @param id_run
	 * @param adressesMail_clients
	 */
	public void deleteClientWithAddress(int id_run, ArrayList<String> adressesMail_clients) { //COMMENT FAIRE ??
		
		try {
			
			Run run = findRun(id_run);
		
			for (int i=0; i < adressesMail_clients.size(); i++){
				try {
					Client client_to_delete = run.getClient(adressesMail_clients.get(i));
					run.deleteClient(client_to_delete);
				}
				catch (ExceptionNotFound e){
					System.out.println(e.getMessage());
				}
			}
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		} 
	}
	
	
	
	
	//NOUVEAU(X) INITIATOR(S) //ADDINITIATOR
	
	/**
	 * fonction rajoutant de nouveaux inititators dans un run
	 * Il faut que le run existe et que l'initiator n'ait pas déjà été ajouté, sinon une exception est détectée dans doesInitiatorExist et findRun
	 * @param id_run
	 * @param noms_initiators
	 * @param adressesMail_initiators
	 */
	public void addInitiators(int id_run, ArrayList<String> noms_initiators, ArrayList<String> adressesMail_initiators) {
		
		try {
			
			Run run = findRun(id_run);
			
			for(int i=0; i<noms_initiators.size(); i++){

				try{
					run.doesInitiatorExist(adressesMail_initiators.get(i)); //test de l'existence du client
					run.addInitiator(noms_initiators.get(i), adressesMail_initiators.get(i));
				}
				catch (ExceptionAlreadyExists e){
					System.out.println(e.getMessage());
				}
				
			}
			
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		} 

	}
	
	//SUPPRIMER INITIATOR //DELINITIATOR
	
	/**
	 * permet de supprimer un ou plusieurs initiator(s) d'un run particulier
	 * Le run et l'initiator doivent exister, sinon une exception est détectée (findRund et getInitiator)
	 * @param id_run
	 * @param ids_initiators
	 */
	public void deleteInitiators(int id_run, ArrayList<Integer> ids_initiators) {
		
		try {
			
			Run run = findRun(id_run);
			
			for (int i=0; i < ids_initiators.size(); i++) {
				try {
					Client initiator_to_delete = run.getInitiator(ids_initiators.get(i));
					run.deleteInitiators(initiator_to_delete);
				}
				catch (ExceptionNotFound e){
					System.out.println(e.getMessage());
				}		
			}
			
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		} 
	}
	
	
	
	//AJOUTER UN CHOIX   //ADDCHOICE 
	
	/**
	 * permet de rajouter un ou plusieurs choix dans un run particulier
	 * le run doit exister et le choix ne doit pas déjà avoir été rentré, sinon une exception est détectée (findRun et findChoixWithDescription)
	 * @param id_run
	 * @param descriptions
	 */
	public void addChoix(int id_run, ArrayList<String> descriptions) {
		
		try {
			
			Run run = findRun(id_run);
			
			for (int i=0; i < descriptions.size(); i++) {
				
				try{
					run.findChoixWithDescription(descriptions.get(i));
					run.addChoix(descriptions.get(i));
				}
				catch (ExceptionAlreadyExists e){
					System.out.println(e.getMessage());
				}
			}
			
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		} 
	}
	
	
	//SUPPRIMER CHOIX  //DELCHOICE
	
	/**
	 * permet de supprimer un ou plusieurs choix dans un run particulier
	 * le run et le choix doivent exister, sinon une exception est détectée (findRun et getChoix)
	 * @param id_run
	 * @param ids_choix
	 */
	public void deleteChoix(int id_run, ArrayList<Integer> ids_choix) { 
		
		try {
			
			Run run = findRun(id_run);
			
			for(int i=0; i < ids_choix.size(); i++){
				
				Choix choice_to_delete = run.getChoix(ids_choix.get(i));
				run.deleteChoix(choice_to_delete);
			}	
		}
		
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		} 
	}
	
	//SENDCHOICE
	/**
	 * permet à un client de voter avec un nombre déterminé de jetons pour un choix d'un run précisé en paramètre
	 * si le run n'existe pas, si le client n'existe pas et s'il ne peut pas voter, et si le choix pour lequel il veut voter n'existe pas, la fonction affiche une erreur (findRun, getClient et ExceptionCantVote)
	 * @param id_run
	 * @param adresse_client
	 * @param choix
	 * @param nb_jetons
	 */
	public void voteChoice(int id_run, String adresse_client, String choix, int nb_jetons){
		
		try { //si le run existe
			Run run = findRun(id_run);
			
			Client client_who_votes = run.getClient(adresse_client);
				
			Choix c = run.getChoix(choix);

			try { // et s'il peut voter
				run.addVote(client_who_votes, c, nb_jetons);	

			}	
			catch (ExceptionCantVote exce) {
			System.out.println(exce.getMessage());
			}
		}
	
		catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		}		
	}
	

	//BECOME FOLLOWER(id_client_following, id_client_followed)
	
	/**
	 * permet à un client de follower un autre client (identifiés avec leurs id)
	 * il faut que le run existe et les clients existent (les autres exceptions sont gérées dans la fonction addFollower)
	 * @param id_run
	 * @param id_following
	 * @param id_followed
	 */
	public void becomeFollower(int id_run, int id_following, int id_followed){ //ON NE PEUT FOLLOWER QU'UN SEUL
		
		try { //si le run existe
			
			Run run = findRun(id_run);
			Client client_who_follows = run.getClient(id_following); //on trouve ce client
			run.addFollower(id_followed, client_who_follows); //et on l'ajoute dans la liste des followers du client qui va etre suivi
		}
		
		catch (ExceptionNotFound e) { //le run le client qui veut follower n'existe pas
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * permet à un client de follower un autre client (identifiés avec leurs adresses email)
	 * il faut que le run existe et les clients existent (les autres exceptions sont gérées dans la fonction addFollower)
	 * @param id_run
	 * @param address_follower
	 * @param address_followed
	 */
	public void becomeFollower(int id_run, String address_follower, String address_followed){ //si on cherche un client avec son addresse
		
		try { //si le run existe
			
			Run run = findRun(id_run);
			Client client_who_follows = run.getClient(address_follower); //on trouve ce client
			run.addFollower(address_follower, client_who_follows); //et on l'ajoute dans la liste des followers du client qui va etre suivi
		}
		
		catch (ExceptionNotFound e) { //le run ou le client qui veut follower n'existe pas
			System.out.println(e.getMessage());
		} 
		
	}
	
	//GETCHOICESFROMCLIENT (id run, idclient)
	
	/**
	 * permet de récupérer les choix effectués par un client (récupéré avec son id)
	 * il faut que le run et le client existent
	 * @param id_run
	 * @param id_client
	 * @return une liste de chaine de caractère contenant la description du ou des choix ainsi que le nombre de tokens placés correspondant
	 */
	public List<String> getChoicesFromClient (int id_run, int id_client) {
		
		List<String> res = new ArrayList<String>();
		
		try {
			
			Run run = this.findRun(id_run);
			Client client = run.getClient(id_client);
			return client.getChoices();
		}
		catch(ExceptionNotFound e){
			
			res.add(e.getMessage());
			
		}
		return res;
	}
	
	 /**
	 * permet de récupérer les choix effectués par un client (récupéré avec son adresse mail)
	 * il faut que le run et le client existent
	 * @param id_run
	 * @param adresseMail
	 * @return une liste de chaine de caractères contenant la description du ou des choix ainsi que le nombre de tokens placés correspondant
	 */
	public List<String> getChoicesFromClient (int id_run, String adresseMail) {
		
		List<String> res = new ArrayList<String>();
		
		try {
			
			Run run = this.findRun(id_run);
			Client client = run.getClient(adresseMail);
			return client.getChoices();
		}
		catch(ExceptionNotFound e){
			
			res.add(e.getMessage());
			
		}
		return res;
	}
	//SENDDECISION (==>ENDRUN ?)
	
	
	/**
	 * renvoie l'actuelle décision d'un run (pas définitive), c'est à dire l'ensemble des choix décidé par l'algorithme de décision pour chaque client (commande STATUS)
	 * @param id_run
	 * @return une liste de chaine de caractères contenant tous les clients et leur choix "définitif" associé
	 */
	public List<String> getTemporaryDecision(int id_run){
		
		List<String> result = new ArrayList<String>();
		
		try {
			
			try {
				Run run = findRun(id_run);
				result = run.getDecision();
			}
			
			catch (ExceptionImpossible ex){
				result.add(ex.getMessage());
			}
		}
		catch (ExceptionNotFound e){
			result.add(e.getMessage());
		}
		
		return result;
	}

	//ENDRUN
	
	/**
	 * sert à terminer un run
	 * le run doit exister et le nombre de client doit etre inférieur ou égal au nombre de choix (sinon une exception est levée par ExceptionNotFound et ExceptionImpossible)
	 * @param id_run
	 * @return
	 */
	public List<String> endRun(int id_run) {
		
		List<String> result = new ArrayList<String>();
		
		try {
			
			try {
				Run run = findRun(id_run);
				result = run.sendDecision();
			}
			
			catch (ExceptionImpossible ex){
				result.add(ex.getMessage());
			}
		}
		catch (ExceptionNotFound e){
			result.add(e.getMessage());
		}
		
		return result;
	}	
}
