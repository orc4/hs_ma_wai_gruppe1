package exception;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6740877291318350899L;

	// F�r Alle
	public NotFoundException() {
		super("Auswahl konnte nicht aufgelistet werden!");
	}
		
	// F�r getUserById
	public NotFoundException(Long id) {
		super("ID: " + id + " wurde nicht gefunden!");
	}
	
	// F�r getUserByName
	public NotFoundException(String username) {
		super("User: " + username + " wurde nicht gefunden!");
	}
}