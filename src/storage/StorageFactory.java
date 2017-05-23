package storage;

public class StorageFactory {
	// Private Variablen
	private static StorageFactory instance = new StorageFactory();
	
	// Methoden
	public static StorageFactory getInstance() {
		return (instance);
	}

	public Storage getStorage() {
		return (new StorageImpl());
	}
}