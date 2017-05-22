package storage;

import java.sql.Date;
import java.util.List;

import data_model.Cam;
import data_model.Picture;
import data_model.User;

public interface Storage {
	public void addCam(Cam cam); //

	public void addPic(Picture pic); //

	public void addUser(User user); // Erledigt

	public void delCam(long id); // Erledigt

	public void delUser(long id); // Erledigt

	public void editCam(long id, Cam newCam); //

	public void editUser(long id, User user); // Erledigt

	public List<Cam> getCamForUser(long User); //

	// Cameras
	public List<Cam> getCamList(); // Erledigt

	public List<Date> getDaysWithPictures(long camId, Date from, Date to); //

	public List<Date> getHoursWithPictures(long camId, Date from, Date to); //
	// User

	public List<User> getListUser(); // Erledigt
	// Sonstiges

	public List<Date> getMonthsWithPictures(long camId, Date from, Date to); //

	public Picture getPicture(long id); //
	// Picture

	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit); //

	public User getUserById(Long id); //

	public User getUserByName(String username); //

	public void setUserCamAllow(long userId, long camId); //

	public void unsetUserCamAllow(long userId, long camId); //
}