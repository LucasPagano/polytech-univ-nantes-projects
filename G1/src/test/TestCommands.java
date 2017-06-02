package test;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import util.Email;

import commands.Client;
import commands.Commands;
import commands.Initiator;
import commands.Run;

import exceptions.ChoiceNotFoundException;
import exceptions.CommandNotAvailableException;
import exceptions.RunNotFoundException;
import exceptions.RunNotSpecifiedException;
import exceptions.UserNotFoundException;
import exceptions.UserNotSpecifiedException;
import exceptions.VoteTotalOfTokensIncorrect;

public class TestCommands {

	private Commands command;
	
	@Before
	public void createCommand() {
		command= new Commands();
		
		//The initiator create and configure a run
		try {
			command.initMail();
			
			command.createRun("init@mail.com", 0, 9);

			command.tokenCount(10);
			command.description("This is a description");
			command.addClient("client0@mail.com", 0);
			command.addClient("client1@mail.com", 1);
			command.addClient("client2@mail.com", 2);
			command.addClient("client3@mail.com", 3);
			command.addClient("client4@mail.com", 4);
			command.addClient("client5@mail.com", 5);
			command.addClient("client6@mail.com", 6);
			command.addClient("client7@mail.com", 7);
			command.addClient("client8@mail.com", 8);
			command.addChoice("choice0", 0);
			command.addChoice("choice1", 1);
			command.addChoice("choice2", 2);

			
			
			command.endMail();
						
		} catch (Exception e) {
			System.out.println("Erreur lors de l'initialisation des tests");
		}
	}
	
	// For getRun
	@Test
	public void getRunEqual() throws RunNotFoundException, AddressException {	
		Run run = command.getRun(0);
		Run runEqual = new Run(0, 0);
		runEqual.addInitiator("init@mail.com", 9);
		runEqual.tokenCount(10);
		runEqual.description("This is a description");
		for (int i = 0; i < 9; i++) {
			runEqual.addClient("client" + i + "@mail.com", i);
		}
		for (int i = 0; i < 3; i++) {
			runEqual.addChoice("choice" + i, i);
		}
		Assert.assertEquals(run, runEqual);
	}
	
	@Test(expected=RunNotFoundException.class)
	public void getRunNotFound() throws RunNotFoundException {
		command.getRun(1);
	}
	
	//For run
	
	@Test(expected=RunNotFoundException.class)
	public void runNotFound() throws RunNotFoundException, UserNotFoundException {
		command.run(1);
	}
	
	//For user
	
	@Test(expected=UserNotFoundException.class)
	public void userNotFound() throws UserNotFoundException, RunNotSpecifiedException {
		command.user(10);
	}
	
	// For status
	
	@Test
	public void statusClient() throws RunNotFoundException, UserNotFoundException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(1);
		System.out.println("---------------------------------");
		System.out.println("Statut client :");
		List<Email> emails = command.status(new InternetAddress());
		System.out.println(emails.get(0).getBody());
	}
	
	@Test
	public void statusInitiator() throws RunNotFoundException, UserNotFoundException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		System.out.println("---------------------------------");
		System.out.println("Statut initiator :");
		List<Email> emails = command.status(new InternetAddress());
		System.out.println(emails.get(0).getBody());
	}
	
	// For vote
	
	public void voteOK() throws RunNotFoundException, UserNotFoundException, ChoiceNotFoundException, CommandNotAvailableException, VoteTotalOfTokensIncorrect, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(0);
		command.vote(0, 3);
		command.vote(1, 4);
		command.vote(0, 1);
		command.vote(2, 2);
		command.user(1);
		command.user(2);
		command.vote(0, 10);
		command.endMail();
	
		command.initMail();
		command.run(0);
		command.user(0);
		Client client = (Client) command.getUser();
		
		Assert.assertEquals(client.getTokensUsed(), 0);
		int tokensChoice1 = client.getTokenRepartition().get(0);
		int tokensChoice2 = client.getTokenRepartition().get(1);
		int tokensChoice3 = client.getTokenRepartition().get(2);
		Assert.assertEquals(tokensChoice1, 4);
		Assert.assertEquals(tokensChoice2, 4);
		Assert.assertEquals(tokensChoice3, 2);		
	}
	
	@Test(expected=VoteTotalOfTokensIncorrect.class)
	public void voteTokensUnderTotal() throws RunNotFoundException, UserNotFoundException, ChoiceNotFoundException, CommandNotAvailableException, VoteTotalOfTokensIncorrect, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(0);
		command.vote(0, 3);
		command.vote(1, 4);
		command.vote(0, 1);
		command.vote(2, 1);
		command.endMail();
	}
	
	@Test(expected=VoteTotalOfTokensIncorrect.class)
	public void voteTokensOverTotal() throws RunNotFoundException, UserNotFoundException, ChoiceNotFoundException, CommandNotAvailableException, VoteTotalOfTokensIncorrect, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(0);
		command.vote(0, 3);
		command.vote(1, 4);
		command.vote(0, 1);
		command.vote(2, 3);
		command.endMail();
	}
	
	// For follow
	@Test
	public void followOK() throws UserNotFoundException, RunNotFoundException, VoteTotalOfTokensIncorrect, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(0);
		command.follow(1);
		command.endMail();
		
		command.initMail();
		command.run(0);
		command.user(0);
		Client follower = (Client) command.getUser();
		command.user(1);
		Client followed = (Client) command.getUser();
		
		Assert.assertSame(follower.getFollowed(), followed);
	}
	
	// For createRun
	@Test
	public void createRun() throws UserNotFoundException, RunNotFoundException, VoteTotalOfTokensIncorrect, AddressException, RunNotSpecifiedException {
		command.initMail();
		command.createRun("init@mail.com", 1, 9);
		command.endMail();
		
		command.initMail();
		command.run(1);
		command.user(9);
		Initiator initiator = (Initiator) command.getUser();
		Initiator initiatorEqual = new Initiator(9, "init@mail.com");
		
		Assert.assertEquals(initiator, initiatorEqual);
	}
	
	// For addInitiator
	@Test
	public void addInitiatorOK() throws RunNotFoundException, UserNotFoundException, RunNotSpecifiedException, AddressException, UserNotSpecifiedException, CommandNotAvailableException, VoteTotalOfTokensIncorrect {
		command.initMail();
		command.run(0);
		command.user(9);
		command.addInitiator("init@mail.com", 10);
		command.endMail();
		
		command.initMail();
		command.run(0);
		command.user(10);
		command.tokenCount(12);
		command.endMail();
		
		int tokensMax = command.getRun(0).getMaxToken();
		Assert.assertEquals(tokensMax, 12);
	}
	
	
	// For tokenCount
	@Test
	public void tokenCountOK() throws UserNotFoundException, RunNotFoundException, VoteTotalOfTokensIncorrect, CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		command.tokenCount(12);
		command.endMail();
		
		int tokensMax = command.getRun(0).getMaxToken();
		Assert.assertEquals(tokensMax, 12);
	}
	
	// For description
	@Test
	public void descriptionOK() throws UserNotFoundException, RunNotFoundException, VoteTotalOfTokensIncorrect, CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		command.description("new description");
		command.endMail();
		
		String description = command.getRun(0).getDescription();
		Assert.assertEquals(description, "new description");
	}
	
	// For addClient
	@Test
	public void addClientOK() throws UserNotFoundException, RunNotFoundException, VoteTotalOfTokensIncorrect, CommandNotAvailableException, AddressException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		command.addClient("client10@mail.com", 10);
		command.endMail();
		
		command.initMail();
		command.run(0);
		command.user(10);
		
		Client client = (Client) command.getUser();
		Client clientEqual = new Client(10, "client10@mail.com");
		Assert.assertEquals(client, clientEqual);
	}
	
	@Test(expected=AddressException.class)
	public void addClientBadAddress() throws RunNotFoundException, UserNotFoundException, CommandNotAvailableException, AddressException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		command.addClient("badEmail", 10);
	}
	
	// For delClient
	@Test(expected=UserNotFoundException.class)
	public void delClientOK() throws UserNotFoundException, RunNotFoundException, CommandNotAvailableException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		command.delClient(1);
		command.user(1);
	}
	
	
	//For addChoice
	@Test
	public void addChoiceOK() throws UserNotFoundException, RunNotFoundException, VoteTotalOfTokensIncorrect, CommandNotAvailableException, ChoiceNotFoundException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		command.addChoice("choice10", 10);
		command.endMail();
		
		command.initMail();
		command.run(0);
		command.user(0);
		command.vote(10, 10);
		command.endMail();
	}
	
	// For delChoice
	@Test(expected=ChoiceNotFoundException.class)
	public void delChoiceOK() throws UserNotFoundException, RunNotFoundException, CommandNotAvailableException, ChoiceNotFoundException, RunNotSpecifiedException, UserNotSpecifiedException {
		command.initMail();
		command.run(0);
		command.user(9);
		command.delChoice(1);
		command.user(1);
		command.vote(1, 10);
	}
}
