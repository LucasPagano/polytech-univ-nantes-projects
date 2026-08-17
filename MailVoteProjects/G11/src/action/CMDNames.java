package action;

/**
 * Classe qui stock en attributs statique les commandes existantes dans le programme.
 *
 */
public class CMDNames {
	
	// General commands
	public static Commande HELP = new Commande("HELP", 0);
	public static Commande RUN = new Commande("RUN", 1);
	public static Commande USER = new Commande("USER", 1);
	public static Commande STATUS = new Commande("STATUS", 0);
	
	// Clients command
	public static Commande VOTE = new Commande("VOTE", 2);
	public static Commande FOLLOW = new Commande("FOLLOW", 1);
	
	// Initiator commands
	public static Commande CREATE_RUN = new Commande("CREATERUN", 10);
	public static Commande TOKEN_COUNT = new Commande("TOKENCOUNT", 1);
	public static Commande DESCRIPTION = new Commande("DESCRIPTION", 1);
	public static Commande ADD_CLIENT = new Commande("ADDCLIENT", 1);
	public static Commande DEL_CLIENT = new Commande("DELCLIENT", 1);
	public static Commande ADD_CHOICE = new Commande("ADDCHOICE", 1);
	public static Commande DEL_CHOICE = new Commande("DELCHOICE", 1);
	public static Commande SEND_INVITATION = new Commande("SENDINVITATION", 1);
	public static Commande SEND_DESCISION = new Commande("SENDDECISION", 1);
	
	
}
