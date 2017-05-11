package exception;

public class UserLoginIncorrect extends RuntimeException {
	private static final long serialVersionUID = 1029235031623622736L;

	public UserLoginIncorrect(String name) {
		super("User mit username: " + name + " konnte nicht gefunden werden oder Passwort Stimmt nicht überein!");
	}

}