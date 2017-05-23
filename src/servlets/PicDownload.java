package servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data_model.Picture;
import data_model.User;
import storage.Storage;
import storage.StorageFactory;

/**
 * Servlet implementation class PicDownload
 */
@WebServlet("/PicDownload")
public class PicDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final Storage storageDao = StorageFactory.getInstance().getStorage();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PicDownload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		progressRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		progressRequest(request, response);
	}

	private User getLoggedInUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = null;
		if (session.getAttribute(UserLogin.USER_ATTRIBUTE) != null) {
			long userId = (Long) session.getAttribute(UserLogin.USER_ATTRIBUTE);
			user = storageDao.getUserById(userId);
		}
		return user;

	}

	private void progressRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long picId = 0;
		Picture pic = storageDao.getPicture(picId);
		long camId = pic.getCam();

		// User holen
		User user = getLoggedInUser(request, response);

		// checken ob User Rechte hat

		response.setContentType("image/jpeg");

		File f = new File("/tmp/test.jpg");
		BufferedImage bi = ImageIO.read(f);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();

	}

}
