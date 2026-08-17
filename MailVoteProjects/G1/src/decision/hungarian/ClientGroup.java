package decision.hungarian;

import java.util.ArrayList;

import commands.Client;

import exceptions.AllClientsFollowException;
import exceptions.MoreThanOneWithoutFollowException;

/*
 * A class representing a group of clients. A group can be composed of a sole person.
 * This class is bound to be part of a GroupsList.
 * */

/**
 * A class representing a group of clients. A group can be composed of a sole person.
 * This class is bound to be part of a GroupsList.
 * @author Pagano Lucas
 */
public class ClientGroup {
	ArrayList<Client> clients;

	public ArrayList<Client> getClients() {
		return clients;
	}

	public ClientGroup() {
		this.clients = new ArrayList<Client>();
	}

	/**
	 * To create the group when a client not following anybody and not in the groupsList is found
	 * @param client the first client of the group
	 */
	ClientGroup(Client client) {
		this.clients = new ArrayList<Client>();
		this.clients.add(client);
	}

	/**
	 * To create the group when a client following someone who isn't in the groupsList is found
	 * @param followingClient the following client to add in the group
	 * @param followedClient the followed client to add in the group
	 */
	ClientGroup(Client followingClient, Client followedClient) {
		this.clients = new ArrayList<Client>();
		this.clients.add(followingClient);
		this.clients.add(followedClient);
	}

	/**
	 * To use when a client is following someone in a group already
	 * @param client the client to add in the group
	 */
	void addToGroup(Client client) {
		this.clients.add(client);
	}

	/**
	 * Return the only client who follows no one in this group
	 * @return the only client who follows no one in this group
	 * @throws AllClientsFollowException No client doesn't follow anyone
	 * @throws MoreThanOneWithoutFollowException More than one client doesn't follow anyone
	 */
	public Client getNoFollow() throws AllClientsFollowException, MoreThanOneWithoutFollowException {
		int numberNoFollow = 0;
		Client notFollowing = null;
		for (Client client : this.clients) {
			if (client.getFollowed() == null) {
				numberNoFollow++;
				notFollowing = client;
			}
		}

		if (numberNoFollow > 1) {
			throw new MoreThanOneWithoutFollowException(this);
		} else if (numberNoFollow < 1) {
			throw new AllClientsFollowException(this);
		} else {
			return notFollowing;
		}
	}
}
