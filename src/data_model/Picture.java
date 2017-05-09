package data_model;

import java.sql.Date;

public class Picture {

	private long id;
	private String path;
	private Date date;

	public long getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public Date getDate() {
		return date;
	}

	public Picture(long id, String path, Date date) {
		super();
		this.id = id;
		this.path = path;
		this.date = date;
	}

}
