package decision.hungarian;

import java.util.ArrayList;
import java.util.List;

import commands.Client;

/**
 * A class representing the list of all grouped clients
 * @author Pagano Lucas
 */
public class GroupsList {
	ArrayList<ClientGroup> list;

	public ArrayList<ClientGroup> getList() {
		return list;
	}

	/**
	 * Set the value of list
	 * @param clientList the list of all the clients
	 */
	GroupsList(List<Client> clientList) {
		this.list = new ArrayList<ClientGroup>();
		// Form the groups
		this.populateList(clientList);
	}

	/**
	 * Form the groups of clients
	 * @param clientList the list of all the clients
	 */
	private void populateList(List<Client> clientList) {
		for (Client client : clientList) {

			// If client isn't already in one of the groups
			if (!(this.contains(client))) {
				// If he doesn't follow anyone, add him to a new group, and add
				// this group to the groups list
				if (client.getFollowed() == null) {
					this.list.add(new ClientGroup(client));
				} else {
					Client followed = client.getFollowed();
					// He follows someone who's in the groups list
					// Add clientID to the follower's group
					try {

						ClientGroup followerGroup = this.getGroup(followed);
						followerGroup.clients.add(client);
					} catch (Exception e1) {
						// He follows someone who isn't in the groups list
						// Get the list of followed persons
						ArrayList<Client> followingList = new ArrayList<Client>();
						this.buildFollowingList(client, followingList);

						// Try if the last person in the chain is in a group
						try {
							// The last person in the chain is in a group
							// Get it and add others to it
							ClientGroup followerGroup = this.getGroup(followingList.get(followingList.size() - 1));
							followerGroup.clients.remove(followingList.get(followingList.size() - 1));

							for (Client followedInList : followingList) {
								followerGroup.clients.add(followedInList);
							}
						} catch (Exception e2) {
							// The last person in the chain is not in a group
							// Create a new one and add every chain member in it
							ClientGroup group = new ClientGroup();
							for (Client followedInList : followingList) {
								group.clients.add(followedInList);
							}
							this.list.add(group);
						}

					}
				}
			}
		}
	}

	/**
	 * Builds the list representing the chain of clients a client follows chain : client -> followed ->
	 * followed-followed etc
	 * @param client the client to add to the list, with everyone he follows
	 * @param followingList the empty list to be filled
	 */
	private void buildFollowingList(Client client, ArrayList<Client> followingList) {
		followingList.add(client);

		// Stop if we find a client who follows no one or a client who is part
		// of a group
		if (!(client.getFollowed() == null || this.contains(client))) {
			this.buildFollowingList(client.getFollowed(), followingList);
		}
	}

	/**
	 * Check if a client is in a group
	 * @param client the client to be checked
	 * @return true if the client is in a group, false instead
	 */
	private boolean contains(Client client) {
		boolean found = false;

		for (ClientGroup group : this.list) {
			for (Client i : group.clients) {
				if (i == client) {
					found = true;
				}
			}
		}

		return found;
	}


	/**
	 * Get the group in which a client is
	 * @param client the client from the group
	 * @return the group where the client is
	 * @throws Exception
	 */
	private ClientGroup getGroup(Client client) throws Exception  {
		for (ClientGroup group : this.list) {
			for (Client i : group.clients) {
				if (client == i) {
					return group;
				}
			}
		}
		// Will throw if the client is not in a group
		throw new Exception("Client not in groups");
	}
}
