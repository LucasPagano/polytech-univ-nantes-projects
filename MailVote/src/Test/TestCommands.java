package Test;
import org.junit.Before;
import org.junit.Test;

import Commands.Commands;
import Exceptions.RunNotFoundException;
import Exceptions.UserNotFoundException;

public class TestCommands {
	//Questions pour Marcus :
	//Pas de méthodes pour ajouter des initiateurs ?
	//Pourquoi "CREATERUN ADDRESS" ? l'addresse de l'initiateur devrait être directement le mec qui a lancé la commande ?
	//Status doit être forcément lancé après "USER IDENTIFIER" ? et si non retourne le status pour l'utilisateur ?
	//Quand un e-mail contient une erreur (exemple suppression d'un client non existant), faut-il bien exécuter les commandes sans erreurs précédents et suivant cette erreur ?
	//Ou au contraire ne rien faire de l'e-mail ?

	private Commands command;
	
	@Before
	public void createCommand() {		
		command= new Commands();
		
		//The initiator create and configure a run
		try {
			command.initMail("john.doe@etu.univ-nantes.fr");
			
			command.createRun("useless ?");

			command.tokenCount(10);
			command.description("This is a description");
			command.addClient("mailClient1");
			command.addClient("mailClient2");
			command.addClient("mailClient3");
						
		} catch (Exception e) {
			System.out.println("Erreur lors de l'initialisation des tests");
		}
	}
	
	
	@Test(expected=RunNotFoundException.class)
	public void runNotFound() throws RunNotFoundException, UserNotFoundException {
		command.run(100);
	}
	
	@Test
	public void statusClient() throws RunNotFoundException, UserNotFoundException {
		command.initMail("mailClient1");
		command.run(0);
		System.out.println("---------------------------------");
		System.out.println("Statut client :");
		System.out.println(command.status());
	}
	
	@Test
	public void statusInitiator() throws RunNotFoundException, UserNotFoundException {
		command.initMail("john.doe@etu.univ-nantes.fr");
		command.run(0);
		System.out.println("---------------------------------");
		System.out.println("Statut initiator :");
		System.out.println(command.status());
	}
	
}
