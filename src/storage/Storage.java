package storage;

import java.sql.Date;
import java.util.List;
import data_model.Cam;
import data_model.Picture;
import data_model.User;

public interface Storage {
	// User
	public List<User> getListUser();													// Erledigt
	public void addUser(User user);														// Erledigt
	public void delUser(long id);														// Erledigt
	public void editUser(long id, User user);											// Erledigt
	public User getUserByName(String username);											// Erledigt, aber überprüfen bitte
	public User getUserById(Long id);													// Erledigt, aber überprüfen bitte
	public void setUserCamAllow(long userId, long camId);								// Bitte Anschauen
	public void unsetUserCamAllow(long userId, long camId);								// 

	// Cameras
	public List<Cam> getCamList();														// Erledigt 
	public List<Cam> getCamForUser(long userId);										// Erledigt
	public void addCam(Cam cam); 														// Erledigt
	public void delCam(long id);														// Erledigt 
	public void editCam(long id, Cam newCam);											// Erledigt
	
	// Picture
	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit);	// 
	public void addPic(Picture pic);													// Erledigt
	public Picture getPicture(long id);													// Fertig, aber es fliegt immer Exeption bei ID
	public Picture getLatestPicture(long camId);										// Fertig, aber überprüfen bitte
	
	// Sonstiges
	public List<Date> getMonthsWithPictures(long camId, Date from, Date to);			// 
	public List<Date> getDaysWithPictures(long camId, Date from, Date to);				// 
	public List<Date> getHoursWithPictures(long camId, Date from, Date to);				// 
}