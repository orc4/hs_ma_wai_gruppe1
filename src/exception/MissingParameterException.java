package exception;

public class MissingParameterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6902020676814441079L;

	public MissingParameterException() {
		super("Es Liegt ein Fehler bei den Parametern vor - bitte alles korrekt ausfüllen!!");
	}

}