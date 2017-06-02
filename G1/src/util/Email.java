package util;

import javax.mail.internet.InternetAddress;

public class Email {
	private InternetAddress from;
	private String subject;
	private String body;
	private MailAction action; // represents what we have to do with the mail

	public Email(InternetAddress from, String subject, String body) {
		this.from = from;
		this.subject = subject;
		this.body = body;
		this.action = new MailAction();
	}

	public MailAction getAction() {
		return action;
	}

	public InternetAddress getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	/**
	 * Add a new text before the actual text of this mail
	 * 
	 * @param textBefore
	 *            the text to add
	 */
	public void addTextBefore(String textBefore) {
		this.body = textBefore + this.body;
	}

	public String getText() {
		StringBuilder b = new StringBuilder();
		b.append(this.getSubject());
		b.append('\n');
		b.append(this.getBody());
		return b.toString();
	}
}
