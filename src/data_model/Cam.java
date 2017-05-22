package data_model;

import java.net.URI;

public class Cam {
	// Private Variablen
	private long id;
	private String name;
	private URI uri;
	private long interval;
	
	// Konstruktoren
	public Cam(long id, String name, URI uri, long interval) {
		super();
		this.id = id;
		this.name = name;
		this.uri = uri;
		this.interval = interval;
	}

	public Cam(String name, URI uri, long interval) {
		super();
		this.name = name;
		this.uri = uri;
		this.interval = interval;
	}

	public Cam() {
		super();
	}

	// Getter- und Setter-Methoden
	// Settermethoden
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public void setInterval(long interval) {
		this.interval = interval;
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

	public long getInterval() {
		return (interval);
	}
}