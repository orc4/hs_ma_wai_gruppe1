package storage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import data_model.Cam;
import data_model.Picture;
import data_model.User;

public interface Storage {
	// User
	public List<User> getListUser();													// Erledigt
	public void addUser(User user);														// Erledigt
	public void delUser(long id);														// Erledigt
	public void editUser(long id, User user);											// Erledigt
	public User getUserByName(String username);											// 
	public User getUserById(Long id);													// 
	public void setUserCamAllow(long userId, long camId);								// 
	public void unsetUserCamAllow(long userId, long camId);								// 

	// Cameras
	public List<Cam> getCamList();														// Erledigt
	public List<Cam> getCamForUser(long User);											// 
	public Object addCam(Cam cam) throws SQLException, NamingException;					// 
	public void delCam(long id);														// Erledigt 
	public void editCam(long id, Cam newCam);											// 
	
	// Picture
	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit);	// 
	public void addPic(Picture pic);													// 
	public Picture getPicture(long id);													// 
	
	// Sonstiges
	public List<Date> getMonthsWithPictures(long camId, Date from, Date to);			// 
	public List<Date> getDaysWithPictures(long camId, Date from, Date to);				// 
	public List<Date> getHoursWithPictures(long camId, Date from, Date to);				// 
}