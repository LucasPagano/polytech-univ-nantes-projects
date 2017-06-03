import java.util.ArrayList;

import donneesLocales.Data;
import donneesLocales.ExceptionNotFound;

public class ApplicationEva {
	public static void main(String[] args) {
		
		System.out.println("**************************************************");
		
		//CREATION RUNS (2)
		
		
		String nomR1 = "Run1";
		String descriptionR1 = "Description1";
		int nbTokenR1 = 2;
		ArrayList<String> nomInitiators_R1 = new ArrayList<String>();
		nomInitiators_R1.add("Ian CLEMENT 1");
		nomInitiators_R1.add("Felix ARNOULT 1");
		ArrayList<String> adressesInitiators_R1 = new ArrayList<String>();
		adressesInitiators_R1.add("ian@clement1.fr");
		adressesInitiators_R1.add("felix@arnoult1.fr");
		
		String nomR2 = "Run2";
		String descriptionR2 = "Description2";
		int nbTokenR2 = 3;
		ArrayList<String> nomInitiators_R2 = new ArrayList<String>();
		nomInitiators_R2.add("Ian CLEMENT 2");
		nomInitiators_R2.add("Felix ARNOULT 2");
		ArrayList<String> adressesInitiators_R2 = new ArrayList<String>();
		adressesInitiators_R2.add("ian@clement2.fr");
		adressesInitiators_R2.add("felix@arnoult2.fr");
		
		Data.getInstance().createRun(nomR1, descriptionR1, nbTokenR1, nomInitiators_R1, adressesInitiators_R1);
		Data.getInstance().createRun(nomR2, descriptionR2, nbTokenR2, nomInitiators_R2, adressesInitiators_R2);
		System.out.println("RUN 1 : "+ Data.getInstance().tabRun.get(0));
		System.out.println("Initiators R1 : "+ Data.getInstance().tabRun.get(0).getTabInitiators());
		System.out.println("nbToken : "+ Data.getInstance().tabRun.get(0).getNbTokenPerClient());
		System.out.println("RUN 2 : "+ Data.getInstance().tabRun.get(1));
		System.out.println("Initiators R2 : "+ Data.getInstance().tabRun.get(1).getTabInitiators());
		System.out.println("nbToken : "+ Data.getInstance().tabRun.get(1).getNbTokenPerClient());
		
		
		
		System.out.println("**************************************************");
		
		
		//MODIFICATION NB TOKENS (1)
		
		Data.getInstance().setTokens(1, 6);
		System.out.println("Modification nbToken : "+ Data.getInstance().tabRun.get(1).getNbTokenPerClient());
		
		
		System.out.println("**************************************************");
		
		
		//AJOUT CLIENTS (2)
		
		
		ArrayList<String> newNomsClients_R2 = new ArrayList<String>();
		newNomsClients_R2.add("Eva BOURGEAIS--BOON 2");
		newNomsClients_R2.add("Nicolas SKLER 2");
		newNomsClients_R2.add("Paul LOZE 2");
		ArrayList<String> newAdressesClients_R2 = new ArrayList<String>();
		newAdressesClients_R2.add("eva@bboon2.fr");
		newAdressesClients_R2.add("nicolas@skler2.fr");
		newAdressesClients_R2.add("paul@loze2.fr");
		Data.getInstance().addClients(1, newNomsClients_R2, newAdressesClients_R2);
		System.out.println("Liste des clients R2:" + Data.getInstance().tabRun.get(1).getTabClients());
		
		
		
		System.out.println("**************************************************");
		
		
		//AJOUT CHOIX (2)
		
		
		ArrayList<String> newChoix_R2 = new ArrayList<String>();
		newChoix_R2.add("Vote pour LePen");
		newChoix_R2.add("Vote pour Macron");
		/*newChoix_R2.add("Vote pour Melenchon");
		newChoix_R2.add("Vote pour Hamon"); */
		Data.getInstance().addChoix(1,newChoix_R2);
		System.out.println("Matrice des choix R2:" + Data.getInstance().tabRun.get(1).getMatrice());
		System.out.println("Liste des choix R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabChoix());
		
		
		
		System.out.println("**************************************************");
		
		
		//SUPPRESSION CHOIX (2)
		
		
		ArrayList<Integer> deleteChoix_R2 = new ArrayList<Integer>();
		deleteChoix_R2.add(1);
		deleteChoix_R2.add(8);
		Data.getInstance().deleteChoix(1, deleteChoix_R2);
		System.out.println("Suppression Choix n°1 et choix n°8 R2 : \nMatrice : " + Data.getInstance().tabRun.get(1).getMatrice());
		System.out.println("Liste des choix R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabChoix());
		
		
		
		System.out.println("**************************************************");
		
		
		
		//SUPPRESSION CLIENT (1)
		
		
		ArrayList<Integer> deleteClients_R2 = new ArrayList<Integer>();
		deleteClients_R2.add(0);
		Data.getInstance().deleteClientWithID(1, deleteClients_R2);
		System.out.println("Suppression Client R2 (Eva):\nMatrice : " + Data.getInstance().tabRun.get(1).getMatrice());
		System.out.println("Liste des clients R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabClients());
		
		
		
		System.out.println("**************************************************");
		
		
		//RAJOUT CLIENTS (2)
		
		
		ArrayList<String> rajoutNomsClients_R2 = new ArrayList<String>();
		rajoutNomsClients_R2.add("Eva BOURGEAIS--BOON 2");
		rajoutNomsClients_R2.add("Paul LOZE 2");
		ArrayList<String> rajoutAdressesClients_R2 = new ArrayList<String>();
		rajoutAdressesClients_R2.add("eva@bboon2.fr");
		rajoutAdressesClients_R2.add("paul@loze2.fr");
		Data.getInstance().addClients(1, rajoutNomsClients_R2, rajoutAdressesClients_R2);
		System.out.println("Rajouts Clients R2:\nMatrice : " + Data.getInstance().tabRun.get(1).getMatrice());
		System.out.println("Liste des clients R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabClients());
		
		
		
		System.out.println("**************************************************");
		
		
		//RAJOUT CHOIX (2) (rajout du choix de mme lepen) ==> respect du tri de la liste des choix
		
		Data.getInstance().addChoix(1,newChoix_R2);
		System.out.println("Matrice des choix R2:" + Data.getInstance().tabRun.get(1).getMatrice());
		System.out.println("Liste des choix R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabChoix());
		
		
		
		System.out.println("**************************************************");
		
		
		
		//SUPPRESSION INITIATOR (1)
		
		ArrayList<Integer> deleteInitiators_R2 = new ArrayList<Integer>();
		deleteInitiators_R2.add(0);
		Data.getInstance().deleteInitiators(1, deleteInitiators_R2);
		System.out.println("Suppression initiators R2:\nListe des initiators R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabInitiators());
		
		
		
		System.out.println("**************************************************");
		
		
		//AJOUT INITIATOR (1)
		
		ArrayList<String> rajoutNomsInitiators_R2 = new ArrayList<String>();
		rajoutNomsInitiators_R2.add("Eva BOURGEAIS--BOON 2");
		ArrayList<String> rajoutAdressesInitiators_R2 = new ArrayList<String>();
		rajoutAdressesInitiators_R2.add("eva@bboon2.fr");
		Data.getInstance().addInitiators(1, rajoutNomsInitiators_R2, rajoutAdressesInitiators_R2);
		System.out.println("Rajouts initiators R2:\nListe des initiators R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabInitiators());
		
		
		
		System.out.println("**************************************************");
		
		
		//AJOUT INITIATOR TENTATIVE (1)
		
		Data.getInstance().addInitiators(1, rajoutNomsInitiators_R2, rajoutAdressesInitiators_R2);
		System.out.println("Rajouts initiators TENTATIVE R2:\nListe des initiators R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabInitiators());
		
		
		System.out.println("**************************************************");
		
		
		//SUPPRESSION INITIATOR TENTATIVE(1+1)
		
		ArrayList<Integer> deleteInitiatorsEssai_R2 = new ArrayList<Integer>();
		deleteInitiatorsEssai_R2.add(0);
		deleteInitiatorsEssai_R2.add(7);
		Data.getInstance().deleteInitiators(1, deleteInitiatorsEssai_R2);
		System.out.println("Suppression initiators R2:\nListe des initiators R2 MAJ :" + Data.getInstance().tabRun.get(1).getTabInitiators());
		
		
		
		System.out.println("**************************************************");
		
		
		
		//AJOUT FOLLOWER Eva follow Paul
		

		Data.getInstance().becomeFollower(1, 0, 2);
		try { System.out.println("Ajout d'un follower Eva ==> Paul R2:\nListe des followers de Paul de R2 MAJ :" + Data.getInstance().tabRun.get(1).getClient(2).getFollowers());
		} catch (ExceptionNotFound e) {
		System.out.println(e.getMessage());
		} 	  
		
		System.out.println("**************************************************");
		
		
		//AJOUT FOLLOWER Paul follow Nicolas

		try {
			System.out.println("Ajout d'un follower Paul ==> Nicolas R2:");
			Data.getInstance().becomeFollower(1, 2, 1);
			System.out.println("Liste des followers de Nicolas de R2 MAJ :" + Data.getInstance().tabRun.get(1).getClient(1).getFollowers());
			System.out.println("nombre de tokens de Paul de R2 MAJ :" + Data.getInstance().tabRun.get(1).getClient(2).getNbToken());
			System.out.println("qui est la personne followée par Paul ?" + Data.getInstance().tabRun.get(1).getClient(2).getFollow());
			System.out.println("qui est la personne followée par Eva ?" + Data.getInstance().tabRun.get(1).getClient(0).getFollow());
			System.out.println("nombre de tokens de Nicolas R2 MAJ :" + Data.getInstance().tabRun.get(1).getClient(1).getNbToken());
			System.out.println("Liste des followers de Paul de R2 MAJ :" + Data.getInstance().tabRun.get(1).getClient(2).getFollowers());
		} catch (ExceptionNotFound e) {
		System.out.println(e.getMessage());
		} 	
		
		
		System.out.println("**************************************************");
		
		
		//AJOUT FOLLOWER Paul follow Eva
		
		Data.getInstance().becomeFollower(1, 2, 0);
		try {
			System.out.println("Ajout d'un follower Paul ==> Eva R2:\nListe des followers de Paul de R2 MAJ :" + Data.getInstance().tabRun.get(1).getClient(2).getFollowers());
			System.out.println("qui est la personne followée par Paul ?" + Data.getInstance().tabRun.get(1).getClient(2).getFollow());
			System.out.println("nombre de tokens de Eva de R2 MAJ :" + Data.getInstance().tabRun.get(1).getClient(0).getNbToken());
			System.out.println("Matrice : " + Data.getInstance().tabRun.get(1).getMatrice());
			System.out.println("nombre de tokens de Paul de R2 MAJ : " + Data.getInstance().tabRun.get(1).getClient(2).getNbToken());

		} catch (ExceptionNotFound e) {
		System.out.println(e.getMessage());
		} 
		
		System.out.println("**************************************************");
		
		
		//AJOUT CHOIX (1) //OK ==>> gere bien le nombre de tokens, ne fait rien si le nombre de tokens n'est pas suffisant, MAJ nb tokens ok
		
		System.out.println("Nicolas skler (client 2) vote pour LePen");	
		Data.getInstance().voteChoice(1, "nicolas@skler2.fr", "Vote pour LePen", 12);
		try {
			System.out.println("nombre de tokens de client 2 de R2 MAJ : " + Data.getInstance().tabRun.get(1).getClient(1).getNbToken());
			System.out.println("Matrice : " + Data.getInstance().tabRun.get(1).getMatrice());
			System.out.println("Test fonction get : " + Data.getInstance().tabRun.get(1).getElementMatrix(1, 0));
		} catch (ExceptionNotFound e) {
			System.out.println(e.getMessage());
		}
		
		//MATRICE !!
		
		System.out.println("**************************************************");
		
		System.out.println("Tableau des clients : " + Data.getInstance().tabRun.get(1).getTabClients());
		System.out.println("Fermeture run");
		System.out.println(Data.getInstance().endRun(1));
		
		
		System.out.println("**************************************************");
		System.out.println("Décision temporaire : (status)" + Data.getInstance().getTemporaryDecision(1));
		
		
		System.out.println("**************************************************");

		System.out.println("Choix Nicolas: (status)" + Data.getInstance().getChoicesFromClient(1, 1)); //TODO
		
		
		
	}
	
	
}