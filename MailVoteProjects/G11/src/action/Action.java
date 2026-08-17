package action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.mail.Address;
import javax.mail.MessagingException;

import donneesLocales.Choix;
import donneesLocales.Client;
import donneesLocales.Data;
import donneesLocales.ExceptionAlreadyExists;
import donneesLocales.ExceptionNotFound;
import donneesLocales.Run;
import receiveMail.CheckMail;
import sendMail.SendMailTLS;

public class Action {

	public static SendMailTLS sender = new SendMailTLS();
	public static CheckMail receiver = null;

	public static void setReceiver(CheckMail checker) {
		Action.receiver = checker;
	}

	/**
	 * Mthode qui envoit tous les clients d'un Run la dcision finale.
	 * 
	 * @param cmd
	 *            La commande
	 * @param idRun
	 *            L'id du run.
	 */
	public static void sendDescision(Commande cmd, int idRun) {
		System.out.println(">SendDecision " + cmd.getAddresse());
		String msg;
		try {
			Client initiator = Data.getInstance().findRun(idRun).getInitiator(cmd.getAddresse());
			msg = getDecisionMessage(idRun, cmd.getArgs().getFirst());
			for (Client client : Data.getInstance().findRun(idRun).getTabClients()) {
				sender.send(client.getAddress(), Constants.DecisionSubject, msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mthode qui renvoi tous les clients d'un vote une invitation a voter.
	 * l'invitation contient, la description du run courant. l'aide indiquant
	 * comment voter En rpondant au mail, la rponse doit tre comprise comme un
	 * vote.
	 * 
	 * @param cmd
	 *            La commande
	 * @param idRun
	 *            L'id du run concern.
	 */
	public static void sendInvitation(Commande cmd, int idRun) {
		System.out.println(">sendInvitation - " +cmd.getAddresse());
		String msg;
		try {
			Client initiator = Data.getInstance().findRun(idRun).getInitiator(cmd.getAddresse());
			msg = getInvitationMessage(idRun, cmd.getArgs().getFirst());
			for (Client client : Data.getInstance().findRun(idRun).getTabClients()) {
				msg = msg.replace("IDCLIENT", client.getId() + "");
				sender.send(client.getAddress(), Constants.InvitationSubject, msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}

	}

	/**
	 * Ajoute un choix à un run
	 * 
	 * @param cmd
	 *            La commande
	 * @param idRun
	 *            l'id du Run
	 */
	public static void addChoice(Commande cmd, int idRun) {
		System.out.println(">addChoice - " + cmd.getAddresse());
		try {
			Client initiator = Data.getInstance().findRun(idRun).getInitiator(cmd.getAddresse());
			ArrayList<String> choice = new ArrayList<>();
			for (String ch : cmd.getArgs()) {
				choice.add(ch);
			}
			Data.getInstance().addChoix(idRun, choice);

		} catch (ExceptionNotFound e) {
			System.out.println("un client ne peux pas supprimer un client.");

		}
	}

	/**
	 * Supprime un chix a un RUN.
	 * 
	 * @param cmd
	 *            La commande
	 * @param idRun
	 *            l'id du run
	 */
	public static void delChoice(Commande cmd, int idRun) {
		System.out.println(">delChoice - " + cmd.getAddresse());
		try {
			Client initiator = Data.getInstance().findRun(idRun).getInitiator(cmd.getAddresse());
			ArrayList<Integer> choice = new ArrayList<>();
			for (String ch : cmd.getArgs()) {
				choice.add(Integer.parseInt(removeLineBreaks(ch)));
			}
			Data.getInstance().deleteChoix(idRun, choice);

		} catch (ExceptionNotFound e) {
			System.out.println("un client ne peux pas supprimer un client.");

		}
	}

	/**
	 * Supprime un client d'un run
	 * 
	 * @param cmd
	 *            la commande
	 * @param idRun
	 *            l'id du run
	 */
	public static void delClient(Commande cmd, int idRun) {
		System.out.println(">delClient - " + cmd.getAddresse());
		try {
			Client initiator = Data.getInstance().findRun(idRun).getInitiator(cmd.getAddresse());
			ArrayList<Integer> idClient = new ArrayList<>();
			idClient.add(Data.getInstance().findRun(idRun).getClient(cmd.getArgs().getFirst()).getId());
			Data.getInstance().deleteClientWithID(idRun, idClient);

		} catch (ExceptionNotFound e) {
			System.out.println("un client ne peux pas supprimer un client.");

		}

	}

	/**
	 * Ajoute un cliet a un Run
	 * 
	 * @param cmd
	 *            La commande
	 * @param idRun
	 *            l'id du run
	 */
	public static void addClient(Commande cmd, int idRun) {
		System.out.println(">addClient" + cmd.getAddresse());
		try {
			Client initiator = Data.getInstance().findRun(idRun).getInitiator(cmd.getAddresse());
			ArrayList<String> adresse = new ArrayList<>();
			adresse.add(cmd.getArgs().getFirst());
			Data.getInstance().addClients(idRun, adresse, adresse);
			try {
				Client c = Data.getInstance().findRun(idRun).getClient(adresse.get(0));
				String msg = getInvitationMessage(idRun, c.getId(), "");
				sender.send(cmd.getArgs().getFirst(), Constants.InvitationSubject, msg);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ExceptionNotFound e) {
				e.printStackTrace();
			}
		} catch (ExceptionNotFound e1) {
			System.out.println("un client ne peux pas ajouter de client.");
		}

	}

	/**
	 * Ajoute un cliet a un Run
	 * 
	 * @param addresse
	 *            L'adresse
	 * @param idRun
	 *            l'id du run
	 */
	public static void addClient(String addresse, int idRun) {
		System.out.println(">addClient - " +addresse);
		ArrayList<String> adresse = new ArrayList<>();
		adresse.add(addresse);
		Data.getInstance().addClients(idRun, adresse, adresse);
		try {
			Client c = Data.getInstance().findRun(idRun).getClient(adresse.get(0));
			String msg = getInvitationMessage(idRun, c.getId(), "");
			sender.send(addresse, Constants.InvitationSubject, msg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}
	}

	public static void description() {
		System.out.println("description");
	}

	public static void tokenCount() {
		System.out.println("tokenCount");

	}

	/**
	 * Creer un run, necessite trois commandes. createRun, tokenCount et
	 * description
	 * 
	 * @param createRun
	 *            La commande createRun
	 * @param tokenCount
	 *            La commande tokenCount
	 * @param description
	 *            La commande description
	 */
	public static void createRun(Commande createRun, Commande tokenCount, Commande description) {
		if (createRun != null && tokenCount != null && description != null) {
			System.out.println(">createRun - " + createRun.getAddresse());
			ArrayList<String> adresse = new ArrayList<>();
			ArrayList<String> noms = new ArrayList<>();
			for (String addr : createRun.getArgs()) {
				adresse.add(addr);
			}
			noms.add(createRun.getAddresse());
			int nbTokens = Integer.parseInt(removeLineBreaks(tokenCount.getArgs().getFirst()));
			// System.out.println(tokenCount+"\n"+description);
			int idRun = Data.getInstance().createRun("VOTE", removeLineBreaks(description.getArgs().getFirst()), nbTokens, adresse,
					noms);
			// addClient(createRun.getAddresse(), createRun.getIdRun());
			String msg;
			try {
				Client initiator = Data.getInstance().findRun(idRun).getInitiator(createRun.getAddresse());
				msg = getStatusMsg(idRun, initiator.getId(), createRun.getAddresse());
				sender.send(createRun.getAddresse(), Constants.CreateSubject, msg);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ExceptionNotFound e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("CREATERUN : FAIL!");
		}

		// Send Status, renvoi le status du vote partir du mail .

	}

	/**
	 * Ajoute un follower
	 * 
	 * @param cmd
	 *            La commande
	 * @param idRun
	 *            l'id du run
	 */
	public static void follow(Commande cmd, int idRun) {
		System.out.println(">follow - " + cmd.getAddresse());
		try {
			Client follower = Data.getInstance().findRun(idRun).getClient(cmd.getAddresse());
			Client followe = Data.getInstance().findRun(idRun).getClient(cmd.getArgs().getFirst());
			Data.getInstance().becomeFollower(idRun, follower.getId(), followe.getId());
			String msg = getFollowMessage(idRun, follower.getNom());
			sender.send(followe.getAddress(), Constants.FollowSubject, msg);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Affecte un vote a un client
	 * 
	 * @param vote
	 *            le vote
	 * @param idRun
	 *            l'id du run
	 * @param idClient
	 *            l'id du client
	 */
	public static void vote(Commande vote, int idRun, String idClient) {
		System.out.println(">vote - " + vote.getAddresse());
		String choix;
		String msg = "";
		// System.out.println(vote);
		// System.out.println(vote.getArgs().getFirst());
		try {
			Client client = Data.getInstance().findRun(idRun).getClient(vote.getAddresse());
			choix = Data.getInstance().findRun(idRun).getChoix(Integer.parseInt(vote.getArgs().getFirst()))
					.getDescription();
			Data.getInstance().voteChoice(idRun, vote.getAddresse(), choix, Integer.parseInt(vote.getArgs().getLast()));
			msg = getStatusMsg(idRun, client.getId(), client.getAddress());
			sender.send(vote.getAddresse(), Constants.VoteSubject, msg);
		} catch (ExceptionNotFound | IOException e) {
			System.out.println("Non trouvé");
		}

	}

	/**
	 * Renvois le status d'un run
	 * 
	 * @param status
	 *            la commande
	 * @param idRun
	 *            l'id du run
	 * @param idClient
	 *            lid du client
	 */
	public static void status(Commande status, int idRun, String idClient) {
		System.out.println(">Status - " +status.getAddresse());
		String msg;
		try {
			Client c = getValidClient(status, idRun, idClient);
			if (c != null) {
				msg = getStatusMsg(idRun, c.getId(), status.getAddresse());
				sender.send(status.getAddresse(), Constants.HelpSubject, msg);

			} else {
				msg = getStatusMsg(idRun, -1, status.getAddresse());
				sender.send(status.getAddresse(), Constants.HelpSubject, msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}
	}

	public static void user() {
		System.out.println("user");

	}

	public static void run() {
		System.out.println("run");

	}

	private static Client getValidClient(Commande cmd, int idRun, String idClient) throws ExceptionNotFound {
		Client c = null;
		if (idClient.equals("")) {
			try {
				c = Data.getInstance().findRun(idRun).getClient(cmd.getAddresse());
			} catch (ExceptionNotFound e) {
			}
			try {
				c = Data.getInstance().findRun(idRun).getInitiator(cmd.getAddresse());
			} catch (Exception e) {
			}
		}
		return c;
	}

	/**
	 * Envoi un mail d'aide au client
	 * 
	 * @param cmd
	 *            La commande
	 */
	public static void help(Commande cmd) {
		System.out.println(">help - " + cmd.getAddresse());
		try {
			String msg = readFile("help.txt");
			sender.send(cmd.getAddresse(), Constants.HelpSubject, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	/**
	 * Methode qui permet de lire un fichier ressource.
	 * 
	 * @param path
	 *            le chemin
	 * @return Un string du contenu du fichier
	 * @throws IOException
	 */
	public static String readFile(String path) throws IOException {
		String filePath = new File("").getAbsolutePath();
		path = filePath + "\\ressource\\" + path;
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, "utf-8");
	}

	/**
	 * Renvoi le status d'un run
	 * 
	 * @param idRun
	 *            l'i du run
	 * @return Un string complete renseignant le status d'un run
	 * @throws IOException
	 * @throws ExceptionNotFound
	 */
	public static String getStatusMsg(int idRun, int idClient, String adresse) throws IOException, ExceptionNotFound {
		String msg = readFile("status.txt");
		StringBuilder choice = new StringBuilder();
		Run run = Data.getInstance().findRun(idRun);
		msg = msg.replace("IDRUN", idRun + "");
		msg = msg.replace("DESCRIPTION", run.getDescription());
		Client client = null;
		Client initiator = null;
		try {
			client = run.getClient(adresse);
		} catch (ExceptionNotFound e) {

		}
		try {
			initiator = run.getInitiator(adresse);
		} catch (ExceptionNotFound e) {
		}
		if (idClient != -1 && client != null) {
			msg = msg.replace("TOKENS", run.getClient(idClient).getNbToken() + "");
			msg = msg.replace("USERTYPE", "user");
			msg = msg.replace("TMPDECISION", "");

			String tmp = "";
			for (String s : Data.getInstance().getChoicesFromClient(idRun, idClient)) {
				tmp = tmp + s +"\n";
			}
			msg = msg.replace("PLACECLIENT", tmp);
			msg = msg.replace("ALLCLIENT", "");

		} else if (idClient != -1 && initiator != null) {
			msg = msg.replace("TOKENS", "0 (you are an initiator)");
			msg = msg.replace("USERTYPE", "initiator");
			msg = msg.replace("PLACECLIENT", "None");

			String tmp2 = "";
			if ( run.getTabClients().size() > 1 && run.getTabChoix().size() > 1) {

				for (String s : Data.getInstance().getTemporaryDecision(idRun)) {
					tmp2 = tmp2 +"\n";
				}
			}
			msg = msg.replace("TMPDECISION", "\nTemporary decision:\n" + tmp2);
			String clients = "";


			for (Client cli : run.getTabClients()) {
				clients = clients + cli.getAddress() + " : " + cli.getNbToken() + " tokens left";
				if (cli.getFollowers().size() > 0) {
					clients = clients + " follow by ";
					for (Client followers : cli.getFollowers()) {
						clients = clients + followers.getAddress() + " ";
					}
				}
				clients = clients + "\n";
			}
			msg = msg.replace("ALLCLIENT", "\nClients who participates:\n" + clients);

		}
		
		for (Choix ch : run.getTabChoix()) {
			choice.append(ch.getId() + " : " + ch.getDescription() + "\n");
		}
		msg = msg.replace("CHOICE", choice);

		return msg;
	}

	/**
	 * Renvoi le message complété d'une invitation
	 * 
	 * @param idRun
	 *            l'id du run
	 * @param text
	 *            un text additionel
	 * @return un string du texte complete
	 * @throws IOException
	 */
	private static String getInvitationMessage(int idRun, String text) throws IOException {
		String msg = readFile("invitation.txt");
		try {
			msg = msg.replace("ADDITIONALTEXT", text);
			msg = msg.replace("DESCRIPTION", Data.getInstance().findRun(idRun).getDescription() + "");
			msg = msg.replace("DEFAULTCHOICE", Data.getInstance().findRun(idRun).getTabChoix().get(0).getId() + "");
			msg = msg.replace("TOKENNUMBER", Data.getInstance().findRun(idRun).getNbTokenPerClient() + "");
			msg = msg.replace("CHOICE", "");
			
			msg = msg.replace("IDRUN", idRun + "");
		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * Renvoi le message complété d'une invitation
	 * 
	 * @param idRun
	 *            l'id du run
	 * @param idClient
	 *            l'id du client
	 * @param text
	 *            un text additionel
	 * @return un string du texte complete
	 * @throws IOException
	 */
	private static String getInvitationMessage(int idRun, int idClient, String text) throws IOException {
		String msg = readFile("invitation.txt");
		try {
			msg = msg.replace("ADDITIONALTEXT", text);
			msg = msg.replace("DESCRIPTION", Data.getInstance().findRun(idRun).getDescription() + "");
			msg = msg.replace("IDDEFAULTCHOICE", Data.getInstance().findRun(idRun).getTabChoix().get(0).getId() + "");
			msg = msg.replace("DEFAULTCHOICE",
					Data.getInstance().findRun(idRun).getTabChoix().get(0).getDescription() + "");
			msg = msg.replace("TOKENNUMBER", Data.getInstance().findRun(idRun).getNbTokenPerClient() + "");
			msg = msg.replace("IDCLIENT", idClient + "");
			
			String tmp = "";
			for (Choix ch : Data.getInstance().findRun(idRun).getTabChoix()) {
				tmp = tmp + "" +(ch.getId() + " : " + ch.getDescription() + "\n");
			}
			msg = msg.replace("CHOICE", tmp + "");


			msg = msg.replace("IDRUN", idRun + "");
		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * Renvoi le texte complete d'ajout d'un follower
	 * 
	 * @param idRun
	 *            l'id du run
	 * @param follower
	 *            le nom du follower
	 * @return Un string du text complete
	 * @throws IOException
	 */
	private static String getFollowMessage(int idRun, String follower) throws IOException {
		String msg = readFile("follow.txt");
		try {
			msg = msg.replace("FOLLOWER", follower);
			msg = msg.replace("DESCRIPTION", Data.getInstance().findRun(idRun).getDescription() + "");
			msg = msg.replace("IDRUN", idRun + "");

		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}

		return msg;
	}

	/**
	 * Renvoi un text complete d'une decision
	 * 
	 * @param idRun
	 *            l'id du run
	 * @param text
	 *            un texte additionnel
	 * @return un string du texte complete
	 * @throws IOException
	 */
	private static String getDecisionMessage(int idRun, String text) throws IOException {
		String msg = readFile("decision.txt");
		try {
			msg = msg.replace("ADDITIONALTEXT", text);
			msg = msg.replace("DESCRIPTION", Data.getInstance().findRun(idRun).getDescription());

			String tmp2 = "";
			for (String s : Data.getInstance().getTemporaryDecision(idRun)) {
				tmp2 = tmp2 + s +"\n";
			}
			msg = msg.replace("ASSIGNATION", tmp2);

		} catch (ExceptionNotFound e) {
			e.printStackTrace();
		}

		return msg;
	}

	private static String removeLineBreaks(String s) {
		return s.replace("\n", "").replaceAll("\r", "");
	}
}
