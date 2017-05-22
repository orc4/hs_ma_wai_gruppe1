package storage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import data_model.Cam;
import data_model.Picture;
import data_model.User;

public interface Storage {
	// Erledigt
	public List<Cam> getCamList();

	public void addPic(Picture pic);

	public List<Cam> getCamForUser(long User);

	public Object addCam(Cam cam) throws SQLException, NamingException;
	
	public void editCam(long id, Cam newCam);
	// Erledigt
	public void delCam(long id);

	public void addUser(User user);

	public void delUser(long id);

	public void editUser(long id, User user);
	// Erledigt
	public List<User> listUser();

	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit);

	public List<Date> getMonthsWithPictures(long camId, Date from, Date to);

	public List<Date> getDaysWithPictures(long camId, Date from, Date to);

	public List<Date> getHoursWithPictures(long camId, Date from, Date to);

	public Picture getPicture(long id);

	public User getUserByName(String username);

	public User getUserById(Long id);

	public void setUserCamAllow(long userId, long camId);

	public void unsetUserCamAllow(long userId, long camId);
}