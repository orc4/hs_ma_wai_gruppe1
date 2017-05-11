package data_model;

public class User {

	private long id;
	private String vorname;
	private String nachname;
	private boolean can_mod_cam = false;
	private boolean can_mod_user = false;
	private boolean can_see_all_cam = false;
	private boolean can_delegate_cam = false;

	public boolean isCan_mod_cam() {
		return can_mod_cam;
	}

	public boolean isCan_mod_user() {
		return can_mod_user;
	}

	public boolean isCan_see_all_cam() {
		return can_see_all_cam;
	}

	public boolean isCan_delegate_cam() {
		return can_delegate_cam;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public long getId() {
		return id;
	}

	public User(long id, String vorname, String nachname) {
		super();
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
	}

}
