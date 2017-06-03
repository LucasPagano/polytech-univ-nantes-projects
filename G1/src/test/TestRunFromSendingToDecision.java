package test;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import serverCommunication.SendMail;
import util.Email;

public class TestRunFromSendingToDecision {

	public static void main(String[] args) {
		List<Email> liste = new ArrayList<Email>();
		try {

			// run id : 0 init id : 1
			liste.add(new Email(
					new InternetAddress("john.doe.mailvote@gmail.com"),
					"",
					"CREATERUN john.doe.mailvote.test@gmail.com\nDESCRIPTION this is a test\nTOKENCOUNT 10\nADDCLIENT john.doe.mailvote.test@gmail.com\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCLIENT lucas.pagano@etu.univ-nantes.fr\nADDCLIENT john.doe.mailvote.test2@gmail.com\nADDCHOICE choice0\nADDCHOICE choice1\nADDCHOICE choice2\nADDCHOICE choice3"));
			// init id 1 sendinvitation
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.4223267636731538\nSENDINVITATION You are invited"));
			// user id 2 follow user id 3
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.047270382899072594\nFOLLOW 0.46830859707808126"));
			// user id 3 follow user id 10
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.46830859707808126\nFOLLOW 0.6125046431207679"));
			// user id 4 follow user id 2
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.05488749151133887\nFOLLOW 0.047270382899072594"));
			// user id 5 vote choice id 12
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.20503130568439532\nvote 0.9176435805417136, 10"));
			// user id 6 vote choice id 13
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.08184635505306581\nvote 0.43803714005688044, 10"));
			// user id 7 follow user id 5
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.580909978821641\nFOLLOW 0.20503130568439532"));
			// user id 8 follow user id 2
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.488529285358426\nFOLLOW 0.047270382899072594"));
			// user id 9 follow user id 11
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.7015534302602261\nFOLLOW 0.8926083672684249"));
			// user id 10 vote choice id 13
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.6125046431207679\nVOTE 0.43803714005688044, 10"));
			// user id 11 vote choice id 14
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.8926083672684249\nVOTE 0.39314246537879105, 10"));
			// initiator id 1 sendDecision
			liste.add(new Email(new InternetAddress("john.doe.mailvote@gmail.com"), "",
					"RUN 0.47497692040880735\nUSER 0.4223267636731538\nSENDDECISION The decision has been taken"));
			System.out.println("done");
		} catch (AddressException e) {
			e.printStackTrace();
		}
		SendMail.sendMail(liste);
	}

}
