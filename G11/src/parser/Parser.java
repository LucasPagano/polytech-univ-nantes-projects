package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import action.Action;
import action.CMDNames;
import action.Commande;
import receiveMail.CheckMail;

public class Parser {

	private List<Commande> cmds;
	private CheckMail checkerMail;

	/**
	 * Constructeur du parser
	 * @param checker Le lecteur de mail, utilese pour avertir qu'un mail est traite
	 */
	public Parser(CheckMail checker) {
		this.cmds = new LinkedList<>();
		this.cmds.add(CMDNames.HELP);
		this.cmds.add(CMDNames.RUN);
		this.cmds.add(CMDNames.USER);
		this.cmds.add(CMDNames.STATUS);
		this.cmds.add(CMDNames.VOTE);
		this.cmds.add(CMDNames.FOLLOW);
		this.cmds.add(CMDNames.CREATE_RUN);
		this.cmds.add(CMDNames.TOKEN_COUNT);
		this.cmds.add(CMDNames.DESCRIPTION);
		this.cmds.add(CMDNames.ADD_CLIENT);
		this.cmds.add(CMDNames.DEL_CLIENT);
		this.cmds.add(CMDNames.DEL_CHOICE);
		this.cmds.add(CMDNames.ADD_CHOICE);
		this.cmds.add(CMDNames.SEND_INVITATION);
		this.cmds.add(CMDNames.SEND_DESCISION);
		Action.setReceiver(checker);
		this.checkerMail = checker;
	}

	/**
	 * Mï¿½thode qui parse un nouveau mail.
	 * 
	 * @param messageMail
	 *            Le nouveau mail
	 * @throws IOException
	 * @throws MessagingException
	 */
	public void newMail(Message messageMail) throws MessagingException, IOException {
		
		ArrayList<String> mail = CheckMail.getChaineMsg(messageMail);
		boolean sendHelp = true;
		boolean runSelected = false;
		LinkedList<Commande> fifo = new LinkedList<>();
		String addr = mail.remove(0);
		Commande cmd;
		for (String line : mail) {
			cmd = this.getCommande(line);

			if (cmd != null) {
				if (!runSelected) {
					runSelected = isRunSelected(cmd);
				}
				cmd.setAddresse(addr);
				cmd.setMessage(messageMail);
				sendHelp = false;
				if (runSelected) {
					// this.executeCMD(cmd); // on execute la commande.
					fifo.addLast(cmd);
				}
			} else {
				sendHelp = true;
			}
		}

		// Si aucune commande trouvï¿½ on envoi de l'aide.
		if (sendHelp) {
			cmd = new Commande("HELP", 0);
			cmd.setAddresse(addr);
			cmd.setMessage(messageMail);

			fifo.addLast(cmd);
		}
		// Lancer les commandes execute( fifo)
		this.executeCMD(fifo);
	}

	/**
	 * Méthode qui lis les mails un à un, et les ajoute 
	 * @param mail
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void newMail(ArrayList<String> mail) throws MessagingException, IOException {
		boolean sendHelp = true;
		boolean runSelected = false;
		LinkedList<Commande> fifo = new LinkedList<>();

		String addr = mail.remove(0);
		Commande cmd;
		for (String line : mail) {
			cmd = this.getCommande(line);

			if (cmd != null) {
				if (!runSelected) {
					runSelected = isRunSelected(cmd);
				}
				cmd.setAddresse(addr);
				sendHelp = false;
				if (runSelected) {
					fifo.addLast(cmd);
				}
			}
		}

		// Si aucune commande trouvï¿½ on envoi de l'aide.
		if (sendHelp) {
			cmd = new Commande("HELP", 0);
			cmd.setAddresse(addr);
			fifo.addLast(cmd);
		}
		// Lancer les commandes execute( fifo)
		this.executeCMD(fifo);
	}

	private boolean isRunSelected(Commande cmd) {

		return (cmd.getName().equals("RUN") || cmd.getName().equals("CREATERUN"));
	}

	/**
	 * Mï¿½thode qui lis tous les mails.
	 * 
	 * @param mails
	 *            un tableau de mails
	 * @throws IOException
	 * @throws MessagingException
	 */
	public void readMails(ArrayList<Message> mails) throws MessagingException, IOException {
		for (Message mail : mails) {
			newMail(mail);
		}
	}

	/**
	 * Mï¿½thode qui execute les commades
	 * 
	 * @param cmds
	 *            une file de commande.
	 * @throws MessagingException 
	 */
	private void executeCMD(LinkedList<Commande> cmds) throws MessagingException {
		if ( !cmds.isEmpty()) {
			int idRun = cmds.getFirst().getIdRun();
			String idClient = "";
			for (Iterator<Commande> iteCmd = cmds.iterator(); iteCmd.hasNext();) {
				Commande cmd = iteCmd.next();

				String key = cmd.getName();
				switch (key) {

				case "HELP":
					Action.help(cmd);
					iteCmd.remove();
					break;
				 case "RUN":
					 try {
						 idRun = Integer.parseInt(removeLineBreaks(cmd.getArgs().getFirst()));
					 } catch (Exception e) {
						System.out.println("Impossible de recuperer idRun");
					}
					 break;
				case "USER":
					idClient = cmd.getArgs().getFirst();
					iteCmd.remove();
					break;
				case "STATUS":
					Action.status(cmd, idRun, idClient);
					iteCmd.remove();
					break;
				case "VOTE":
					Action.vote(cmd, idRun, idClient);
					iteCmd.remove();
					break;
				case "FOLLOW":
//					if (!idClient.equals("")) {
						Action.follow(cmd, idRun);
//					} else {
//						System.out.println("id client non définie");
//					}
					iteCmd.remove();
					break;
				case "CREATERUN":
					Commande tokenCount = null;
					Commande description = null;
					Commande createRun = null;
					createRun = new Commande("CREATERUN", 1);
					createRun.addArgs(cmd.getArgs().getFirst());
					createRun.setAddresse(cmd.getAddresse());
//					 for (Commande tmpCmd : cmds) {

					if ( iteCmd.hasNext()) {

						cmd = iteCmd.next();
						if (cmd.getName().equals("TOKENCOUNT")) {
							tokenCount = new Commande("TOKENTCOUNT", 1);
							tokenCount.addArgs(cmd.getArgs().getFirst());
							iteCmd.remove();
	
						} else if (cmd.getName().equals("DESCRIPTION")) {
							description = new Commande("DESCRIPTION", 1);
							cmd.joinArgs();
							description.addArgs(cmd.getArgs().getFirst());
							iteCmd.remove();
						}
					}
					if ( iteCmd.hasNext()) {

						cmd = iteCmd.next();
						
						if (cmd.getName().equals("TOKENCOUNT")) {
							tokenCount = new Commande("TOKENTCOUNT", 1);
							tokenCount.addArgs(cmd.getArgs().getFirst());
							iteCmd.remove();

						} else if (cmd.getName().equals("DESCRIPTION")) {
							description = new Commande("DESCRIPTION", 1);
							description.joinArgs();
							iteCmd.remove();
						}
						Action.createRun(createRun, tokenCount, description);
					}
					

					break;

				case "DELCHOICE":
					Action.delChoice(cmd, idRun);
					iteCmd.remove();
					break;
				case "ADDCLIENT":
					Action.addClient(cmd, idRun);
					iteCmd.remove();
					break;
				case "DELCLIENT":
					Action.delClient(cmd, idRun);
					iteCmd.remove();
					break;
				case "ADDCHOICE":
					cmd.joinArgs();
					Action.addChoice(cmd, idRun);				
					iteCmd.remove();
					break;
				case "SENDINVITATION":
					cmd.joinArgs();
					Action.sendInvitation(cmd, idRun);
					iteCmd.remove();
					break;
				case "SENDDECISION":
					cmd.joinArgs();
					Action.sendDescision(cmd, idRun);
					iteCmd.remove();
					break;
				default:
					System.out.println("Invalid Commande.");
					checkerMail.setFlags(cmd.getMessage(), CheckMail.DELETE);
					Action.help(cmd);
					iteCmd.remove();
					break;
				}

			}
		} else {
//			System.out.println("fifo vide");
		}

	}

	//
	// public void gererMail(Message msg){
	// try {
	// this.newMail(CheckMail.getChaineMsg(msg));
	// } catch (AddressException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (MessagingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	//
	// }

	/**
	 * Méthode qui prend une chaine de caractere et retourne la commande associé.
	 * @param cmd une chaine de caractère
	 * @return la commande associé.
	 */
	public Commande getCommande(String cmd) {
		if ( cmd != null) {
			cmd = removeLineBreaks(cmd);
			cmd = cmd.replace(">", "");
			String[] s = cmd.split(" ");
			Commande ret = null;

			// on cherche la commande associï¿½
			for (Commande tmpCMD : this.cmds) {
				for (String tmp : s) {
					if (tmpCMD.getName().equals(tmp)) {

						ret = new Commande(tmpCMD.getName(), tmpCMD.getNumberArgs());
						// On rï¿½cupï¿½re les arguments passï¿½ dans le mail.
						for (int i = 1; i < s.length; i++) {
							ret.addArgs(removeLineBreaks(s[i]));
						}
					}
				}
			}

			return ret;
		} else {
			return null;
		}
		

	}

	/**
	 * Retourne la valeur minimal
	 * @param var1 une valeur
	 * @param var2 une valeur
	 * @return la valeur minimale
	 */
	public int min(int var1, int var2) {
		if (var1 < var2)
			return var1;
		else
			return var2;
	}
	
	/**
	 * Supprimme les '\n' dans une chaine e caractère.
	 * @param s une chaine de caractère
	 * @return la nouvelle ligne sans les '\n'
	 */
	private static String removeLineBreaks(String s) {
		return s.replace("\n", "").replaceAll("\r", "");
	}
}
