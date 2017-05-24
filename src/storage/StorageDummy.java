package storage;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import data_model.Cam;
import data_model.Picture;
import data_model.User;

public class StorageDummy implements Storage {
	protected List<Cam> cams = new ArrayList<>();
	protected List<User> users = new ArrayList<>();
	protected List<Picture> pics = new ArrayList<>();

	public StorageDummy() {
		Logger logger = Logger.getLogger(StorageDummy.class);
		logger.info("Storage Dummy Initialisiert!!");

		try {
			cams.add(new Cam(1, "Mosbach Martplatz", new URL("http://webcam.mosbach.de/webcam/marktplatz.jpg")));
			cams.add(new Cam(2, "Mosbach Mälzerei", new URL("http://www.maelzerei.de/files/webcam/maelzerei.jpg")));
			cams.add(new Cam(3, "Heilbronn vor Kirche", new URL("https://webcam.heilbronn.de/current.jpg")));

			// cams.add(new Cam(2, "Mannheim Wasserturm", new
			// URI("https://www.mvv-energie.de/webcam_maritim/MA-Wasserturm.jpg"),
			// 60));

			users.add(new User(1, "Aaron", "Letzguss", "aaron", "muster", "", true, true, true, true));
			users.add(new User(2, "Dennis", "nach2", "dennis", "muster", "", true, true, true, true));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void addCam(Cam cam) {
		cams.add(cam);
	}

	@Override
	public void addPic(Picture pic) {
		pic.setId(pics.size());
		System.out.println("Pic mit id " + pic.getId() + " hinzugefügt Camid: " + pic.getCamId());
		pics.add(pic);

	}

	@Override
	public void addUser(User user) {
		users.add(user);
	}

	@Override
	public void delCam(long id) {
		int delid = 0;
		for (int i = 0; i < cams.size(); i++) {
			Cam cam = cams.get(i);
			if (cam.getId() == id) {
				delid = i;
			}
		}
		cams.remove(delid);
	}

	@Override
	public void delUser(long id) {

		int delid = 0;
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (user.getId() == id) {
				delid = i;
			}
		}
		users.remove(delid);

	}

	@Override
	public void editCam(long id, Cam newCam) {
		delCam(id);
		cams.add(newCam);

	}

	@Override
	public void editUser(long id, User user) {
		delUser(id);
		users.add(user);
	}

	@Override
	public List<Cam> getCamForUser(long user) {
		// if (user == 1) {
		// List<Cam> newList = new ArrayList<>();
		// newList.add(cams.get(1));
		// newList.add(cams.get(2));
		// return newList;
		// } else {
		return cams;
		// }
	}

	@Override
	public List<Cam> getCamList() {

		return cams;
	}

	@Override
	public List<Date> getDaysWithPictures(long camId, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Date> getHoursWithPictures(long camId, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture getLatestPicture(long camId) {
		return pics.get(pics.size() - 1);
	}

	@Override
	public List<User> getListUser() {
		return users;
	}

	@Override
	public List<Date> getMonthsWithPictures(long camId, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture getPicture(long id) {
		for (Picture pic : pics) {
			if (pic.getId() == id)
				return pic;
		}
		return null;
	}

	@Override
	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit) {
		return pics;
	}

	@Override
	public User getUserById(Long id) {
		for (User user : users) {
			if (user.getId() == id)
				return user;
		}
		System.out.println("Storage - getUserById - user nicht gefunden!");
		return null;
	}

	@Override
	public User getUserByName(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public void setUserCamAllow(long userId, long camId) {
		System.out.println("StorageDummy: - erlaube user: " + userId + " auf cam " + camId);
	}

	@Override
	public void unsetUserCamAllow(long userId, long camId) {
		System.out.println("StorageDummy: - verbiete user: " + userId + " auf cam " + camId);

	}

}
