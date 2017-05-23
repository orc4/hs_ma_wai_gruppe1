package test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.impl.protocol.giopmsgheaders.CancelRequestMessage_1_2;

import data_model.Cam;
import data_model.Picture;
import data_model.User;
import storage.Storage;
import storage.StorageFactory;

public class test_v1 extends HttpServlet {
	private static final long serialVersionUID = 1679245794661032467L;
	final Storage storageDao = StorageFactory.getInstance().getStorage();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = storageDao.getListUser();
		List<Cam> cams = storageDao.getCamList();
		List<Cam> camsUser = storageDao.getCamForUser(2);

		
		// Test f�r getUserByName
//		String user = ("Admin");
//		System.out.println(storageDao.getUserByName(user));

		// Test f�r getPicture
//		storageDao.getPicture(1);
		
//		// Test f�r getUserById
//		User s = storageDao.getUserById((long)2);
//		System.out.println(s);

		
		// Test f�r CamForUser
//		for (Cam cam : camsUser) {
//			System.out.println(cam.getId());
//			System.out.println(cam.getName());
//			System.out.println(cam.getUrl());
//		}
		
		
		// Test f�r Picture hinzuf�gen		
//		Date date = new Date(new java.util.Date().getTime());
//		Picture pic = new Picture(date, "blub",1);
//		storageDao.addPic(pic);
		
		// Test Cam hinzuf�gen
//		URL url = null;
//		url = new URL("https://www.google.de/");
//		Cam c = new Cam("newCamera1", url);
//		storageDao.addCam(c);

		// Test Picture hinzuf�gen
//		Picture pic = new Picture("shit");
//		storageDao.addPic(pic);
		
		// Test Cam zum l�schen
//		storageDao.delCam(4);
		
		// Test User hinzuf�gen
//		User u = new User("Abeiter", "Thomas", "Bender", "ggg1", "salt", false, false, false, false);
//		storageDao.addUser(u);
		
		// Test User zu L�schen
//		storageDao.delUser(3);
		
		// Test f�r User
//		 for (User user : users) {
//		 System.out.println(user.getId());
//		 System.out.println(user.getUsername());
//		 System.out.println(user.getVorname());
//		 System.out.println(user.getNachname());
//		 System.out.println(user.getPassword());
//		 System.out.println(user.getSalt());
//		 System.out.println(user.isCan_mod_cam());
//		 System.out.println(user.isCan_see_all_cam());
//		 System.out.println(user.isCan_delegate_cam());
//		 System.out.println(user.isCan_mod_user());
//		 }

		// Test f�r Cams
//		for (Cam cam : cams) {
//			System.out.println(cam.getId());
//			System.out.println(cam.getName());
//			System.out.println(cam.getUrl());
//		}

		super.doGet(req, resp);
	}
}