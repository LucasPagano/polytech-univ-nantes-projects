package classes;

import java.util.Date;
import java.util.List;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

import envoie_reception_mails.CollectMail;

import java.util.Date;
import java.util.List;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;

public class Main {
	public static void main(String[] args) throws Exception {

//		System.out.println("Création d'un run\n");
//		Run runTest = Initiators.createRun("COUPPEY", "Yohan", "yohan.couppey@etu.univ-nantes.fr");
//
//		for (Initiators i : runTest.getInitiator()) {
//			System.out.println(i.getName() + " " + i.getSurname() + " " + i.getAddress() + " " + i.getId() + "\n");
//		}
//
//		System.out.println("Ajout d'un initiator\n");
//
//		Initiators.addRunInitiator(runTest, "GIDE", "Adrien", "adrien.gide@etu.univ-nantes.fr");
//
//		for (Initiators i : runTest.getInitiator()) {
//			System.out.println(i.getName() + " " + i.getSurname() + " " + i.getAddress() + " " + i.getId() + "\n");
//		}
//
//		System.out.println("Ajout d'une Description au RUN\n");
//
//		Initiators.addRunDescription(runTest, "Description test\n");
//
//		System.out.println(runTest.getDescription());
//
//		System.out.println("Ajout d'un Client\n");
//
//		Initiators.addRunClient(runTest, "STYBLINSKI", "Théo", "theo.styblinski@etu.univ-nantes.fr");
//		Initiators.addRunClient(runTest, "Ehresmann", "Nicolas", "nicolas.erhesmann@etu.univ-nantes.fr");
//		Initiators.addRunClient(runTest, "COUPPEY", "Yohan", "yohan.couppey@etu.univ-nantes.fr");
//		Initiators.addRunClient(runTest, "GIDE", "Adrien", "adrien.gide@etu.univ-nantes.fr");
//
//		Client[] clientTest = new Client[runTest.getClient().size()];
//		int j = 0;
//		for (Client c : runTest.getClient()) {
//			clientTest[j] = c;
//			j++;
//			System.out.println(c.getName() + " " + c.getSurname() + " " + c.getAddress() + " " + c.getId() + "\n");
//		}
//
//		System.out.println("Ajout d'un choix\n");
//
//		Initiators.addRunChoice(runTest, "Choix1");
//		Initiators.addRunChoice(runTest, "Choix2");
//
//		Choice[] choiceTest = new Choice[runTest.getChoice().size()];
//		j = 0;
//		for (Choice c : runTest.getChoice()) {
//			choiceTest[j] = c;
//			j++;
//			System.out.println("Nom choix :" + c.getName() + " id: " + c.getId());
//		}
//
//		System.out.println("Ajout d'un nombre de Token max (on choisit 3) ");
//
//		Initiators.addRunTokenNumber(runTest, 3);
//
//		System.out.println("Nombre de Token max = " + runTest.getTokenNumber());
//
//		System.out.println("Envoie de mails d'invitation au vote aux Clients du Run");
//
//		Initiators.sendInvitation(runTest);
//
//		System.out.println("Génération d'un tableau de vote avec 1 choix et 1 Client");
//
//		Initiators.makeRunArrayVote(runTest);
//
//		System.out.println("Vote du client Théo pour le choix 0 avec 2 Token");
//
//		Client.voteRun(runTest, clientTest[0], choiceTest[0], 2);
//
//		System.out.println(runTest.getArrayVote()[0][0]);
//
//		System.out.println("Vote du client Nicolas pour le choix 1 avec 2 Token");
//
//		Client.voteRun(runTest, clientTest[1], choiceTest[1], 2);
//
//		System.out.println(runTest.getArrayVote()[1][1]);
//
//		System.out.println("Vote du client Nicolas pour le choix 0 avec 1 Token");
//
//		Client.voteRun(runTest, clientTest[1], choiceTest[0], 1);
//
//		System.out.println(runTest.getArrayVote()[1][0]);
//
//		System.out.println(runTest.getArrayVote());
//
//		Decision decision = new Decision();
//		int[][] tabDecision = decision.makeDecision(runTest);
//
//		System.out.println("Décision pour le tableau de vote a la position 0 0");
//		System.out.println(tabDecision[0][0]);

		 while (true) {
		
		 CollectMail mailCollect = new CollectMail();
		 Message[] mails = mailCollect.collectMail();
		
		 for (int i = 0; i < mails.length; i++) {
		 Message msg = mails[i];
		 System.out.println(msg.getSubject());
		 String subject = msg.getSubject();
		 System.out.println("Subject: " + subject);
		 System.out.println("Body: \n" + msg.getContent());
		
		 TakeAction action = new TakeAction();
		 action.makeAction(mails[i]);
		
		 }
		 System.out.println("sorti");
		 
		 mailCollect.delMail();
		 Thread.sleep(3600000);
		 }
	}
}
