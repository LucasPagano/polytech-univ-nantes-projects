package action;

import java.util.LinkedList;

import javax.mail.Message;

/**
 * Classe commande qui représente une commande.
 * @author iancl
 *
 */
public class Commande {

	private int numberArgs;
	private String name;
	private LinkedList<String> args;
	private String addresse;
	private int idRun;
	private Message message;

	
	public Commande(String name, int numberArgs) {
		this.name = name;
		this.numberArgs = numberArgs;
		this.args = new LinkedList();
	}
	
	public void addArgs(String arg) {
//		if ( this.args.size() < this.numberArgs) {
			this.args.add(arg);
//		}
	}

	public void joinArgs() {
		String tmp = String.join(" ", this.args);
		this.args.clear();
		this.args.add(tmp);

	}
	public LinkedList<String> getArgs() {
		return args;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getIdRun() {
		return idRun;
	}

	public void setIdRun(int idRun) {
		this.idRun = idRun;
	}

	public String getAddresse() {
		return addresse;
	}

	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}

	@Override
	public String toString() {
		return "Commande [numberArgs=" + numberArgs + ", name=" + name + ", args=" + args + ", addresse=" + addresse
				+ ", idRun=" + idRun + "]";
	}

	public int getNumberArgs() {
		return numberArgs;
	}

	public void setNumberArgs(int numberArgs) {
		this.numberArgs = numberArgs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
