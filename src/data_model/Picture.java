package data_model;

import java.sql.Date;

public class Picture {
	// Private Variablen
	private long id;
	private Cam cam;
	private Date date;
	private String path;

	// Konstruktoren
	public Picture(long id, String path, Date date) {
		super();
		this.id = id;
		this.path = path;
		this.date = date;
	}
	
	// Gettermethoden
	public long getId() {
		return (id);
	}
	
	public Cam getCam() {
		return (cam);
	}
	
	public Date getDate() {
		return (date);
	}
	
	public String getPath() {
		return (path);
	}
}