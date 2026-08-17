package java_Nantes;
import java.io.FileReader;
import java.util.Properties;



public class config1 {
	
 public static void main (String[] args)  {
		
		try (FileReader reader = new FileReader("config")){
			Properties properties = new Properties();
			properties.load(reader);
			
			String initiator = properties.getProperty("initiator");
			String choices = properties.getProperty("choices");
			String follower = properties.getProperty("follower");
			String selection = properties.getProperty("selection");
			String tokens = properties.getProperty("tokens");
			String decision = properties.getProperty("decision");
			String client = properties.getProperty("client");
			String run = properties.getProperty("run");
			String constraint = properties.getProperty("constraint");
			String constraintP = properties.getProperty("constraintP");
			String constraintN = properties.getProperty("constraintN");
			
			String incomingServer_type = properties.getProperty("incomingServer_type");
			String outgoingServer_type = properties.getProperty("outgoingServer_type");
			
			
			
			// choices = run number
			//client=  unique number
			//run = unique number
		
			//follower = client = client2
			// client -> initiator -> Run
			//Run = clients -> choices
			// initiators start runs
			//SELECTION: The process of automatic assignment of CLIENTS to CHOICES. It results in a (complete or incomplete) DECISION.
			
			//decision==choice  The user interaction is performed through the mail server
			
			
			System.out.println(incomingServer_type);
			System.out.println(outgoingServer_type);
			
			System.out.println(tokens);
			System.out.println(constraintP);
			System.out.println(constraintN);
			System.out.println(run);
			System.out.println(client);
			System.out.println(constraint);
			System.out.println(decision);
			System.out.println(selection);
			System.out.println(choices);
			System.out.println(follower);
			System.out.println(initiator);
			
			
			

		}catch (Exception e) {
			;
			e.printStackTrace();
		}
	}

}

