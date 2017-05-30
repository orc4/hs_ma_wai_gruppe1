package data_model;

import java.sql.Date;

public class Picture {
	// Private Variablen
	private long id;
	private final long camId;
	private final Date date;
	private final String path;

	// Konstruktoren
	public Picture(long camId, Date date, String path) {
		this.date = date;
		this.path = path;
		this.camId = camId;
	}

	public Picture(long id, long camId, Date date, String path) {
		this.id = id;
		this.date = date;
		this.path = path;
		this.camId = camId;
	}
	
	public long getCamId() {
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