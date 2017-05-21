package test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data_model.User;
import storage.Storage;
import storage.StorageFactory;

public class test_v1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1679245794661032467L;
	final Storage storageDao = StorageFactory.getInstance().getStorage();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<User> users = storageDao.listUser();

		for (User user : users) {
			System.out.println(user.getId());
		}
		super.doGet(req, resp);
	}
}
