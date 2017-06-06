package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Random;
import java.util.Iterator;

import server.*;
import user.*;


public class EmailHandler{
	private boolean stop;
	private Email mail;
	private User user;
	private Run run;
	private String idRun;
	private DecisionAlgorithm algo;
	private boolean toTreat;
	private HashMap<String, String> commandToMethod;
	private Server server;
	private RandomSequenceOfUnique idUserGen;
	private ArrayList<Email> toSend;
	private boolean error;

	public EmailHandler(Email m, DecisionAlgorithm a, Server s, boolean treat){
		mail = m;
		algo = a;
		user = null;
		run = null;
		server = s;
		toTreat = treat;

		long runId_seed = BigInteger.probablePrime(15, new Random()).longValue();
		long idUser_seed = BigInteger.probablePrime(15, new Random()).longValue();

		idRun = new RandomSequenceOfUnique(runId_seed, runId_seed + 1).nextBase26();
		idUserGen = new RandomSequenceOfUnique(idUser_seed, idUser_seed + 1);

		commandToMethod = new HashMap<String, String>();
		
		commandToMethod.put("CREATERUN", "createRun");
		commandToMethod.put("DESCRIPTION", "description");
		commandToMethod.put("TOKENCOUNT", "tokenCount");
		commandToMethod.put("ADDCLIENT", "addClient");
		commandToMethod.put("ADDCHOICE", "addChoice");
		commandToMethod.put("STATUS", "getStatus");
		commandToMethod.put("FOLLOW", "follow");
		commandToMethod.put("SENDINVITATION", "sendInvitation");
		commandToMethod.put("USER", "selectUser");
		commandToMethod.put("VOTE", "vote");
		commandToMethod.put("SENDDECISION", "sendDecision");
		commandToMethod.put("HELP", "help");
		commandToMethod.put("DECIDE", "decide");
		commandToMethod.put("ADDINITIATOR", "addInitiator");

		this.stop = false;
		toSend = new ArrayList<Email>();
		this.error = false;
	}

	public void interpret(){
		this.error = false;

		Iterator<String> lines = mail.getBody().iterator();

		while(lines.hasNext() && !this.stop){
			String line = lines.next();
			
			List<String> command = new ArrayList<String>(Arrays.asList(line.split(" ")));

			String name = command.get(0);
			List<String> args = command.subList(1, command.size());
			name = name.replaceAll("[^A-Z]", "");

			if(commandToMethod.containsKey(name)){
				if(user == null || user.hasPermission(name)){
					name = commandToMethod.get(name);
					try{
						this.getClass().getMethod(name, List.class).invoke(this, args);
					}
					catch(Exception e){
						e.printStackTrace();
						this.stop = true;
						error = true;
					}					
				}
				else{
					this.stop = true;
					error = true;
				}
			}
		}		
	
		
		if(toTreat){
			if(!error){
				if(run != null){		


					server.createFolder("Runs/Mails/"+run.idRun);

					server.move(mail, "Runs/Mails/"+run.idRun);
				}

				for(Email m: toSend){
					server.send(m);
				}
			}
			else{
				help(new ArrayList<String>());
				//server.send(toSend.get(toSend.size() - 1));
			}

		}
		else{
			toSend = new ArrayList<Email>();
		}
	}

	public void end(List<String> args){
		this.stop = true;
	}

	public void selectUser(List<String> args){
		System.out.println("selectUser");

		String userId = args.get(0).substring(0, 5);
		String runId = args.get(0).substring(5, 10);

		//System.out.println("User id = " + userId);

		userId += runId;
		this.idRun = runId;

		if(toTreat){
			try{

				restituerRun(runId);	
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		if(run.users.containsKey(userId)){
			this.user = this.run.getUser(userId);
		}
		else{
			System.out.println("bad run id");
		}

		System.out.println(user);
	}

	public void restituerRun(String runId) throws Exception{
		System.out.println("Selected Run :" + runId);
		setRandomGenerator(runId);
		Email old = null;
		if(mail != null){
			
			old = Email.stringsToEmail(this.mail.toArray());	
		}

		User oldU = null;
		boolean toTreat = this.toTreat;

		if(user != null){
 			oldU = user.clone();
		}
		int i = 0;
		List<Email> toRead = server.readFolder("Runs/Mails/"+runId);
		for(Email m: toRead){
			System.out.println("reading " + i + "/" + toRead.size());
			this.stop = false;
			this.error = false;
			this.toTreat = false;
			this.mail = m;
			interpret();
			i++;
		}

		this.toTreat = toTreat;
		this.mail = old;
		user = oldU;
		this.stop = false;
		this.error = false;
	}
	//utiliser pour getStatus
	public HashMap<String, Run> restituerAllRun(){
		boolean old = this.toTreat;

		HashMap<String, Run> runs = new HashMap<String, Run>();
		EmailHandler h = new EmailHandler(null, algo, server, false);

		try{
			for(String runId: server.foldersList("Runs/Mails")){
				try{
					h.restituerRun(runId);	
				}
				catch(Exception e){
					e.printStackTrace();
				}
				runs.put(runId, h.run);
			}
	
		}
		catch(Exception e){

		}	

		return runs;
	}

	public void setRandomGenerator(String runId) throws Exception{
		Email seed = server.readFolder("Runs/Seeds/"+runId).get(0);
		//System.out.println("seed Email : " + seed.body.get(0));
		long idUserSeed = 0;
		String trimed = seed.body.get(0).replaceAll("[^0-9]", "");
		idUserSeed = Long.parseLong(trimed);
		//System.out.println("seed userid " + idUserSeed);
		this.idUserGen = new RandomSequenceOfUnique(idUserSeed, idUserSeed + 1);
		idRun = runId;
	}

	public void help(List<String> args){
		
		Email help = new Email();
		
		help.subject = new ArrayList<String>();
		help.to = new ArrayList<String>();
		help.body = new ArrayList<String>();

		help.from = server.getAdress();
		help.to.add(mail.from);
		help.body.add("--start help message--");
		help.body.add("--end help message--");

		toSend.add(help);
	}

	public void createRun(List<String> args) throws Exception{
		System.out.println("createRun");

		this.run = new Run();
		this.run.idRun = this.idRun;

		this.user = new Initiator(args.get(0), args.get(1), mail.getFrom());
		user.idUser = Long.toString(idUserGen.next(), 26) + this.run.idRun;
		
		this.run.addUser(user);

		System.out.println("idInitiator : " + user.idUser);
		if(toTreat){
			Email seed = new Email();
			seed.to = new ArrayList<String>();
			seed.subject = new ArrayList<String>();
			seed.body = new ArrayList<String>();
			seed.from = "me";

			seed.body.add(Long.toString(idUserGen.seedBase));
			server.createFolder("Runs/Seeds/"+run.idRun);
			server.move(seed, "Runs/Seeds/"+run.idRun);
		}


	}

	public void description(List<String> args) throws Exception{
		System.out.println("description");
		String desc = "";
		
		for(String ch: args){
			desc += ch + " ";
		}

		this.run.setDescription(desc);
	}

	public void tokenCount(List<String> args) throws Exception{
		System.out.println("tokenCount");
		String token = args.get(0).replaceAll("[^0-9.]", "");
		this.run.setTokens(new Integer(token));
	}

	public void addClient(List<String> args) throws Exception{
		System.out.println("addClient");

		Client clt = new Client(args.get(0), args.get(1), args.get(2));
		
		clt.idRun = run.idRun;
		clt.idUser = Long.toString(idUserGen.next(), 26) + this.run.idRun;

		this.run.addUser(clt);
		//System.out.println(clt.idUser);

	}
	public void addInitiator(List<String> args) throws Exception{
		System.out.println("addInitiator");
		Initiator initiator = new Initiator(args.get(0), args.get(1), args.get(2));
		
		initiator.idRun = run.idRun;
		initiator.idUser = Long.toString(idUserGen.next(), 26) + this.run.idRun;
		
		this.run.addUser(initiator);
		user = initiator;
	}
	public void addChoice(List<String> args) throws Exception{

		System.out.println("addChoice");
		/*
		String trimed = args.get(args.size() - 1).replaceAll("[^0-9.]", "");

		Integer maxCl = new Integer(trimed);
		args.remove(args.size() - 1);
		*/
		String name = "";
		for(String ch: args){
			name += ch + " ";
		}

		Choice c = new Choice(name, 1);
		this.run.addChoice(c);
		
	}
	
	public void vote(List<String> args) throws Exception{
		System.out.println("Vote");
		args = new ArrayList<String>(Arrays.asList(args.get(0).split(",")));
		try{
			String trimed1 = args.get(0).replaceAll("[^0-9.]", "");
			String trimed2 = args.get(1).replaceAll("[^0-9.]", "");
			Client c = (Client) user;

			c.vote(Integer.parseInt(trimed1), Integer.parseInt(trimed2), this.run.max_token_per_client);	
			user = c;
			run.users.put(user.idUser, user);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void follow(List<String> args) throws Exception{
		System.out.println("follow");
		String idToFollow = args.get(0).replaceAll("[^A-Za-z0-9 ]", "");

		User toFollow = run.users.get(idToFollow);
		Client follower = (Client) user;

		follower.follow(toFollow);

		run.users.put(follower.idUser, follower);
		run.users.put(toFollow.idUser, toFollow);

	}

	public void sendDecision(List<String> args) throws Exception{
		System.out.println("send Decision");
		run.setDecisionTable(this.algo.decide(run.generateTokenMatrix()));

		for(Client cl: run.getClients().values()){
			Email invitMail = new Email();

			invitMail.body = new ArrayList<String>();
			invitMail.body.add("Hello User " + cl.idUser);
			invitMail.body.add("You have been assigned the following choice: ");
			invitMail.body.add(run.decisionTable.get(cl.idUser) + ") " + run.choices.get(run.decisionTable.get(cl.idUser)).text);

			invitMail.from = server.getAdress();
			invitMail.to = new ArrayList<String>();
			invitMail.subject = new ArrayList<String>();
			invitMail.to.add(cl.mailAdress);	

			toSend.add(invitMail);
		}
	}

	public void getStatus(List<String> args){
		if(toTreat){

			System.out.println("status");
			Email status = new Email();
			status.body = new ArrayList<String>();
			status.to = new ArrayList<String>();

			status.from = server.getAdress();
			status.to.add(mail.from);
			status.subject = mail.subject;
			status.body.add("You are registered as the following users: ");

			if(user == null){
				for(Run r: restituerAllRun().values()){
					ArrayList<User> res = new ArrayList<User>();

					if(r != null)
					{
						res = r.searchUser(mail.from);
					}
					
					//System.out.println(r.users.values().size() + " " + res.size() + " found in " + r.idRun);
					for(User u: res){
						status.body.add(u.toString());
					}
				}	
			}
			else{
				status.body.add(user.getStatus(run));
			}

			toSend.add(status);	
		}
		
	}

	public void sendInvitation(List<String> args) throws Exception{
		System.out.println("sendInvitation");
		System.out.println("users id's : ");
		for(User u: run.getUsers()){

			Email invitMail = new Email();

			invitMail.body = new ArrayList<String>();
			invitMail.body.add("You have been invited");
			invitMail.body.add("the run bellow");
			invitMail.body.add(run.description);
			invitMail.body.addAll(run.choicesToString());
			invitMail.body.add(u.toString());
			System.out.println(u.idUser);
			invitMail.from = server.getAdress();
			invitMail.to = new ArrayList<String>();
			invitMail.subject = new ArrayList<String>();
			invitMail.to.add(u.mailAdress);	

			toSend.add(invitMail);

		}
	}

}