package data_model;

import java.net.URL;

public class Cam {
	// Private Variablen
	private long id;
	private String name;
	private URL url;
	
	// Konstruktoren
	public Cam(){
		super();
	}

	public Cam(String name, URL url) {
		super();
		this.name = name;
		this.url = url;
	}
	
	public Cam(long id, String name, URL url) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
	}

	// Getter- und Setter-Methoden
	// Settermethoden
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUrL(URL url) {
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