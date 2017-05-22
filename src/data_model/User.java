package data_model;

public class User {
	// Private Variablen
	private long id;
	private String username;
	private String vorname;
	private String nachname;
	private String password;
	private String salt;
	private boolean can_mod_cam = false;
	private boolean can_mod_user = false;
	private boolean can_see_all_cam = false;
	private boolean can_delegate_cam = false;

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

	// Getter- und Setter-Methoden
	// Settermethoden
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
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

	public boolean isCan_delegate_cam() {
		return can_delegate_cam;
	}
	
	// Gettermethoden
	public long getId() {
		return (id);
	}
	
	public String getUsername() {
		return (username);
	}

	public String getVorname() {
		return (vorname);
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
}