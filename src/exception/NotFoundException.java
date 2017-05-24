package exception;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6740877291318350899L;

	// Für getUserByName
	public NotFoundException(String username) {
		super("User: " + username + " wurde nicht gefunden!");
	}
		
	// Für getUserById
	public NotFoundException(Long id) {
		super("ID: " + id + " wurde nicht gefunden!");
	}
	
	// Für Alle
	public NotFoundException() {
		super("Auswahl könnte nicht aufgelistet werden!");
	}
}