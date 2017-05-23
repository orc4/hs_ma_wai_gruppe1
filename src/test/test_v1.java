package test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
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
		
		
		
		// Test Cam hinzufügen
		URL url = new URL("https://www.google.de/");
		Cam c = new Cam("newCamera1", url);
		storageDao.addCam(c);

		
		// Test Picture hinzufügen
//		Picture pic = new Picture("shit");
//		storageDao.addPic(pic);
		
		// Test Cam zum löschen
//		storageDao.delCam(1);
		
		// Test User hinzufügen
//		User u = new User("Schichtleiter", "Markus", "Mair", "ag1", "salt", false, false, false, false);
//		storageDao.addUser(u);
		
		// Test User zu Löschen
//		storageDao.delUser(3);
		
		// Test für User
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

		// Test für Cams
//		for (Cam cam : cams) {
//			System.out.println(cam.getId());
//			System.out.println(cam.getName());
//			System.out.println(cam.getUrl());
//		}

		super.doGet(req, resp);
	}
}