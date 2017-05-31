package exception;

public class CamNotDeletedException extends RuntimeException {
	private static final long serialVersionUID = -7698641429484099538L;

	public CamNotDeletedException(Long id) {
		super("Cam mit der Id " + id + " konnte nicht gelöscht werden!");
	}
}