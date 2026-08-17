package Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;

public class Parser {

	public String getSubject() {
		return subject;
	}

	public Object getContent() {
		return content;
	}

	public String getSender() {
		return sender;
	}

	public int getID() {
		return ID;
	}

	// la commande envoyée
	public String getCommand() {
		return command;
	}

	// les tokens du run
	public Integer getTokencount() {
		return tokencount;
	}

	// la description du run
	public String getRundescription() {
		return rundescription;
	}

	public int[] getClienttabidx() {
		return clienttabidx;
	}

	// tableau contenant les addresses des clients
	public ArrayList<String> getClienttab() {
		return clienttab;
	}

	// tableau contenant les ids des clients a supprimer
	public ArrayList<Integer> getClientdelidx() {
		return clientdelidx;
	}

	// tableau contenant les choix a ajouter
	public ArrayList<String> getAddchoices() {
		return addchoices;
	}

	// tableau contenant les choix a supprimer
	public ArrayList<Integer> getClientdelchoix() {
		return clientdelchoix;
	}

	// tableau contenant les addresses des initiateurs
	public ArrayList<String> getInits() {
		return inits;
	}

	// hashmap qui contient les votes et le nombre de token associé a chaque
	public HashMap<String, String> getVotes() {
		return votes;
	}

	// texte pour invitation
	public String getInvtext() {
		return invtext;
	}

	// texte pour decision
	public String getDectext() {
		return dectext;
	}

	private static Pattern pattern;
	private static Matcher matcher;
	private String subject;
	private Object content;
	private String sender;
	private int ID;
	private String command;
	private Integer tokencount;
	private String rundescription;
	private int[] clienttabidx;
	private ArrayList<String> clienttab;
	private ArrayList<Integer> clientdelidx;
	private ArrayList<String> addchoices;
	private ArrayList<Integer> clientdelchoix;
	private ArrayList<String> inits;
	private HashMap<String, String> votes;
	private String invtext;
	private String dectext;
	private Integer runID;
	private String sendername;
	private String sendersurname;
	private String contents;

	public String getContents() {
		return contents;
	}

	public Parser(Message mail) throws Exception {
		int indx = 0;
		this.clientdelchoix = new ArrayList<Integer>();
		this.clienttab = new ArrayList<String>();
		this.addchoices = new ArrayList<String>();
		this.clientdelidx = new ArrayList<Integer>();
		this.inits = new ArrayList<String>();
		this.subject = mail.getSubject();
		this.sender = InternetAddress.toString(mail.getFrom());
		this.command="";
		pattern = Pattern.compile("^[\\w]+\\s");
		matcher = pattern.matcher(this.sender);
		while (matcher.find()) {
			this.sendersurname = this.sender.substring(matcher.start(), matcher.end() - 1);
			indx = matcher.end();
		}

		pattern = Pattern.compile("[\\w]+\\s");
		matcher.region(indx, this.sender.length());

		while (matcher.find()) {
			this.sendername = this.sender.substring(matcher.start(), matcher.end() - 1);

		}
		this.content = mail.getContent();
		this.contents = this.content.toString();
		// mailValid(this.contents);

	}

	public String getSendername() {
		return this.sendername;
	}

	public String getSendersurname() {
		return this.sendersurname;
	}

	public static void parseNomMail(String mail) {
		boolean finded = false;
		pattern = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$");
		matcher = pattern.matcher(mail);
		while (matcher.find()) {
			System.out.println("c'est une @-mail valide!");
			finded = true;
		}
		if (!finded) {
			System.out.println("marche pas");
		}
	}

	public boolean mailValid(String contents) {
		// le message commence soit par USER[RUN USER] soit par
		// USER[RUN USER] ou CREATE_RUN
		pattern = Pattern.compile("CREATERUN");
		matcher = pattern.matcher(contents);
		while (matcher.find()) {
			this.command = "CREATERUN";

			pattern = Pattern.compile("[\\w]+\\.[\\w]+@[.]+\\.[.]+\\s");
			matcher = pattern.matcher(contents);
			while (matcher.find()) {
				this.inits.add((contents).substring(matcher.start(), matcher.end() - 1));
			}
			pattern = Pattern.compile("TOKENCOUNT\\s[0-9]{1,2}");
			matcher = pattern.matcher(contents);
			while (matcher.find()) {
				this.tokencount = Integer
						.valueOf(((String) content).substring(matcher.start() + 11, matcher.start() + 13));
			}

			pattern = Pattern.compile("DESCRIPTION\\s[\\w]*\\.");
			matcher = pattern.matcher(contents);
			while (matcher.find()) {
				this.rundescription = (contents).substring(matcher.start() + 12, matcher.end());
			}

			return true;
		}
		pattern = Pattern.compile("RUN\\s[0-9]{4}");
		matcher = pattern.matcher(contents);
		while (matcher.find()) {
			System.out.println("in run");
			this.runID = Integer.valueOf((contents).substring(matcher.start() + 4, matcher.end()));
			pattern = Pattern.compile("STATUS");
			matcher = pattern.matcher(contents);
			while (matcher.find()) {
				this.command = "STATUS";
			}
			int m = 0;
			pattern = Pattern.compile("VOTE\\s[0-9]{8},[0-9]{1,2}");
			matcher = pattern.matcher(contents);
			while (matcher.find()) {
				this.command = "VOTE";
				this.votes.put((contents).substring(matcher.start() + 5, matcher.start() + 13),
						(contents).substring(matcher.start() + 15, matcher.end()));
			}

			pattern = Pattern.compile("ADDCLIENT\\s[\\S]+\\s|$");
			matcher = pattern.matcher(contents);
			m = 0;
			while (matcher.find()&&!matcher.hitEnd()) {
				this.command = "ADDCLIENT";
				this.clienttab.add(contents.substring(matcher.start()+10, matcher.end()-1));
				m++;
				matcher.region(matcher.end(), contents.length());
			}

			pattern = Pattern.compile("DELCLIENT\\s[0-9]{8}");
			matcher = pattern.matcher(contents);
			m = 0;
			while (matcher.find()) {
				this.command = "DELCLIENT";
				this.clientdelidx.add(Integer.valueOf((contents).substring(matcher.start() + 10, matcher.end())));
				m++;
			}

			pattern = Pattern.compile("ADDCHOICE\\s[\\w]*\\.");
			matcher = pattern.matcher(contents);
			m = 0;
			while (matcher.find()) {
				this.command = "ADDCHOICE";
				this.addchoices.add((contents).substring(matcher.start() + 10, matcher.end()));
			}
			
			pattern = Pattern.compile("DELCHOICE\\s[0-9]{8}");
			matcher = pattern.matcher(contents);
			m = 0;
			while (matcher.find()) {
				this.command = "DELCHOICE";
				this.clientdelchoix.add(Integer.valueOf((contents).substring(matcher.start() + 10, matcher.end())));
				m++;
			}

			pattern = Pattern.compile("SENDINVITATION\\s[\\w]*\\.");
			matcher = pattern.matcher(contents);
			while (matcher.find()) {
				this.command = "SENDINVITATION";
				this.invtext = (contents).substring(matcher.start() + 15, matcher.end());
			}

			pattern = Pattern.compile("SENDDECISION\\s[\\w]*\\.");
			matcher = pattern.matcher(contents);
			while (matcher.find()) {
				this.command = "SENDDECISION";
				this.dectext = (contents).substring(matcher.start() + 15, matcher.end());
			}
			return true;
		}
		return false;

	}

	public Integer getRunID() {
		return this.runID;
	}

	public String getNamefromAddress(String address) {
		pattern = Pattern.compile("[\\w]+\\.");
		matcher = pattern.matcher(address);
		while (matcher.find()) {
			return address.substring(matcher.start(), matcher.end() - 1);
		}
		return "invalid";
	}

	public String getSurnamefromAddress(String address) {
		pattern = Pattern.compile("\\.[\\w]+@");
		matcher = pattern.matcher(address);
		while (matcher.find()) {
			return address.substring(matcher.start() + 1, matcher.end() - 1);
		}
		return "invalid";
	}

	public String getAddressfromAddress(String address) {
		pattern = Pattern.compile("@[\\w]");
		matcher = pattern.matcher(address);
		while (matcher.find()) {
			return address.substring(matcher.start() + 1, matcher.end());
		}
		return "invalid";
	}

}