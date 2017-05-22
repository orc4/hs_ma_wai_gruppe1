package data_model;

import java.net.URI;

public class Cam {
	// Private Variablen
	private long id;
	private String name;
	private URI uri;

	public Cam() {
		super();
	}

	// Konstruktoren
	public Cam(long id, String name, URI uri) {
		super();
		this.id = id;
		this.name = name;
		this.uri = uri;
	}

	public Cam(String name, URI uri) {
		super();
		this.name = name;
		this.uri = uri;
	}

	// Gettermethoden
	public long getId() {
		return (id);
	}

	public String getName() {
		return (name);
	}

	public URI getUri() {
		return (uri);
	}

	// Getter- und Setter-Methoden
	// Settermethoden
	public void setName(String name) {
		this.name = name;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
}