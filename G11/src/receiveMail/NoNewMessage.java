package receiveMail;

public class NoNewMessage extends Exception{
	public NoNewMessage() {
		System.out.println("Pas de nouveau message !");
	}
}
