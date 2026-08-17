package Freemium;

import java.util.Hashtable;

import Freemium.users.Free;
import Freemium.users.Personne;
import Freemium.users.Utilisateur;
import S3_FREEMIUM.AdresseElectronique;

public class Main {

	public static void main(String[] args) {
		// SytemeFreemiumStreaming mySystem = new SytemeFreemiumStreaming();
		// mySystem.addUserToSystem(new Free(new Personne("Jean Eudes")));
		// try {
		// mySystem.addUserToSystem(new Premium(new Personne("Fabien Hudier"),
		// new CarteBancaire(0, 1, 2)));
		// } catch (Exception e) {}
		// }
		//
		Hashtable<AdresseElectronique, Utilisateur> table = new Hashtable<AdresseElectronique, Utilisateur>();

		Utilisateur u1 = new Free(new Personne("Fhudier"));
		Utilisateur u2 = new Free(new Personne("Fhudier2"));
		Utilisateur u3 = new Free(new Personne("Fhudier3"));

		table.put(u1.personne.mail, u1);
		table.put(u2.personne.mail, u2);
		table.put(u3.personne.mail, u3);

		for (Utilisateur util : table.values()) {
			System.out.println(util);
		}
	}
}
