package data_model;

public class User {
	// Private Variablen
	private long id;
	private final String username;
	private final String vorname;
	private final String nachname;
	private final String password;
	private final String salt;
	private final boolean can_mod_cam;
	private final boolean can_mod_user;
	private final boolean can_see_all_cam;
	private final boolean can_delegate_cam;

	//Konstruktoren
	public User(long id, String username, String vorname, String nachname, String password, String salt, boolean can_mod_cam, boolean can_mod_user, boolean can_see_all_cam, boolean can_delegate_cam) {
		super();
		this.id = id;
		this.username = username;
		this.vorname = vorname;
		this.nachname = nachname;
		this.password = password;
		this.salt = salt;
		this.can_mod_cam = can_mod_cam;
		this.can_mod_user = can_mod_user;
		this.can_see_all_cam = can_see_all_cam;
		this.can_delegate_cam = can_delegate_cam;
	}
	
	public User(String username, String vorname, String nachname, String password, String salt, boolean can_mod_cam, boolean can_mod_user, boolean can_see_all_cam, boolean can_delegate_cam) {
		super();
		this.username = username;
		this.vorname = vorname;
		this.nachname = nachname;
		this.password = password;
		this.salt = salt;
		this.can_mod_cam = can_mod_cam;
		this.can_mod_user = can_mod_user;
		this.can_see_all_cam = can_see_all_cam;
		this.can_delegate_cam = can_delegate_cam;
	}
	
	// Gettermethoden
	public long getId() {
		return (id);
	}

	public String getNachname() {
		return (nachname);
	}

	public String getPassword() {
		return (password);
	}

	public String getSalt() {
		return (salt);
	}
	
	public String getUsername() {
		return (username);
	}
	
	public String getVorname() {
		return (vorname);
	}

	public boolean isCan_delegate_cam() {
		return can_delegate_cam;
	}

	public boolean isCan_mod_cam() {
		return can_mod_cam;
	}

	public boolean isCan_mod_user() {
		return can_mod_user;
	}

	public boolean isCan_see_all_cam() {
		return can_see_all_cam;
	}
}