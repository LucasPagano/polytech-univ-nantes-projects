package decision.hungarian;

import java.util.HashMap;
import java.util.List;

import commands.Choice;
import commands.Client;

import decision.DecisionMaker;
import exceptions.AllClientsFollowException;
import exceptions.MoreThanOneWithoutFollowException;

/**
 * A decision maker using hungarian algorithm
 * @author Pagano Lucas
 *
 */
public class HungarianDecision implements DecisionMaker {
	List<Client> clientList;
	int nbChoices;
	// Map needed to map from choiceID to int in order for the hungarian algorithm to work
	HashMap<Integer, Double> choiceMap;
	GroupsList groupsList;

	public HungarianDecision(List<Client> clientList, List<Choice> choicesList) {
		this.clientList = clientList;
		this.choiceMap = this.createChoiceMap(choicesList);
		this.nbChoices = this.choiceMap.size();
		this.groupsList = new GroupsList(clientList);
	}

	/**
	 * Return the initial value of choiceMap
	 * @param choicesList the list of all the choices
	 * @return the value of choiceMap
	 */
	private HashMap<Integer, Double> createChoiceMap(List<Choice> choicesList) {
		HashMap<Integer, Double> choicesMap = new HashMap<>();
		int cpt = 0;
		for (Choice choice : choicesList) {
			choicesMap.put(cpt, choice.getId());
			cpt++;
		}
		return choicesMap;
	}

	@Override
	public HashMap<Double, Double> makeDecision() {

		double[][] choicesMatrix = new double[this.groupsList.list.size()][this.nbChoices];

		for (Integer i = 0; i < this.groupsList.list.size(); i++) {
			ClientGroup group = this.groupsList.list.get(i);
			try {
				// A singler choser by group
				Client choserClient = group.getNoFollow();
				for (Integer j = 0; j < this.nbChoices; j++) {
					// We get the number of tokens associated to the choice
					Integer nbTokens = choserClient.getTokenRepartition().get(this.choiceMap.get(j));
					if (nbTokens == null) {
						choicesMatrix[i][j] = 0;
					} else {
						// The choice is multiplied by the number of members
						choicesMatrix[i][j] = nbTokens * group.clients.size();
					}
				}

			} catch (AllClientsFollowException | MoreThanOneWithoutFollowException e) {
				// In case the groups aren't formed correctly and there is 0 or more than 1 choser by group
				e.printStackTrace();
			}
		}

		// The choices by group
		int[][] choserChoices = HungarianAlgorithm.hgAlgorithm(choicesMatrix, "max");

		// A Hashmap linking the client ID to the choice ID
		HashMap<Double, Double> clientChoices = this.giveChoices(choserChoices);

		return clientChoices;

	}

	/**
	 * Link the client ID to the choices ID, from the groups
	 * @param choserChoices the choices by group
	 * @return a map where the key is the client ID, and the value is the choice
	 */
	private HashMap<Double, Double> giveChoices(int[][] choserChoices) {
		HashMap<Double, Double> clientChoices = new HashMap<>();
		for (ClientGroup group : this.groupsList.list) {
			for (Client client : group.clients) {
				// We put the client's id and the id of the choice given to his group
				clientChoices.put(client.getId(),
				// We retrieve the choice's ID
				this.choiceMap.get(choserChoices[this.groupsList.list.indexOf(group)][1]));
			}
		}

		return clientChoices;
	}

	public GroupsList getGroupsList() {
		return groupsList;
	}
}
