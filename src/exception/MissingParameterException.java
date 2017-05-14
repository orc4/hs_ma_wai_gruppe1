package exception;

public class MissingParameter extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6902020676814441079L;

	public MissingParameter() {
		super("Es Liegt ein Fehler bei den Parametern vor - bitte alles korrekt ausfüllen!!");
	}

}