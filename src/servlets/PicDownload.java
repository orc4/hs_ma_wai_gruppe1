package servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.naming.NoPermissionException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import data_model.Cam;
import data_model.Picture;
import data_model.User;
import exception.CamNotDeletedException;
import exception.MissingParameterException;
import exception.NotFoundException;
import exception.UserLoginIncorrect;
import exception.UserNotLoggedIn;
import exception.UserNotPermitted;
import storage.Storage;
import storage.StorageFactory;
import utils.JNDIFactory;

/**
 * Servlet implementation class PicDownload
 */
@WebServlet("/PicDownload")
public class PicDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// private String storageLocation = "/tmp/cams/";
	private static Logger jlog = Logger.getLogger(PicDownload.class);

	private static File getStoragePath() {
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		File storageLocation = null;
		try {
			storageLocation = new File(jndiFactory.getEnvironmentAsString("projectPath")
					+ jndiFactory.getEnvironmentAsString("picturePath"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storageLocation;
	}

	final Storage storageDao = StorageFactory.getInstance().getStorage();
	private final String PARAMETER_PICTURE_ID = "picId";
	private final String PARAMETER_PICTURE_THUMB = "thumb";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		progressRequest(request, response);
	}

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

		// Request per get picId + optional thumb
		try {
			long picId = 0;

			if (request.getParameter(PARAMETER_PICTURE_ID) != null) {
				picId = Long.parseLong(request.getParameter(PARAMETER_PICTURE_ID));
			}
			Picture pic = storageDao.getPicture(picId);
			if (pic == null) {
				jlog.warn("Bild mit Bild-ID: " + picId + " nicht gefunden");
			} else {
				long camId = pic.getCamId();

				// User holen
				User user = getLoggedInUser(request, response);

				// checken ob User Rechte hat
				boolean hasRights = false;
				if (user.isCan_see_all_cam()) {
					hasRights = true;
				} else {
					List<Cam> cams = storageDao.getCamForUser(user.getId());
					for (Cam cam : cams) {
						if (cam.getId() == camId) {
							hasRights = true;
						}
					}
				}
				if (!hasRights) {
					throw new NoPermissionException();
				}
				File picFile = new File(getStoragePath() + "/" + pic.getPath());
				if (request.getParameter(PARAMETER_PICTURE_THUMB) != null) {
					// Wenn thumbmail angefordert wird
					String base = picFile.getParent();
					String filename = picFile.getName();
					picFile = new File(base + "/thumb/" + filename);
				}

				// User hat rechte - jetzt kann die Antwort kommen!
				response.setContentType("image/jpeg");

				BufferedImage bi = ImageIO.read(picFile);
				OutputStream out = response.getOutputStream();
				ImageIO.write(bi, "jpg", out);
				out.close();

			}
		} catch (NoPermissionException | CamNotDeletedException | MissingParameterException | NotFoundException
				| UserLoginIncorrect | UserNotLoggedIn | UserNotPermitted e) {
			request.setAttribute("error", e);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
			dispatcher.forward(request, response);
		}
	}

}
