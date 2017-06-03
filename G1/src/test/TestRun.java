package test;
import static org.junit.Assert.assertTrue;

import javax.mail.internet.AddressException;

import org.junit.Test;

import commands.Run;

public class TestRun {
	
	
	@Test
	public void testClone() throws CloneNotSupportedException, AddressException{
		Run run = new Run(0);
		run.addInitiator("init@mail.com");
		run.addChoice("choice1");
		run.addChoice("choice2");
		run.addChoice("choice3");
		run.addClient("client1@mail.com");
		run.addClient("client2@mail.com");
		run.description("This is a description");
		
		Run runCloned = (Run) run.clone();
		
		assertTrue(run.equals(runCloned));
	}
}
