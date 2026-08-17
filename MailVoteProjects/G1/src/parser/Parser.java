package parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.mail.internet.AddressException;

import serverCommunication.SendMail;
import util.Email;

import commands.Commands;

import exceptions.ChoiceNotFoundException;
import exceptions.CommandNotAvailableException;
import exceptions.RunNotFoundException;
import exceptions.RunNotSpecifiedException;
import exceptions.UserNotFoundException;
import exceptions.UserNotSpecifiedException;
import exceptions.VoteTotalOfTokensIncorrect;

/**
 * A class which parses an e-mail and stock all the commands it contains.
 * @author Monvoisin Mathilde
 */

/** Assumes UTF-8 encoding. JDK 7+. */
public class Parser {

	private static List<Command> listOfExistingCommands = new ArrayList<Command>(); // all commands
	private List<Command> listCommandsFoundInMail;
	private Email email;
	private List<Email> mailsToSend;
	private static Commands commands = new Commands();
	private boolean readOnly; // Has to be true not to send mails

	/**
	 * Constructor.
	 * 
	 * @param boolean readOnly, true means that we should not move the message from where it is
	 */
	public Parser(boolean readOnly) {

		this.listCommandsFoundInMail = new ArrayList<Command>();
		this.mailsToSend = new ArrayList<Email>();

		this.readOnly = readOnly;

		// general commands
		Parser.listOfExistingCommands.add(new Command("HELP"));
		Parser.listOfExistingCommands.add(new Command("RUN"));
		Parser.listOfExistingCommands.add(new Command("USER"));
		Parser.listOfExistingCommands.add(new Command("STATUS"));

		// client commands
		Parser.listOfExistingCommands.add(new Command("VOTE"));
		Parser.listOfExistingCommands.add(new Command("FOLLOW"));

		// initiator commands
		Parser.listOfExistingCommands.add(new Command("CREATERUN"));
		Parser.listOfExistingCommands.add(new Command("TOKENCOUNT"));
		Parser.listOfExistingCommands.add(new Command("DESCRIPTION"));
		Parser.listOfExistingCommands.add(new Command("ADDCLIENT"));
		Parser.listOfExistingCommands.add(new Command("DELCLIENT"));
		Parser.listOfExistingCommands.add(new Command("ADDINITIATOR"));
		Parser.listOfExistingCommands.add(new Command("ADDCHOICE"));
		Parser.listOfExistingCommands.add(new Command("DELCHOICE"));
		Parser.listOfExistingCommands.add(new Command("SENDINVITATION"));
		Parser.listOfExistingCommands.add(new Command("SENDDECISION"));
	}

	/**
	 * Intermediary method which calls processLine
	 * 
	 * @param the
	 *            entire content of the email
	 */
	public final void processLineByLine(String stringToParse) throws IOException {
		try (Scanner scanner = new Scanner(stringToParse)) {
			while (scanner.hasNextLine()) {
				Command newCommand = new Command();
				newCommand = processLine(scanner.nextLine());
				if (newCommand.getName() != "0") {
					this.listCommandsFoundInMail.add(newCommand);
				}
			}
			scanner.close();
		}
	}

	/**
	 * Returns the command if it is written in a valid way, "0" instead.
	 * 
	 * @param the
	 *            line to process
	 */
	protected Command processLine(String aLine) {
		// use a second Scanner to parse the content of each line
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter(" ");
		Command newCommand = new Command();
		if (scanner.hasNext()) {
			String mot1 = scanner.next();
			mot1 = mot1.toUpperCase();
			newCommand.setName(mot1.trim());

			// if newCommand is a command
			if (newCommand.isCommand()) {

				// if newCommand is a command without attribute
				if (newCommand.isCommandToBeUsedAlone()) {

					// if there's more than one word on the line, then we know that's an invalid line
					if (scanner.hasNext()) {
						newCommand.setName("0");
					}

					// if newCommand is a command with attribute
				} else {

					// if there's more than one word on the line
					if (scanner.hasNext()) {
						String mot2 = scanner.next();
						newCommand.setAttribute(mot2.trim()); // this second word may be a valid attribute

						// if there's more than two words on the line
						if (scanner.hasNext()) {
							String mot3 = scanner.next();

							// the only command that can be used with two arguments is VOTE or...
							if (newCommand.getName().equals("VOTE")) {

								// if there's exactly three words on the line
								if (!scanner.hasNext()) {

									StringBuilder newAttribute = new StringBuilder(newCommand.getAttribute());
									newAttribute.deleteCharAt(newAttribute.length() - 1);
									newCommand.setAttribute(newAttribute.toString());

									try {
										Integer NumberOfTokens = Integer.parseInt(mot3.trim());
										newCommand.setNumberOfTokens(NumberOfTokens);
									} catch (Exception e) {
										newCommand.setName("0");
										newCommand.setAttribute(null);
										scanner.close();
										return newCommand;
									}

								}

								// ...DESCRIPTION, SENDINVITATION or SENDDECISION
							} else if (newCommand.getName().equals("DESCRIPTION")
									| newCommand.getName().equals("SENDINVITATION")
									| newCommand.getName().equals("SENDDECISION")) {
								StringBuilder description = new StringBuilder(newCommand.getAttribute());
								description.append(" " + mot3.trim());

								while (scanner.hasNext()) {
									description.append(" " + scanner.next());
								}

								newCommand.setAttribute(description.toString());

								// if there's more than three words on the line AND it's not description, then it's
								// INVALID
							} else {
								newCommand.setName("0");
								newCommand.setAttribute(null);
								scanner.close();
								return newCommand;
							}
						}

						// SENDINVITATION and SENDDECISION can be used without attribute
					} else if (newCommand.getName().equals("SENDINVITATION")
							| newCommand.getName().equals("SENDDECISION")) {
						newCommand.setAttribute("");

						// if there's exactly one word on the line, then we know that's an invalid line
					} else {
						newCommand.setName("0");
						newCommand.setAttribute(null);
					}
				}

				// if newCommand is not a command
			} else {
				newCommand.setName("0");
				newCommand.setAttribute(null);
			}
		}
		scanner.close();

		return newCommand;
	}

	/**
	 * Execute the commands that have been found in the e-mail parsed
	 * 
	 * @throws RunNotFoundException
	 *             , CommandNotAvailableException, UserNotFoundException, ChoiceNotFoundException
	 * @throws VoteTotalOfTokensIncorrect
	 * @throws UserNotSpecifiedException
	 * @throws RunNotSpecifiedException
	 */
	public void executeCommandsInMail() throws RunNotFoundException, CommandNotAvailableException,
			UserNotFoundException, ChoiceNotFoundException, VoteTotalOfTokensIncorrect, RunNotFoundException,
			UserNotSpecifiedException, RunNotSpecifiedException {
		Parser.commands.initMail();
		this.mailsToSend.clear();
		for (Command comm : listCommandsFoundInMail) {
			switch (comm.getName()) {

			// general commands
			case "HELP":
				this.mailsToSend.addAll(Parser.commands.helpMessage(this.email.getFrom()));
				break;

			case "RUN":
				Parser.commands.run(Double.parseDouble(comm.getAttribute()));
				break;

			case "USER":
				Parser.commands.user(Double.parseDouble(comm.getAttribute()));
				break;

			case "STATUS":
				this.mailsToSend.addAll(Parser.commands.status(this.email.getFrom()));
				break;

			// client commands
			case "VOTE":
				Parser.commands.vote(Double.parseDouble(comm.getAttribute()), comm.getNumberOfTokens());
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "FOLLOW":
				Parser.commands.follow(Double.parseDouble(comm.getAttribute()));
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			// initiator commands
			case "CREATERUN":
				try {
					this.mailsToSend.addAll(Parser.commands.createRun(comm.getAttribute()));
					this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
					this.email.getAction().setNewRun(true);
					this.email.getAction().setMailTobeSaved(true);

				} catch (AddressException e) {
					Parser.commands.helpMessage(this.email.getFrom());
				}
				break;

			case "TOKENCOUNT":
				Parser.commands.tokenCount(Integer.parseInt(comm.getAttribute()));
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "DESCRIPTION":
				Parser.commands.description(comm.getAttribute());
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "ADDCLIENT":
				try {
					this.mailsToSend.addAll(Parser.commands.addClient(comm.getAttribute()));
					this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
					this.email.getAction().setMailTobeSaved(true);
				} catch (AddressException e) {
					Parser.commands.helpMessage(this.email.getFrom());
				}
				break;

			case "DELCLIENT":
				Parser.commands.delClient(Double.parseDouble(comm.getAttribute()));
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "ADDINITIATOR":
				try {
					this.mailsToSend.addAll(Parser.commands.addInitiator(comm.getAttribute()));
				} catch (AddressException e) {
					Parser.commands.helpMessage(this.email.getFrom());
				}
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "ADDCHOICE":
				Parser.commands.addChoice(comm.getAttribute());
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "DELCHOICE":
				Parser.commands.delChoice(Double.parseDouble(comm.getAttribute()));
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "SENDINVITATION":
				this.mailsToSend.addAll(Parser.commands.sendInvitation(comm.getAttribute()));
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			case "SENDDECISION":
				this.mailsToSend.addAll(Parser.commands.sendDecision(comm.getAttribute()));
				this.email.getAction().setRunID(Parser.commands.getCurrentRun().getId());
				this.email.getAction().setMailTobeSaved(true);
				break;

			default:
				throw new CommandNotAvailableException(comm.getName());
			}

			Parser.commands.endMail();
		}
	}

	/**
	 * Tests if the mail is valid, throws exception otherwise
	 * 
	 * @throws CommandNotAvailableException
	 */
	public void mailIsValid() throws CommandNotAvailableException {
		for (Command command : this.listCommandsFoundInMail) {
			// we test the attribute only if it a type of command with attribute
			if (!command.isCommandToBeUsedAlone()) {

				if (!command.hasCorrectTypeOfAttribute()) {
					throw new CommandNotAvailableException(command.getName());
				}
			}
		}
	}

	public List<Command> parseEmail() {

		try {
			this.processLineByLine(email.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listCommandsFoundInMail;

	}

	public void executeEmail() {

		try {
			this.mailIsValid();
			try {
				this.executeCommandsInMail();
				if (!(this.readOnly)) {
					SendMail.sendMail(this.mailsToSend);
				}
			} catch (RunNotFoundException | CommandNotAvailableException | UserNotFoundException
					| ChoiceNotFoundException | VoteTotalOfTokensIncorrect | RunNotSpecifiedException
					| UserNotSpecifiedException e) {
				this.email.getAction().setMailTobeSaved(false);
				if (!(this.readOnly)) {
					Email errorMail = new Email(this.email.getFrom(), "Command Error", e.getErrorMessage());
					SendMail.sendMail(errorMail);
				}
			}
		} catch (CommandNotAvailableException e1) {
			this.email.getAction().setMailTobeSaved(false);
			if (!(this.readOnly)) {
				Email errorMail = new Email(this.email.getFrom(), "Command Error", e1.getErrorMessage());
				SendMail.sendMail(errorMail);
			}

		}
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * Method to be called from outside this class to create the mail
	 * 
	 * @param mail
	 */
	public void parse(Email mail) {
		this.mailsToSend.clear();
		this.listCommandsFoundInMail.clear();
		this.email = mail;
		if (this.readOnly) {
			this.email.getAction().setMailTobeSaved(true);
		}

		this.parseEmail();
		this.executeEmail();
	}

	/**
	 * @return the list of commands that have been found in mail
	 */
	public List<Command> getListCommandsFoundInMail() {
		return this.listCommandsFoundInMail;
	}

	/**
	 * @return the attribute commands
	 */
	public Commands getCommands() {
		return commands;
	}
}
