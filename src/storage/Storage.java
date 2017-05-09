package storage;

import java.sql.Date;
import java.util.List;

import data_model.Cam;
import data_model.Picture;
import data_model.User;

public interface Storage {

	public List<Cam> getCamList();

	public void addCam(Cam cam);

	public void editCam(long id, Cam newCam);

	public void delCam(long id);

	public void addUser(User user);

	public void delUser(long id);

	public void editUser(long id, User user);

	public List<User> listUser();

	public List<Picture> getPictureBetween(Cam cam, Date from, Date to, long limit);

	public List<Date> getMonthsWithPictures(Cam cam, Date from, Date to);

	public Picture getPicture(long id);
}
