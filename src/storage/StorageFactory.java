package storage;

public class StorageFactory {
	// Private Variablen
	private static StorageFactory instance = new StorageFactory();
	private final Storage myStorage;
	public StorageFactory(){
		myStorage= new StorageImpl();
	}
	
	// Methoden
	public static StorageFactory getInstance() {
		return (instance);
	}

	public Storage getStorage() {
		return myStorage;
	}
}