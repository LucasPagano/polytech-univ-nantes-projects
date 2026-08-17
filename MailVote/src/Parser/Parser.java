package Parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Commands.Command;
import Commands.Commands;
import Exceptions.ChoiceNotFoundException;
import Exceptions.CommandNotAvailableException;
import Exceptions.RunNotFoundException;
import Exceptions.UserNotFoundException;


/**
 * A class wich parses an e-mail and stock all the commands it contains.
 * @author Monvoisin Mathilde
 */

/** Assumes UTF-8 encoding. JDK 7+. */
public class Parser {

	public static void main(String... aArgs) throws IOException {
		Parser parser = new Parser("C:\\Users\\Mathilde\\Documents\\COURS\\INFO3_S2\\Java\\mailRecu.txt");
		parser.processLineByLine();
		System.out.println(("Done."));
		
		
//		TO DISPLAY COMMANDS
/*		System.out.println(" Command      " + "    Attribute       " + "   is of correct type       " + "Number of Tokens");
		for(Command command : parser.listCommandsFoundInMail){
			if (command.isCommandToBeUsedAlone()){
				System.out.println(command.getName());
			} else {
				if (command.getNumberOfTokens() == 0){
					System.out.print(command.getName());
					for (int i = 0; i<20 - command.getName().length(); i++){
						System.out.print(" ");
					}
					System.out.print(command.getAttribute());
					for (int i = 0; i<20 - command.getAttribute().length(); i++){
						System.out.print(" ");
					}
					System.out.println(command.hasCorrectTypeOfAttribute());	
					} else {
						System.out.print(command.getName());
						for (int i = 0; i<20 - command.getName().length(); i++){
							System.out.print(" ");
						}
						System.out.print(command.getAttribute());
						for (int i = 0; i<20 - command.getAttribute().length(); i++){
							System.out.print(" ");
						}
						System.out.println(command.hasCorrectTypeOfAttribute()+ "                    " + command.getNumberOfTokens());
				}
				
			}
		}
		
		*/
		
		parser.mailIsValid();
	}
  
  /**
   Constructor.
   @param aFileName full name of an existing, readable file.
  */
	public Parser(String aFileName){
		fFilePath = Paths.get(aFileName);
		
		this.commands = new Commands();
		
		this.listCommandsFoundInMail = new ArrayList<Command>();
		this.listOfExistingCommands = new ArrayList<Command>(); 
		
		//general commands
		this.listOfExistingCommands.add(new Command("HELP"));
		this.listOfExistingCommands.add(new Command("RUN"));
		this.listOfExistingCommands.add(new Command("USER"));
		this.listOfExistingCommands.add(new Command("STATUS"));
		
		//client commands
		this.listOfExistingCommands.add(new Command("VOTE"));
		this.listOfExistingCommands.add(new Command("FOLLOW"));
		
		//initiator commands
		this.listOfExistingCommands.add(new Command("CREATERUN"));
		this.listOfExistingCommands.add(new Command("TOKENCOUNT"));
		this.listOfExistingCommands.add(new Command("DESCRIPTION"));
		this.listOfExistingCommands.add(new Command("ADDCLIENT"));
		this.listOfExistingCommands.add(new Command("DELCLIENT"));
		this.listOfExistingCommands.add(new Command("ADDCHOICE"));
		this.listOfExistingCommands.add(new Command("DELCHOICE"));
		this.listOfExistingCommands.add(new Command("SENDINVITATION"));
		this.listOfExistingCommands.add(new Command("SENDDECISION")); 
	}
  
  
  /** Template method that calls {@link #processLine(String)}.  */
	public final void processLineByLine() throws IOException {
		try (Scanner scanner =  new Scanner(fFilePath, ENCODING.name())){
			while (scanner.hasNextLine()){
				String command;
				Command newCommand = new Command();
				newCommand = processLine(scanner.nextLine());
				if (newCommand.getName() != "0"){
					this.listCommandsFoundInMail.add(newCommand);
				}
			}      
		}
	}

	/**
	 * Returns the command if it is written in a valid way, "0" else. .
	 * @param LineToProcess
	 */
	protected Command processLine(String aLine){
		//use a second Scanner to parse the content of each line 
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter(" ");
		Command newCommand = new Command();
		if (scanner.hasNext()){
			String mot1 = scanner.next();
			mot1 = mot1.toUpperCase();
			newCommand.setName(mot1.trim());
			
			//if newCommand is a command
			if(newCommand.isCommand()){
				
				//if newCommand is a command without attribute
				if (newCommand.isCommandToBeUsedAlone()){
				
					//if there's more than one word on the line, then we know that's an invalid line
					if (scanner.hasNext()){
						newCommand.setName("0");
					}
					
				//if newCommand is a command with attribute
				} else {
					
					//if there's more than one word on the line
					if (scanner.hasNext()){
						String mot2 = scanner.next();
						newCommand.setAttribute(mot2.trim()); //this second word may be a valid attribute
					
						//if there's more than two words on the line
						if (scanner.hasNext()){
							String mot3 = scanner.next();
							
							//if there's exactly three words on the line
							if (!scanner.hasNext()){
								Double NumberOfTokens = Double.parseDouble(mot3.trim());
								
								// the only command that can be used with two arguments
								if (newCommand.getName().equals("VOTE")){
								
									// the second argument has to be a number
									if (NumberOfTokens.isNaN()){
										newCommand.setName("0");
										newCommand.setAttribute("0");
										return newCommand;
									}
									newCommand.setNumberOfTokens(NumberOfTokens);
								}
								
							//if there's more than three words on the line, then INVALID	
							} else {
								newCommand.setName("0");
								newCommand.setAttribute("0");
								return newCommand;
							}
						}
						
					//if there's exactly one word on the line, then we know that's an invalid line
					} else {
						newCommand.setName("0");
						newCommand.setAttribute("0");
					}
				}
				
			//if newCommand is not a command
			} else {
				newCommand.setName("0");
				newCommand.setAttribute("0");
			}
		}
		return newCommand;
	}
	
	/**
	 * Return true if the parameter is a valid command
	 * @param Command that may exist
	 */
	private boolean isCommand(Command commandToCheck){
		boolean isCommand = false;
		for (Command command : listOfExistingCommands){
			if (commandToCheck.getName().equals(command)){
				isCommand = true;
			}
		}
		return isCommand;
	}

	
	
	/**
	 * Execute the commands that have been found in a valid e-mail
	 * @throws RunNotFoundException, CommandNotAvailableException, UserNotFoundException, ChoiceNotFoundException
	 */
	private void ExecuteCommandsInMail() throws RunNotFoundException, CommandNotAvailableException, UserNotFoundException, ChoiceNotFoundException{
		//TODO: initMail
		for (Command comm : listCommandsFoundInMail){
			if (comm.equals("HELP")){
				//command.helpMessage(this.email);
			} else if (comm.equals("RUN")){
				this.commands.run(comm.attributeToInt());
			} else if (comm.equals("USER")){
				this.commands.initMail("blop@hotmail.fr");
			} else if (comm.equals("STATUS")){
				//this.commands.status()
			} else if (comm.equals("VOTE")){
				//TODO
			} else if (comm.equals("FOLLOW")){
				//TODO
			} else if (comm.equals("CREATERUN")){
				commands.createRun("blip@orange.fr");
			} else if (comm.equals("TOKENCOUNT")){
				commands.tokenCount(4);
			} else if (comm.equals("DESCRIPTION")){
				commands.description("Ceci est une description");
			} else if (comm.equals("ADDCLIENT")){
				commands.addClient("riri@truc.com");
			} else if (comm.equals("DELCLIENT")){
				commands.delClient(8);
			} else if (comm.equals("ADDCHOICE")){
				commands.addChoice("Choice1");
			} else if (comm.equals("DELCHOICE")){
				commands.delChoice(2);
			} else if (comm.equals("SENDINVITATION")){
				//TODO
			} else if (comm.equals("SENDDECISION")){
				//TODO
			}
		}
		//TODO: endMail
	}
	
	/**
	 * Return true if the mail parsed is valid
	 */
	private boolean mailIsValid(){
		boolean mailIsValid = true;
		for(Command command : this.listCommandsFoundInMail){
			if (!command.hasCorrectTypeOfAttribute()){
				mailIsValid = false;
			}
		}
		return mailIsValid;
	}
	  
		/**
		 * Return the list of commands that have been found in mail
		 */
		public List<Command> getListCommandsFoundInMail() {
			return this.listCommandsFoundInMail;
		}
	  
		/**
		 * Return the e-mail that is beeing parsed
		 */
		public String getEmail() {
			return email;
		}

	// PRIVATE 
	private List<Command> listOfExistingCommands; //all commands
	private List<Command> listCommandsFoundInMail;
	private String email;
	private final Path fFilePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8; 
	private Commands commands;

}
