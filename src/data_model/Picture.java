package data_model;

import java.sql.Date;

public class Picture {
	// Private Variablen
	private long id;
	private long camId;
	private Date date;
	private String path;

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

	public Picture(long camId) {
		this.camId = camId;
	}

	// Getter- und Setter-Methoden
	// Settermethoden
	public void setId(long id) {
		this.id = id;
	}
	
	public void setCamId(long camId) {
		this.camId = camId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPath(String path) {
		this.path = path;
	}

	// Gettermethoden
	public long getId() {
		return (id);
	}
	
	public long getCamId() {
		return (camId);
	}

	public Date getDate() {
		return (date);
	}
	
	public String getPath() {
		return (path);
	}
}