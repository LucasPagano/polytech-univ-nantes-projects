package user;
import main.Run;
import exception.*;
import java.util.HashMap;

public abstract class User {
	public String idUser;
	public String idRun;

	protected String firstName;
	protected String lastName;
	public String mailAdress;
	protected HashMap<String, Boolean> privilegeTable;

	public User(String firstName, String lastName, String mailAdress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailAdress = mailAdress;

		privilegeTable = new HashMap<String, Boolean>();

		//default permissions
		privilegeTable.put("CREATERUN", false);
		privilegeTable.put("DESCRIPTION", false);
		privilegeTable.put("TOKENCOUNT", false);
		privilegeTable.put("ADDCLIENT", false);
		privilegeTable.put("ADDCHOICE", false);
		privilegeTable.put("ADDINITIATOR", false);
		privilegeTable.put("SENDINVITATION", false);
		privilegeTable.put("SENDDECISION", false);

		privilegeTable.put("VOTE", false);
		privilegeTable.put("FOLLOW", false);

		privilegeTable.put("STATUS", true);
		privilegeTable.put("USER", true);
		privilegeTable.put("HELP", true);

			
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof User){
			User u = (User) o;
			return u.mailAdress == this.mailAdress;
		}	
		else{
			return false;
		}
	}
	
	public boolean hasPermission(String cmd){
		if(!privilegeTable.get(cmd)){
			System.out.println("user has no permission for " + cmd);
		}
		return privilegeTable.get(cmd);
	}

	public abstract String getStatus(Run r);
	public abstract User clone();
}