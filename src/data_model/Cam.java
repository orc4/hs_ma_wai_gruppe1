package data_model;

import java.net.URL;

public class Cam {
	// Private Variablen
	private long id;
	private final String name;
	private final URL url;
	
	public Cam(long id, String name, URL url) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
	}
	
	// Konstruktoren
	public Cam(String name, URL url) {
		super();
		this.name = name;
		this.url = url;
	}
	// Gettermethoden
	public long getId() {
		return (id);
	}
	
	public String getName() {
		return (name);
	}

	public URL getUrl() {
		return (url);
	}
}