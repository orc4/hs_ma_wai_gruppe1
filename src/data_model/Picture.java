package data_model;

import java.sql.Date;

public class Picture {
	// Private Variablen
	private long id;
	private Cam cam;
	private Date date;
	private String path;

	// Konstruktoren
	public Picture(Date date, String path) {
		this.date = date;
		this.path = path;
	}
	
	public Picture(long id, Date date, String path) {
		this.id = id;
		this.date = date;
		this.path = path;
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