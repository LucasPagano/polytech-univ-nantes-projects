package classes;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import envoie_reception_mails.SendEmail;

public class Run implements Serializable {

	private int id;
	private String name;
	private String description;
	private String address;
	private int tokenNumber;
	private List<Client> listClient = new ArrayList<Client>();
	private List<Initiators> listInitiator = new ArrayList<Initiators>();
	private List<Choice> listChoice = new ArrayList<Choice>();
	private int[][] arrayVote;
	private List<Integer> listID = new ArrayList<Integer>();
	private Date timeEnd;
	private int[] decision;

	// Constructeur

	public Run(String address) throws IOException {
		this.address = address;
		this.id = this.generateIDRun();
	}

	
	public void saveRun(Run run){
		try {
			FileOutputStream fileout = new FileOutputStream("runs.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(run);
			fileout.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

	}
	
	public static Run deserialize(int runid) {
		try {
			Run run = null;
			FileInputStream fileIn = new FileInputStream("runs.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			while (true) {
				try {
					run = (Run) in.readObject();
					System.out.println("deserun:"+run.getChoice().toString());
					if (run.getId() == runid)
						return run;
				} catch (EOFException exc) {
					in.close();
					fileIn.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException i) {
			i.printStackTrace();
		}
		return null;
	}
	// Accesseurs

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getAddress() {
		return this.address;
	}

	public int getTokenNumber() {
		return this.tokenNumber;
	}

	public List<Client> getClient() {
		return this.listClient;
	}

	public List<Initiators> getInitiator() {
		return this.listInitiator;
	}

	public List<Choice> getChoice() {
		return this.listChoice;
	}

	public List<Integer> getListID() {
		return this.listID;
	}

	public int[][] getArrayVote() {
		return this.arrayVote;
	}

	// Message d'aide

	public void help() {
		DefaultMessages help = new DefaultMessages();
		SendEmail helpMail = new SendEmail();
		try {
			helpMail.sendMail(this.getAddress(), help.helpSubject, help.helpBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Methodes de configuration

	public void addName(String name) {
		this.name = name;
	}

	public void addDescription(String text) {
		this.description = text;
	}

	public void tokenCount(int nbToken) {
		this.tokenNumber = nbToken;
	}

	public void addAddress(String address) {
		this.address = address;
	}

	public void addClient(Client client) {
		this.listClient.add(client);
	}

	public void addInitiator(Initiators initiator) {
		this.listInitiator.add(initiator);
	}

	public void removeClient(int idClient) {
		for (Client c : this.listClient) {
			if (c.getId() == idClient) {
				this.listClient.remove(c);
			}
		}
	}

	public void removeInitiator(int idInitiator) {
		for (Initiators i : this.listInitiator) {
			if (i.getId() == idInitiator) {
				this.listClient.remove(i);
			}
		}
	}

	public void addChoice(String choice) {
		this.listChoice.add(new Choice(choice));
		int id = 0;
		for (Choice c : this.listChoice) {
			c.changeID(id);
			id++;
		}
	}

	public Choice findChoice(int idChoice) {
		for (Choice c : this.listChoice) {
			if (c.getId() == idChoice) {
				return c;
			}
		}
		return null;
	}

	public Client findClient(String address) {
		for (Client c : this.listClient) {
			if (c.getAddress() == address) {
				return c;
			}
		}
		return null;
	}

	public void removeChoice(Integer idchoice) {
		this.listChoice.remove(findChoice(idchoice));
		int id = 0;
		for (Choice c : this.listChoice) {
			c.changeID(id);
			id++;
		}
	}

	public void makeArrayVote() {
		int nbChoice = this.listChoice.size();
		int nbClient = this.listClient.size();
		System.out.println(nbChoice);
		System.out.println(nbClient);
		this.arrayVote = new int[nbChoice][nbClient];
		for (int i = 0; i < nbChoice; i++) {
			for (int j = 0; j < nbClient; j++) {
				this.arrayVote[i][j] = 0;
			}
		}
	}

	public void vote(Client client, Choice choice, int nbToken) {
		if (nbToken > this.tokenNumber) {
			this.help(); // message d'aide envoyé si nbToken > au nombre maximum
							// autorisé
		} else {
			int personPosition = 0;
			int choicePosition = 0;
			for (Client c : this.listClient) { // récupére la position du client
												// dans le tableau
				personPosition++;
				if (c.getId() == client.getId()) {
					personPosition--;
					break;
				}
			}

			for (Choice c : this.listChoice) { // récupére la position du choix
												// dans le tableau
				choicePosition++;
				if (c.getId() == choice.getId()) {
					choicePosition--;
					break;
				}
			}

			if (personPosition > listClient.size() - 1) {
				this.help(); // si la position est superieure a la taille du
								// tableau
				// c'est que le client n'est pas dans le tableau
			} else if (choicePosition > listChoice.size() - 1) {
				this.help(); // idem pour un choix
			} else {
				this.arrayVote[personPosition][choicePosition] = nbToken;
			}
		}
	}

	// Cree les id que l'on donnera a chaque utilisateur.

	public int generateIDPerson() {

		int id = 0;

		boolean belonged = true;
		while (belonged == true) {

			for (int j = 0; j < 4; j++) {
				Random r = new Random();
				id = (int) (id + (r.nextInt(10)) * (Math.pow(10, j)));
			}
			if (!this.listID.contains(id)) {
				belonged = false;
			}

		}
		this.listID.add(id);
		return (int) ((this.id) * (Math.pow(10, 4)) + id);
	}

	public int generateIDRun() throws IOException {

		int id = 0;

		String filename = "runs.txt";
		boolean belonged = true;

		while (belonged == true) {
			for (int j = 0; j < 4; j++) {
				Random r = new Random();
				id = (int) (id + (1 + (r.nextInt(9))) * (Math.pow(10, j)));
			}

			String line = null;
			FileReader verif = new FileReader(filename);

			BufferedReader bufferedReader = new BufferedReader(verif);
			try {
				while ((line = bufferedReader.readLine()) != null) {
					if (!line.equals(String.valueOf(id))) {
						belonged = false;
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("Unable to open file '" + filename + "'");
			} catch (IOException e) {
				System.out.println("Error reading file '" + filename + "'");
			} finally {
				if (verif != null) {
					bufferedReader.close();
				}
			}
		}

		FileWriter file = new FileWriter(filename, true);
		file.write("\n" + String.valueOf(id));
		file.close();

		return id;
	}

	public void sendInvitation() {
		for (Client client : this.listClient) {
			SendEmail mailInvitation = new SendEmail();
			try {
				String body = DefaultMessages.invitationBody + client.getId() + "\n" + "Vous avez "
						+ this.getTokenNumber() + " token a repartir entre les choix suivants :\n";
				for (Choice choice : this.listChoice) {
					body = body + choice.getName() + " id : " + choice.getId() + "\n";
				}

				body = body + "\n\n" + DefaultMessages.helpBody;

				mailInvitation.sendMail(client.getAddress(), DefaultMessages.invitationSubject, body);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void sendDecision() {
		int i = 0;
		int[][] tempa = this.arrayVote;
		Decision.solveAssignmentProblem(tempa);
		int[] count = new int[tempa.length];
		for (int j = 0; j < count.length; j++) {
			for (int x = 0; x < tempa[0].length; x++) {
				for (int n = 0; n < tempa.length; n++) {
					if (tempa[x][n] > count[j]) {
						count[j] = tempa[x][n];
					}
				}
			}
		}
		for (Client client : this.listClient) {
			Choice c = this.listChoice.get(count[i]);
			SendEmail mailDecision = new SendEmail();
			try {
				String body = DefaultMessages.decisionBody + c.getName();
				mailDecision.sendMail(client.getAddress(), DefaultMessages.decisionSubject, body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
