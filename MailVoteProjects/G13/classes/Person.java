package classes;

import java.io.IOException;
import java.io.Serializable;

import envoie_reception_mails.SendEmail;

public abstract class Person implements Serializable{
	private int id;

	private String name;
	private String address;
	private String surname;

	public Person(int id, String name, String surname, String address) {
		this.id = id;
		this.address = address;
		this.name = name;
		this.surname = surname;

	}

	public int getId() {
		return this.id;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public void helpMessages() {
		SendEmail helpMail = new SendEmail();
		try {
			helpMail.sendMail(this.getAddress(), DefaultMessages.helpSubject, DefaultMessages.helpBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
