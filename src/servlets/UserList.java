package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data_model.User;
import storage.Storage;
import storage.StorageFactory;

public class UserList extends HttpServlet {

	private static final long serialVersionUID = -605260502302364704L;
	final Storage storageDao = StorageFactory.getInstance().getStorage();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<User> collection = storageDao.listUser();
		request.setAttribute("users", collection);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_list.jsp");
		dispatcher.forward(request, response);
	}
}
