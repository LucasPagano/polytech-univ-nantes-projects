package test;

import java.util.Arrays;
import java.util.HashMap;

import javax.mail.internet.AddressException;

import org.junit.Assert;
import org.junit.Test;

import commands.Client;
import commands.Run;

import decision.DecisionMaker;
import decision.hungarian.ClientGroup;
import decision.hungarian.HungarianDecision;
import exceptions.AllClientsFollowException;
import exceptions.MoreThanOneWithoutFollowException;

public class TestHungarianDecision {

	@Test
	public void hungarianDecision() throws AddressException {
		// Random test
		Run run = new Run(0);
		run.addClient("client0@mail.com", 0);
		run.addClient("client1@mail.com", 1);
		run.addClient("client2@mail.com", 2);
		run.addClient("client3@mail.com", 3);
		run.addClient("client4@mail.com", 4);
		run.addClient("client5@mail.com", 5);
		run.addClient("client6@mail.com", 6);
		run.addClient("client7@mail.com", 7);
		run.addClient("client8@mail.com", 8);
		run.addClient("client9@mail.com", 9);

		run.addChoice("Choice 0", 0);
		run.addChoice("Choice 1", 1);
		run.addChoice("Choice 2", 2);
		run.addChoice("Choice 3", 3);
		run.addChoice("Choice 4", 4);
		run.addChoice("Choice 5", 5);
		run.addChoice("Choice 6", 6);

		Client client0 = run.getClients().get(0);
		Client client1 = run.getClients().get(1);
		Client client2 = run.getClients().get(2);
		Client client3 = run.getClients().get(3);
		Client client4 = run.getClients().get(4);
		Client client5 = run.getClients().get(5);
		Client client6 = run.getClients().get(6);
		Client client7 = run.getClients().get(7);
		Client client8 = run.getClients().get(8);
		Client client9 = run.getClients().get(9);

		client8.vote(0, 10);
		// client3.vote(1, 10);
		client4.vote(2, 10);
		client9.vote(4, 10);

		client0.setFollowed(client1);
		client1.setFollowed(client8);
		client2.setFollowed(client0);

		client5.setFollowed(client3);
		client6.setFollowed(client0);
		client7.setFollowed(client9);

		DecisionMaker decisionMaker = new HungarianDecision(run.getClients(), run.getChoices());

		System.out.println("==========RANDOM TEST==========");
		for (ClientGroup group : ((HungarianDecision) decisionMaker).getGroupsList().getList()) {
			System.out.println(Arrays.toString(group.getClients().toArray()));
			try {
				System.out.println("Client with no follow " + group.getNoFollow());
			} catch (AllClientsFollowException | MoreThanOneWithoutFollowException e) {
				e.printStackTrace();
			}
		}

		HashMap<Double, Double> decisions = decisionMaker.makeDecision();
		System.out.println(decisions.toString());

		Assert.assertEquals(decisions.get(0.0), 0.0, 0);
		Assert.assertEquals(decisions.get(1.0), 0.0, 0);
		Assert.assertEquals(decisions.get(2.0), 0.0, 0);
		Assert.assertEquals(decisions.get(3.0), 1.0, 0);
		Assert.assertEquals(decisions.get(4.0), 2.0, 0);
		Assert.assertEquals(decisions.get(5.0), 1.0, 0);
		Assert.assertEquals(decisions.get(6.0), 0.0, 0);
		Assert.assertEquals(decisions.get(7.0), 4.0, 0);
		Assert.assertEquals(decisions.get(8.0), 0.0, 0);
		Assert.assertEquals(decisions.get(9.0), 4.0, 0);
		
	}
}
