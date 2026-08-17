package classes;

import javax.mail.Message;

public class Test {

	static private Run[] arrayRun = new Run[0];

	public static void main(String[] args) throws Exception {
		boolean test = true;
		while (test) {

			// CollectMail mailCollect = new CollectMail();
			//
			// Message[] mails = mailCollect.collectMail(); // il y a tous
			// // les
			// // mails recuperes
			//
			// for (int i = 0; i < mails.length; i++) {
			// Message msg = mails[i];
			// System.out.println(msg.getSubject());
			// String subject = msg.getSubject();
			// System.out.println("Subject: " + subject);
			// System.out.println("Body: \n" + msg.getContent());
			// System.out.println("id run : " + run.getId());
			//
			// Choice choice = new Choice(0, "choix");
			//
			// Initiators initiators = new
			// Initiators(run.generateIDPerson(),"gide", "adrien", address);
			//
			// System.out.println("id init : " + initiators.getIdUser());
			//
			// Client client = new Client(run.generateIDPerson(),"client 1",
			// address, address);
			//
			// System.out.println("id client 2 : " + client.getIdUser());
			//
			// initiators.addRunClient(run, "client 1", address, address);
			//
			//
			// initiators.addRunChoice(run, choice);
			//
			// run.addClient(client);
			//
			// initiators.makeRunArrayVote(run);
			//
			// run.vote(client, choice, 5);
			// System.out.println(run.getArrayVote());
			//
			//
			//
			// int k = 1;
			// for(Client c : run.getClient()){
			//
			// System.out.println("client " + k + " : " + c.getIdUser());
			// k++;
			// }
			//
			// k = 1;
			// for (int m : run.getListID()){
			// System.out.println("id " + k + " : " + m);
			// k++;
			// }
			//
			// test= false;
			//
			//

			// if (i == 0) {
			//
			// // Considerons le cas d'un mail on on cree un nouveau Run
			//
			// // On cree un tableau temporaire pour recopier les Run deja
			// // existants dedans
			//
			// Run[] tmp = new Run[arrayRun.length + 1]; // Tableau de RUN
			// // tmp de
			// // longeur
			// // arrayRun+1
			// int j = 0;
			// for (Run r : arrayRun) { // On parcourt les Run deja
			// // existans et on les copies
			// // dans tmp
			// tmp[j] = r;
			// j++;
			// }
			//
			// // On cree un nouvel initiator
			//
			// // Code pour recuperer l'addresse de l'envoyeur
			// // String from = ""+msg.getFrom();
			// // Code de test
			// String from = "yohan.couppey@etu.univ-nantes.fr";
			//
			// Initiators newInitiator = new Initiators(0, from);
			//
			// // On cree le Run avec son id
			//
			// Run newRun = new Run(from);
			//
			// // On ajoute le createur a la liste des personnes
			//
			// newRun.addInitiator(newInitiator);
			//
			// // On l'ajoute a la liste des RUN deja crees
			//
			// tmp[j] = newRun;
			// arrayRun = tmp;
			// }
			//
			// if (i == 1) {
			//
			// // Considerons le cas ou qqun veut donner un nom au Run cree
			// // dans le mail precedent
			// int id = 0;
			// // Addresse de l'envoyeur
			// // String from = ""+msg.getFrom(), name = "nomRun";
			//
			// // Adresse de test
			// String from = "yohan.couppey@etu.univ-nantes.fr", name =
			// "nomRun";
			//
			// // On regarde si la personne est dans la liste des personnes
			// for (Person p : arrayRun[id].getClient()) {
			// System.out.println(p.getAddress());
			// if (p.getAddress() == from) {
			// // Grace a la POO, si p est initiator ca ajoute le
			// // nom, sinon ca envoie un help
			// p.addRunName(arrayRun[id], name);
			// }
			// }
			// }

			/*
			 * if(i == 2){
			 * 
			 * //Considerons le cas ou qqun non Initiator envoie un mail pour
			 * ajouter une personne au run
			 * 
			 * //J'ajoute au prealable un Client dans la liste des personnes du
			 * run arrayRun[0] Client clientTest = new Client(1,
			 * "adrien.gide@etu.univ-nantes.fr"); int id = 0; // id du run
			 * arrayRun[id].addPerson(clientTest);
			 * 
			 * 
			 * String from = "adrien.gide@etu.univ-nantes.fr"; Client
			 * personneAAjouter = new Client(2,
			 * "theo.styblinski@etu.univ-nantes.fr");
			 * 
			 * for(Person p : arrayRun[id].getPerson()){
			 * System.out.println(p.getAddress()); if(p.getAddress() == from){
			 * // Grace a la POO, si p est initiator ca ajoute le nom, sinon ca
			 * envoie un help p.addRunPerson(arrayRun[id], personneAAjouter); }
			 * } }
			 */

			// if (i == 3) {
			// // on veut ajouter dans ce mail un vote a la matrice de vote
			// int id = 0;
			//
			// // j'ajoute 3 choix au arrayRun[0]
			// Choice choix0 = new Choice(0, "choix0");
			// arrayRun[id].addChoice(choix0);
			// Choice choix1 = new Choice(1, "choix1");
			// arrayRun[id].addChoice(choix1);
			// Choice choix2 = new Choice(2, "choix2");
			// arrayRun[id].addChoice(choix2);
			//
			// // je fais la matrice de vote
			// String from = "yohan.couppey@etu.univ-nantes.fr";
			// for (Person p : arrayRun[id].getClient()) {
			// if (p.getAddress() == from) {
			// p.makeRunArrayVote(arrayRun[id]);
			//
			// }
			// }
			//
			// // j'ajoute un vote
			// for (Client c : arrayRun[id].getClient()) {
			// if (c.getAddress() == from) {
			// c.voteRun(arrayRun[0], c, choix0, 3);
			// }
			// }
			//
			// }
		}
		// System.out.println("#ID du Run cree avec l'email 0");
		// System.out.println(arrayRun[0].getId());
		// System.out.println("Nom donne avec l'email 1");
		// System.out.println(arrayRun[0].getName());
		// System.out.println("Vote de la personne a id 0 pour le choix0 avec 3
		// tokens");
		// System.out.println(arrayRun[0].getArrayVote()[0][0]);
		// test = false;
	}
	/* Affiche les mails */
	/*
	 * CollectMail mailCollect = new CollectMail();
	 * 
	 * Message[] mails = mailCollect.collectMail(); //  il y a tous les //
	 * mails recuperes
	 * 
	 * for (int i = 0; i < mails.length; i++) { Message msg = mails[i];
	 * System.out.println(msg.getSubject()); String subject = msg.getSubject();
	 * System.out.println("Subject: " + subject); System.out.println("Body: \n"
	 * + msg.getContent()); }
	 */
	/* Creation d'un nouvel initiators */
	/*
	 * Initiators initTest = new Initiators(1, "Yohan",
	 * "yohan.couppey@etu.univ-nantes.fr"); Client clientTest = new Client(2,
	 * "Yohan", "yohan.couppey@etu.univ-nantes.fr");
	 */
	/* Creation d'un run */
	/*
	 * Run runTest = initTest.createRun("yohan.couppey@etu.univ-nantes.fr");
	 * initTest.addRunName(runTest, "RunTest");
	 * initTest.addRunDescription(runTest,
	 * "Petit test de la structure de RUN et verification du fonctionnement des fonctions de Initiators"
	 * ); initTest.addRunTokenNumber(runTest, 3); System.out.println("\n\n");
	 * System.out.println(runTest.getName());
	 * System.out.println(runTest.getDescription()); System.out.println("\n\n");
	 * 
	 * clientTest.addRunDescription(runTest, "mdr");
	 */
	/* Envoie d'un mail */
	/*
	 * DefaultMessages help = new DefaultMessages(); SendEmail mailAEnvoyer =
	 * new SendEmail();
	 * mailAEnvoyer.sendMail("yohanspar0w96@etu.univ-nantes.fr",
	 * help.helpSubject, help.helpBody);
	 */
	// }
}
