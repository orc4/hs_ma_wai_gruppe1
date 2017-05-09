package storage;

public class StorageFactory {

	private static StorageFactory instance = new StorageFactory();

	public static StorageFactory getInstance() {
		return instance;
	}

	public Storage getStorageFactory() {
		return new StorageImpl();
	}

	// _+getStorageFactory():StorageFactory_
	// +getStorage():Storage

}
