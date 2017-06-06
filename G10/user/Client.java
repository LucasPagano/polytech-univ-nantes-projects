package user;

import java.util.ArrayList;
import java.lang.RuntimeException.*;
import main.Run;
import exception.*;

public class Client extends User{
	
	public int usedToken;
	public ArrayList<Double> tokenVote;
	public ArrayList<Client> followers;
	public boolean canFollow;

	public Client(String firstName, String lastName, String mailAdress) {
		super(firstName, lastName, mailAdress);
		tokenVote = new ArrayList<Double>();
		followers = new ArrayList<Client>();
		canFollow = true;

		privilegeTable.put("FOLLOW", true);
		privilegeTable.put("VOTE", true);
	}

	public User clone(){
		Client c = new Client(firstName, lastName, mailAdress);
		c.tokenVote = new ArrayList<Double>(tokenVote);
		c.followers = new ArrayList<Client>();

		for(Client cl: followers){
			c.followers.add((Client)cl.clone());
		}

		c.canFollow = canFollow;
		c.usedToken = usedToken;

		return c;
	}
	
	public void initVote(int size){
		for(int i = 0; i < size; i++){
			tokenVote.add(new Double(0));
		}
	}

	public void addChoice(){
		tokenVote.add(new Double(0));
	
	}

	public String toString() {
		return "Client :" + idUser + " : " + firstName + " " + lastName + " " + mailAdress;
	}


	public void follow(User u) throws PermissionDeniedException{
		System.out.println(u.idUser);
		if(u instanceof Client){
			Client c = (Client) u;
			System.out.println(c.idUser != this.idUser + " " + followers.contains(c) + " " +c.followers.contains(this));
			if(canFollow && c.idUser != this.idUser && !followers.contains(c) && !c.followers.contains(this)){
				System.out.println("Can Follow yes !");
				c.followers.add(this);
				canFollow = false;
				u = c;
			}
			else{
				throw new PermissionDeniedException();
			}
		}
		else{
			throw new PermissionDeniedException();
		}

	}

	@Override
	public boolean equals(Object o){
		boolean e = false;
		if(o instanceof Client){
			e = ((Client) o).idUser == this.idUser;
		}

		return e;
	}
	public void vote(int idChoice, int nbToken, int maxToken) throws PermissionDeniedException{
		
		int used = usedToken;
		usedToken = (usedToken - tokenVote.get(idChoice).intValue()) + nbToken;
		if(usedToken > maxToken){
			usedToken = used;
			throw new PermissionDeniedException();
		}
		else{
			tokenVote.set(idChoice, new Double(nbToken));	
		}
		
	}

	public void AllFollowers(){
		for(int i = 0; i < followers.size(); i++){
			followers.get(i).AllFollowers();
			this.followers.addAll(followers.get(i).followers);
		}
	}
	public String displayFollowers(){
		String acc = "";
		for(Client c: followers){
			acc += c.toString() + "\n";
		}
		return acc;
	}

	public String displayVote(){
		String acc = "";
		int i = 0;
		for(Double b: tokenVote){
			acc += "n°" + i + " => " + b + "\n";
			i++;
		}
		return acc;
	}

	@Override
	public String getStatus(Run r){
		return r.toString() + "Your followers: " + displayFollowers() + "\nVote: " + displayVote();
	}

}
