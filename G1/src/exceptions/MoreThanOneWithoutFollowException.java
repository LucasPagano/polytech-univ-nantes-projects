package exceptions;

import decision.hungarian.ClientGroup;


public class MoreThanOneWithoutFollowException extends Exception {

	private static final long serialVersionUID = 5067977453006503149L;

	private ClientGroup group;

	public MoreThanOneWithoutFollowException(ClientGroup group) {
		this.group = group;
	}

	public ClientGroup getGroup() {
		return group;
	}

}
