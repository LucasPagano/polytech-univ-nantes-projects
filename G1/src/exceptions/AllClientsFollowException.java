package exceptions;

import decision.hungarian.ClientGroup;

public class AllClientsFollowException extends Exception {

	private static final long serialVersionUID = 5067977453006503149L;

	private ClientGroup group;

	public AllClientsFollowException(ClientGroup group) {
		this.group = group;
	}

	public ClientGroup getGroup() {
		return group;
	}

}
