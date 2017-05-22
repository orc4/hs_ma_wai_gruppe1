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
		this.id = id;
		this.path = path;
		this.date = date;
	}

	public Picture(String path, Date date) {
		this.path = path;
		this.date = date;
	}

	public Cam getCam() {
		return (cam);
	}

	public Date getDate() {
		return (date);
	}

	// Gettermethoden
	public long getId() {
		return (id);
	}

	public String getPath() {
		return (path);
	}
}