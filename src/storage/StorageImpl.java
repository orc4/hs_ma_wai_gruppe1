package storage;

import java.sql.Date;
import java.util.List;

import data_model.Cam;
import data_model.Picture;
import data_model.User;

public class StorageImpl implements Storage {

	@Override
	public List<Cam> getCamList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCam(Cam cam) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editCam(long id, Cam newCam) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delCam(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delUser(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editUser(long id, User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> listUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Picture> getPictureBetween(Cam cam, Date from, Date to, long limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Date> getMonthsWithPictures(Cam cam, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture getPicture(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
