package exception;

public class UserNotPermitted extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8948677106961115271L;

	public UserNotPermitted(String name) {
		super("User mit username " + name + " hat keine ausreichenden Rechte!!");
	}

}
