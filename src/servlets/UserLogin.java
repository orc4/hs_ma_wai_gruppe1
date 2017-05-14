package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data_model.User;
import exception.UserLoginIncorrect;
import storage.Storage;
import storage.StorageFactory;

public class UserLogin extends HttpServlet {

	private static final long serialVersionUID = -3206094083375113178L;

	final Storage storageDao = StorageFactory.getInstance().getStorage();
	public final static String USER_ATTRIBUTE = "userid";

	private void progressRequest(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);

		if (session.isNew()) {
			session.setMaxInactiveInterval(3600000);
		}

		String username = null;
		String password = null;

		if (request.getParameter("username") != null) {
			username = request.getParameter("username");
		}
		if (request.getParameter("password") != null) {
			password = request.getParameter("password");
		}

		if (username == null | password == null) {
			throw new UserLoginIncorrect(username);

		}
		User user = null;
		// User aus Datenbank Holen
		user = storageDao.getUserByName(username);
		if (user == null) {
			throw new UserLoginIncorrect(username);
		}

		if (password.equals(user.getPassword())) {
			session.setAttribute(USER_ATTRIBUTE, user.getId());
		}

		// TODO: Irgendwie zurück auf user_cam_view - wie der auch immer ist!
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		progressRequest(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		progressRequest(request, response);
	}

}
