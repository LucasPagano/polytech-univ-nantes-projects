package Freemium;

import java.util.ArrayList;
import java.util.List;

import Freemium.users.Free;
import Freemium.users.Premium;
import Freemium.users.Utilisateur;
import S3_FREEMIUM.CarteBancaire;

public class SytemeFreemiumStreaming {
	
	protected List<Utilisateur> users = new ArrayList<Utilisateur>();
	protected static int day = 0;
	
	protected Thread runner;
	
	
	public SytemeFreemiumStreaming() {
		runner = new Thread() {
			@Override
			public void run() {
				try {
					while(true){
						System.out.println("Update");
						sleep(1000);
						updateSystem();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		runner.start();
	}
	
	protected void updateSystem() {
		day++;
		boolean month = day == 5;
		for(Utilisateur user : users){
			user.updateDaily();
			if(month){
				System.out.println("Monthly update for user " + user.getPersonne().getNom());
				user.updateMonthly();
			}
		}
		if(month) day = 0;
	}
	
	public void addUserToSystem(Utilisateur user){
		users.add(user);
	}
	
	
	public boolean freeToPremium(Free userFree, CarteBancaire cb){
		try {
			int i = users.indexOf(userFree);
			
			if(i > -1){
				Premium userPremium = new Premium(userFree.getPersonne(), cb);
				users.add(i, userPremium);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
