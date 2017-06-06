package server;
import java.util.List;

public interface Server{
	
	public List<Email> readInbox() ;
	public void send(Email m);
	public boolean createFolder(String foldername);
	public void move(Email mail, String foldername);
	public String getAdress();
	public List<Email> readFolder(String foldername) throws Exception;
	public void clearInbox();
	public List<String> foldersList(String foldername) throws Exception;
}

