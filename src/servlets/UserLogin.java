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
	final String USER_ATTRIBUTE = "username";

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

			session.setAttribute(USER_ATTRIBUTE, username);

		}

		// List<User> collection = storageDao.listUser();
		// request.setAttribute("users", collection);
		// RequestDispatcher dispatcher =
		// getServletContext().getRequestDispatcher("/jsp/user_list.jsp");
		// dispatcher.forward(request, response);
		//
		// Long id = null;
		//
		// if (request.getParameter("id") != null) {
		// id = Long.valueOf(request.getParameter("id"));
		// }
		//
		// String author = request.getParameter("author");
		// String title = request.getParameter("title");

		// try {
		// bookDao.save(book);
		// response.sendRedirect(request.getContextPath() + "/list");
		// } catch (BookNotSavedException e) {
		// RequestDispatcher dispatcher =
		// getServletContext().getRequestDispatcher("/jsp/error.jsp");
		// dispatcher.forward(request, response);
		// }

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
