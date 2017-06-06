package server;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;



public class TestServer implements Server{
	private Path hostname;
	private String adress;
	public TestServer(String hostname){
		adress = hostname;
		try {
			this.hostname = Paths.get(new URI("file://" + hostname));
			try {
				if(!Files.exists(this.hostname)){
					Files.createDirectory(this.hostname);
					initServer();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initServer(){
		createFolder("sent");
		createFolder("inbox");
	}
	
	public String getAdress(){
		return adress;
	}
	public void clearInbox(){
		try{
			File inboxDir = new File(new URI(hostname.toUri().toString() + "/inbox"));
			for(File f: inboxDir.listFiles()){
				f.delete();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//dir path relative to host dir 
	public boolean createFolder(String dirPath){
		
		boolean ok = true;
		Path dir = null;
		
		try {
			dir = Paths.get(new URI(hostname.toUri().toString() + "/" + dirPath));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			ok = false;
		}
		try {
			if(!Files.exists(dir)){
				Files.createDirectory(dir);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok = false;
		}
		
		return ok;
	}
	public List<String> foldersList(String foldername){
		File inbox = null;
		ArrayList<String> names = new ArrayList<String>();

		try {
			inbox = new File(new URI(hostname.toUri().toString() + "/" + foldername));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(File f: inbox.listFiles()){
			if(f.isDirectory()){
				names.add(f.getName());
			}
		}
		return names;
	}

	public List<Email> readFolder(String foldername){
		File inbox = null;
		
		try {
			inbox = new File(new URI(hostname.toUri().toString() + "/" + foldername));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Email> inbox_mails = new ArrayList<Email>();
		File files[] = inbox.listFiles();
		Arrays.sort(files, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));

		for(File f: files){
			if(!f.isDirectory()){
				//System.out.println(f.getName());
				try {
					inbox_mails.add(Email.stringsToEmail((Files.readAllLines(f.toPath(), Charset.forName("UTF-8")))));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
		}

		return inbox_mails;
	}

	public void writeMail(String destination, Email m){
		int id = 0;
		File sentFolder = null;
		Path mail_path = null;
		Charset encoding = Charset.forName("UTF-8");
		
		try {
			sentFolder = new File(new URI(hostname.toUri().toString() + "/" + destination));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		id = sentFolder.list().length;
		
		try {
			mail_path = Paths.get(new URI(sentFolder.toURI().toString() + "/mail" + id));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Files.createFile(mail_path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Files.write(mail_path, m.toArray(), encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(Email m){
		for(String to: m.to){
			writeMail("sent", m);
			new TestServer(to).writeMail("inbox", m);
		}
	}
	
	public List<Email> readInbox(){
		return readFolder("inbox");
	}

	public void move(Email m, String foldername){
		writeMail(foldername, m);
	}
	/*
	//test unitaire ok
	public static void main(String[] args){
		Server test1 = new TestServer("/home/mok33/MailVote@test.com");
		test1.createFolder("Run");

		for(Email m: test1.readInbox()){
			System.out.println("Subject : " + m.getSubject().get(0));
			test1.send(m, "/home/mok33/MailVote@test.com");
			test1.move(m, "Run");
		}
	}
	*/
}
