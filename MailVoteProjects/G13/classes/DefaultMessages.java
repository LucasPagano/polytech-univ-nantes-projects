package classes;

public class DefaultMessages {

	final static String acknowledge = "Acknowledge - No reply";

	final static String add = "Ajout effectuée";

	final static String remove = "Suppression effectuée";

	final static String vote = "Votre vote a bien été pris en compte";

	final static String invitationBody = "Bonjour,\nvous venez d'être ajoute a un vote, votre id est ";

	final static String invitationSubject = "Invitation a un nouveau vote";

	final static String decisionBody = "Bonjour votre choix attribue est: ";

	final static String decisionSubject = "Attribution choix";

	final static String helpSubject = "Help Message";
	final static String helpBody = "-- start help message ---\n\n" + "Dear user,\n"
			+ "This system provides an interface for voting on a predefined number of choices using a token\n"
			+ "based voting system. After a voting procedure is configured, clients may assign a number of\n"
			+ "tokens to each choice of their preference in order to express their opinion. The system\n"
			+ "automatically assigns each client to one of the choices.\n"
			+ "The vote is set up by an initiator. The initiator creates the run and configures its parameters.\n"
			+ "He then invites the clients. The syntax for registering initiators and clients is defined as \n"
			+ "ADDRESS, containing three fields: Name Surname Mailaddress. An example for ADDRESS\n"
			+ "is Leonardo DaVinci Leonardo.Davinci@someserver.com. The syntax for identifying\n"
			+ "registered runs, choices, clients and initiators is IDENTIFIER which is a random unique 64bit\n"
			+ "number assigned by the system.\n"
			+ "If not stated otherwise, the system answers with a STATUS message to a successfully\n"
			+ "processed request and with an error message detailing the error otherwise\n\n" + "===\n"
			+ "General Commands:\n" + "===\n" + "HELP\n" + "This help message is returned.\n\n" + "RUN IDENTIFIER\n"
			+ "Selects a specific run with the given identifier. Shall always precede any other command\n"
			+ "except CREATERUN and HELP.\n\n" + "USER IDENTIFIER\n"
			+ "Selects the client or initiator by its identifier.\n\n" + "STATUS\n"
			+ "Returns the status of the current evaluation for this particular client or initiator. The response\n"
			+ "for a client contains the configuration parameters of the RUN, the client’s CHOICE, and\n"
			+ "potential FOLLOWER information. The response for an initiator contains the configuration\n"
			+ "parameters for the RUN, the list of all configured CLIENTS and their CHOICES, and the\n"
			+ "current DECISION.\n\n" + "===\n" + "Client Commands:\n" + "===\n" + "VOTE IDENTIFIER, NUMBER_OF_TOKENS\n"
			+ "The client votes for a particular CHOICE by placing a number of tokens on that choice’s\n"
			+ "identifier. One or several VOTE commands are sent in one email. The total number of tokens\n"
			+ "in one email has to match the number of tokens configured by the initiator. A client may send\n"
			+ "several mails to the system when his opinion on the preferences changes. A STATUS is\n"
			+ "returned when the system accepts the choice. An error message is returned if the total\n"
			+ "number of tokens does not match the configuration, when the identifiers specified are invalid\n"
			+ "for this run, or when the client is a FOLLOWER of another client who provided a vote email\n"
			+ "first.\n\n" + "FOLLOW IDENTIFIER\n"
			+ "The client delegates the voting to another client that is specified in the identifier. The identifier\n"
			+ "has to be communicated to the follower by the other client or by an initiator.\n\n" + "===\n"
			+ "Initiator Commands:\n" + "===\n" + "CREATERUN ADDRESS\n"
			+ "Creates and selects a new run. All further commands are configuring the created run. The\n"
			+ "system answers with the initiators version of STATUS.\n\n" + "TOKENCOUNT number\n"
			+ "The number of tokens that a client holds in order to express his preference in this run.\n\n"
			+ "DESCRIPTION text\n" + "The description of the run as presented to the clients.\n\n"
			+ "ADDCLIENT ADDRESS\n" + "Adds a client to the current run.\n\n" + "DELCLIENT identifier\n"
			+ "Deletes a client from the current run.\n\n" + "ADDCHOICE text\n"
			+ "Adds the provide text of the choice to the list of choices.\n\n" + "DELCHOICE identifier\n"
			+ "Deletes the choice corresponding to the identifier from the list of choices.\n\n"
			+ "SENDINVITATION text\n"
			+ "Sends an invitation to all clients providing the additional text in the email, i.e. for identifying\n"
			+ "the initiator who send the text or for specifying a voting deadline. This invitation contains the\n"
			+ "description of the current run and instructions on how to perform the voting with preconfigured\n"
			+ "commands for the answer. As the client may use the reply button in order to perform his vote,\n"
			+ "the text of the message is written such that the interpretation of the system conforms to the\n"
			+ "desired effect of voting.\n\n" + "SENDDECISION text\n"
			+ "Sends a decision mail to all clients, informing each client which choice was assigned to him.\n"
			+ "The additional text is sent to the clients in the mail. This usually finishes a run although clients\n"
			+ "may continue voting and a several decision mails may be sent by the initiator in which case,\n"
			+ "text may be used to identify draft and final decisions.\n\n" + "--- end help message ---";
}
