package user;
import main.Run;
import exception.*;

public class Initiator extends User {
	
	public Initiator(String firstName, String lastName, String mailAdress) {
		super(firstName, lastName, mailAdress);

		privilegeTable.put("CREATERUN", true);
		privilegeTable.put("DESCRIPTION", true);
		privilegeTable.put("TOKENCOUNT", true);
		privilegeTable.put("ADDCLIENT", true);
		privilegeTable.put("ADDCHOICE", true);
		privilegeTable.put("ADDINITIATOR", true);
		privilegeTable.put("SENDINVITATION", true);
		privilegeTable.put("SENDDECISION", true);
	}

	public String toString() {
		return "Initiator :" + idUser + " : " + firstName + " " + lastName + " " + mailAdress;
	}

	public User clone(){
		return new Initiator(firstName, lastName, mailAdress);
	}

	public String getStatus(Run r){
		return r.toString() + "\n User list: \n" + r.displayUsers() + "\n" + r.displayDecisionTable();
	}
}