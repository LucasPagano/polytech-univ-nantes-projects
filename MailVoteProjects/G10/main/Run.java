package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

import user.*;

public class Run {

	public String idRun;
	public String description;
	private int nbClient;
	public int max_token_per_client;
	public HashMap<String, User> users;
	public ArrayList<Choice> choices;
	public HashMap<String, Integer> decisionTable;
	public ArrayList<Choice> expandedChoices;
	public HashMap<String, ArrayList<Double>> tokenMatrix;

	public Run(){
		
		nbClient = 0;
		max_token_per_client = 0;

		users = new HashMap<String, User>();
		choices = new ArrayList<Choice>();
		expandedChoices = new ArrayList<Choice>();
		decisionTable = new HashMap<String, Integer>();
		tokenMatrix = new HashMap<String, ArrayList<Double>>();
	}
	public Run clone(){
		Run r = new Run();
		r.idRun = idRun; 
		r.description = description;
		r.nbClient = nbClient;
		r.max_token_per_client = max_token_per_client;
		r.users = new HashMap<String, User>(users);
		r.choices = new ArrayList<Choice>(choices);
		r.decisionTable = new HashMap<String, Integer>(decisionTable);
		r.expandedChoices = new ArrayList<Choice>(expandedChoices);
		r.tokenMatrix = new HashMap<String, ArrayList<Double>>(tokenMatrix);

		return r;
	}
	public void setDescription(String desc){
		description = desc;
	}
	public void setTokens(Integer tokens){
		max_token_per_client = tokens;
	}

	//Ajoute le client recu en parametre a la liste des clients du run
	public void addUser(User user){
		this.users.put(user.idUser, user);
		if(user instanceof Client){
			this.decisionTable.put(user.idUser, null);
		}
	}

	public void addChoice(Choice c){

		c.idChoice = choices.size();
		this.choices.add(c);

		for(Client cl: getClients().values()){
			cl.addChoice();
			users.put(cl.idUser, cl);
		}
	}

	public User getUser(String id){
		return users.get(id);
	}

	public Collection<User> getUsers(){
		return users.values();
	}

	public ArrayList<String> choicesToString(){
		ArrayList<String> display = new ArrayList<String>();
		int i = 0;
		for(Choice c: choices){
			display.add(i + ") " + c.text);
			i++;
		}

		return display;
	}

	public static Integer UserIdToRunId(Integer userId){
		return userId;
	}

	public void expandChoices(){
		expandedChoices = new ArrayList<Choice>();
		for(Choice ch: choices){
			for(int i = 0; i < ch.getMaxClient(); i++){
				expandedChoices.add(ch);
			}
		}
	}

	public ArrayList<Double> expandTokenVote(ArrayList<Double> tokenVote){
		ArrayList<Double> expanded = new ArrayList<Double>();
		int idChoice = 0;

		for(Choice c : choices){
			for(int i = 0; i < c.getMaxClient(); i++){
				expanded.add(tokenVote.get(idChoice));
			}
			idChoice++;
		}

		return expanded;
	}

	public HashMap<String, Client> getClients(){
		HashMap<String, Client> clients = new HashMap<String, Client>();
		
		for(User u: users.values()){
			if(u instanceof Client){
				clients.put(u.idUser, (Client) u);
			}
		}
		return clients;
	}
	
	public void multiply(ArrayList<Double> l, Double k){
		for(int i = 0; i < l.size(); i++){
			l.set(i, l.get(i) * k);
		}
	}

	public void checkAvailableChoice(ArrayList<Double> l, int groupSize){
		for(int i = 0; i < l.size(); i++){
			if(expandedChoices.get(i).getMaxClient() < groupSize){
				l.set(i, new Double(this.max_token_per_client * -1));
			}
		}
	}

	public void mergeFollowers(){
		HashMap<String, Client> clients = getClients();
		ArrayList<String> keySet = new ArrayList<String>(tokenMatrix.keySet());

		int left = keySet.size() - 1;

		while(left >= 0){

			String idClient = keySet.get(left);
			Client cl = clients.get(idClient);			
			cl.AllFollowers();
			
			for(Client c: cl.followers){
				tokenMatrix.remove(c.idUser);
				left--;
			}

			multiply(tokenMatrix.get(idClient), new Double(cl.followers.size() + 1));
			//checkAvailableChoice(tokenMatrix.get(idClient), cl.followers.size() + 1);

			left--;
		}
	}


	public double[][] generateTokenMatrix(){
		expandChoices();

		for(Client cl: getClients().values()){
			tokenMatrix.put(cl.idUser, expandTokenVote(cl.tokenVote));
			System.out.println(cl.tokenVote);
		}

		mergeFollowers();	

		int nbline = tokenMatrix.keySet().size();
		int nbColumn = new ArrayList<ArrayList<Double>>(tokenMatrix.values()).get(0).size();

		double[][] matrix = new double[nbline][nbColumn];
		

		for(int i = 0; i < nbline; i++){
			for(int j = 0; j < nbColumn; j++){
				matrix[i][j] = tokenMatrix.get(new ArrayList<String>(tokenMatrix.keySet()).get(i)).get(j);
			}
		}

		System.out.println("decision matrix: ");
		System.out.print("\t");
		for(int i = 0; i < expandedChoices.size(); i++){
			System.out.print("\t" + expandedChoices.get(i).text);
		}
		
		System.out.println();
		for(String idU: tokenMatrix.keySet()){
			System.out.print(idU + "\t");
			for(Double d: tokenMatrix.get(idU)){
				System.out.print(d + "\t");
			}
			System.out.println();
		}
		return matrix;
	}

	public void setDecisionTable(int[][] decision){
		int nbline = tokenMatrix.keySet().size();
		int r = 0;
		int c = 0;
		Integer assignedChoice = new Integer(0);
		String clientId = "";

		for(int i = 0; i < nbline; i++){
			r = decision[i][0];
			c = decision[i][1];

			assignedChoice = new Integer(expandedChoices.get(c).idChoice);
			clientId = new ArrayList<String>(tokenMatrix.keySet()).get(r);

			decisionTable.put(clientId, assignedChoice);
			
			for(Client follower: ((Client) users.get(clientId)).followers){
				decisionTable.put(follower.idUser, assignedChoice);
			}
		}
		System.out.println("-----------------");
		displayDecisionTable();
	}

	public String displayUsers(){
		String acc = "";
		for(User u: users.values()){
			acc += u.toString() + "\n";
		}

		return acc;
	}

	public String displayDecisionTable(){
		String acc = "";
		acc = "Last Decision Table\n";

		for(String idCl: decisionTable.keySet()){
			acc += users.get(idCl).mailAdress + "-("+ idCl + ") => " + choices.get(decisionTable.get(idCl)).text + "\n";
		}

		if(decisionTable.isEmpty()){
			acc += "no decisions has been taken yet.";
		}

		return acc;
		
	}
	public ArrayList<User> searchUser(String adress){
		ArrayList<User> res = new ArrayList<User>();

		for(User u: users.values()){			
			if(u.mailAdress.equals(adress)){
				res.add(u);
			}
		}
		return res;
	}
	@Override
	public String toString(){
		String acc = "Run\n";
		acc += description + "\n";
		acc += "Max total token per client: " + max_token_per_client + "\n";
		acc += "Choices : \n";
		for(String ch: choicesToString()){
			acc += ch + "\n";
		}
		return acc;
	}
}


