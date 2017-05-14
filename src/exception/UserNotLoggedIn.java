package exception;

public class UserNotLoggedIn extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4902049536774308633L;

	public UserNotLoggedIn() {
		super("Sie müssen sich erst einloggen!");
	}

}