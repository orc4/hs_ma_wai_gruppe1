package data_model;

import java.sql.Date;

public class Picture {
	// Private Variablen
	private long id;
	private long camId;
	private Date date;
	private String path;

	// Konstruktoren
	public Picture(Date date, String path, long camId) {
		this.date = date;
		this.path = path;
	}

	public Picture(long id, Date date, String path, long camId) {
		this.id = id;
		this.date = date;
		this.path = path;
		this.camId = camId;
	}

	public long getCam() {
		return (camId);
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