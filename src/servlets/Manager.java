package servlets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data_model.Cam;
import data_model.User;
import exception.MissingParameterException;
import exception.UserNotLoggedIn;
import exception.UserNotPermitted;
import storage.Storage;
import storage.StorageFactory;

public class Manager extends HttpServlet {
	private final String ACTION_HANDLE_USER_MOD = "handle_user_mod";
	private final String ACTION_HANDLE_USER_LIST = "handle_user_list";
	private final String ACTION_HANDLE_CAM_MOD = "handle_cam_mode";
	private final String ACTION_HANDLE_CAM_LIST = "handle_cam_list";
	private final String ACTION_HANDLE_USER_CAM_DELEGATE_MOD = "handle_user_cam_delegate_mod";
	private final String ACTION_HANDLE_USER_CAM_DELEGATE_LIST = "handle_user_cam_delegate_list";

	// User Variablen
	private final String PARAMETER_USER_ID = "userId";
	private final String PARAMETER_USER_VORNAME = "userVorname";
	private final String PARAMETER_USER_NACHNAME = "userNachname";
	private final String PARAMETER_USER_USERNAME = "userUsername";
	private final String PARAMETER_USER_PASSWORD = "userPassword";
	private final String PARAMETER_USER_SALT = "userSalt";
	private final String PARAMETER_USER_CAN_MOD_CAM = "user_can_mode_cam";
	private final String PARAMETER_USER_CAN_MOD_USER = "user_can_mod_user";
	private final String PARAMETER_USER_CAN_SEE_ALL_CAM = "user_can_see_all_cam";
	private final String PARAMETER_USER_CAN_DELEGATE_CAM = "user_can_delegate_cam";
	// Für Cam/User zuweisung
	private final String PARAMETER_USERID = "userid";
	private final String PARAMETER_CAMID = "camid";
	private final String PARAMETER_STATUS = "camStatus";

	private static final long serialVersionUID = -605260502302364704L;
	final Storage storageDao = StorageFactory.getInstance().getStorage();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		progressRequest(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		progressRequest(request, response);
	}

	private void progressRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = ACTION_HANDLE_CAM_LIST;
		if (request.getParameter("action") != null) {
			action = request.getParameter("action");
		}
		switch (action) {
		case ACTION_HANDLE_USER_MOD:
			this.handle_user_mod(request, response);
			break;
		case ACTION_HANDLE_USER_LIST:
			this.handle_user_list(request, response);
			break;
		case ACTION_HANDLE_CAM_MOD:
			this.handle_cam_mod(request, response);
			break;
		case ACTION_HANDLE_CAM_LIST:
			this.handle_cam_list(request, response);
			break;
		case ACTION_HANDLE_USER_CAM_DELEGATE_MOD:
			this.handle_user_cam_delegate_mod(request, response);
			break;
		case ACTION_HANDLE_USER_CAM_DELEGATE_LIST:
			this.handle_user_cam_delegate_list(request, response);
			break;

		default:
			// TODO: Log rein - ungültiger wert oder Error
			break;
		}

	}

	private void handle_user_cam_delegate_list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// User holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}
		// Berechitgungen Prüfen
		if (!user.isCan_delegate_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Ergebnis ist ein Hash-array - userid - camid - boolean (nur true -
		// false gibt es nicht!)
		List<User> userList = storageDao.listUser();
		HashMap<Long, HashMap<Long, Boolean>> userCamHashMap = new HashMap<>();
		List<Cam> camList = storageDao.getCamList();
		for (User listUser : userList) {
			List<Cam> allowedCamList = storageDao.getCamForUser(listUser.getId());
			HashMap<Long, Boolean> userMap = new HashMap<>();
			for (Cam cam : allowedCamList) {
				userMap.put(cam.getId(), Boolean.TRUE);
			}
			userCamHashMap.put(listUser.getId(), userMap);
		}

		// Liste als Parameter setzen
		request.setAttribute("camList", camList);
		request.setAttribute("userList", userList);
		request.setAttribute("userCamMap", userCamHashMap);
		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_cam_list.jsp");
		dispatcher.forward(request, response);

		// Dispatcher usw.
		// users setzen
		// cam setzen - und jeweils die user dazu?!

	}

	private void handle_user_cam_delegate_mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// User holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}
		// Berechitgungen Prüfen
		if (!user.isCan_delegate_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}
		// Parameter prüfen
		if (request.getParameter(PARAMETER_USERID) == null | request.getParameter(PARAMETER_CAMID) == null
				| request.getParameter(PARAMETER_STATUS) == null) {
			throw new MissingParameterException();
		}

		// Parameter aus Request holen
		long userId = Long.getLong(request.getParameter(PARAMETER_USERID), -1);
		long camId = Long.getLong(request.getParameter(PARAMETER_CAMID), -1);
		// camStatus = true wenn Erlauben - false wenn nicht!
		boolean camStatus = Boolean.getBoolean(request.getParameter(PARAMETER_STATUS));

		// in Dao Schreiben
		if (camStatus == true) {
			storageDao.setUserCamAllow(userId, camId);
		} else {
			storageDao.unsetUserCamAllow(userId, camId);
		}
		handle_user_cam_delegate_list(request, response);

	}

	private void handle_cam_mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// User holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}
		// Berechitgungen Prüfen
		if (!user.isCan_mod_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}
		// Parameter aus Request holen
		URI camUri;
		long camId = Long.getLong(request.getParameter("camId"), -1);
		String camName = request.getParameter("camName");
		try {
			camUri = new URI(request.getParameter("camUri"));
		} catch (URISyntaxException e) {
			camUri = null;
		}
		long camInterval = Long.getLong(request.getParameter("camInterval"), -1);

		// Überprüfen der Parameter
		if (camId == -1 | camInterval == -1 | camName == null | camUri == null) {
			throw new MissingParameterException();
		}

		storageDao.editCam(camId, new Cam(camId, camName, camUri, camInterval));

		// Return
		// RequestDispatcher dispatcher =
		// getServletContext().getRequestDispatcher("/jsp/cam_list.jsp");
		// dispatcher.forward(request, response);

		handle_cam_list(request, response);
	}

	private void handle_cam_list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// User Holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}
		// Berechtigungen Prüfen
		if (!user.isCan_see_all_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Liste von Storage holen
		List<Cam> camList = storageDao.getCamList();

		// Liste als Parameter setzen
		request.setAttribute("cams", camList);

		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/cam_list.jsp");
		dispatcher.forward(request, response);
	}

	private void handle_user_mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// User holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}
		// Berechtigungen Prüfen
		if (!user.isCan_mod_user()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Checken ob alle Params da sind!
		if (request.getParameter(PARAMETER_USER_ID) != null && request.getParameter(PARAMETER_USER_VORNAME) != null
				&& request.getParameter(PARAMETER_USER_NACHNAME) != null
				&& request.getParameter(PARAMETER_USER_USERNAME) != null
				&& request.getParameter(PARAMETER_USER_PASSWORD) != null
				&& request.getParameter(PARAMETER_USER_SALT) != null
				&& request.getParameter(PARAMETER_USER_CAN_MOD_CAM) != null
				&& request.getParameter(PARAMETER_USER_CAN_MOD_USER) != null
				&& request.getParameter(PARAMETER_USER_CAN_SEE_ALL_CAM) != null
				&& request.getParameter(PARAMETER_USER_CAN_DELEGATE_CAM) != null) {
			throw new MissingParameterException();
		}
		// FIXME: refactor Salt irgendwie separat behandeln
		// Parameter aus Request holen

		long userId;
		String vorname = null, nachname = null, username = null;
		String password = null, salt = null;
		boolean can_mod_cam = false, can_mod_user = false, can_see_all_cam = false, can_delegate_cam = false;

		userId = Long.getLong(request.getParameter(PARAMETER_USER_ID), -1);
		vorname = request.getParameter(PARAMETER_USER_VORNAME);
		nachname = request.getParameter(PARAMETER_USER_NACHNAME);
		username = request.getParameter(PARAMETER_USER_USERNAME);
		password = request.getParameter(PARAMETER_USER_PASSWORD);
		salt = request.getParameter(PARAMETER_USER_SALT);
		can_mod_cam = Boolean.getBoolean(request.getParameter(PARAMETER_USER_CAN_MOD_CAM));
		can_mod_user = Boolean.getBoolean(request.getParameter(PARAMETER_USER_CAN_MOD_USER));
		can_see_all_cam = Boolean.getBoolean(request.getParameter(PARAMETER_USER_CAN_SEE_ALL_CAM));
		can_delegate_cam = Boolean.getBoolean(request.getParameter(PARAMETER_USER_CAN_DELEGATE_CAM));

		User u = new User(userId, vorname, nachname, username, password, salt, can_mod_cam, can_mod_user,
				can_see_all_cam, can_delegate_cam);
		storageDao.editUser(user.getId(), u);

		// Return
		// RequestDispatcher dispatcher =
		// getServletContext().getRequestDispatcher("/jsp/user_list.jsp");
		// dispatcher.forward(request, response);
		handle_user_list(request, response);
	}

	private void handle_user_list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// User Holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}
		// Berechtigungen Prüfen
		if (!user.isCan_mod_user()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Liste von Storage holen
		List<User> userList = storageDao.listUser();

		// Liste als Parameter setzen
		request.setAttribute("users", userList);
		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_list.jsp");
		dispatcher.forward(request, response);
	}

	private User getLoggedInUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		long userId = (Long) session.getAttribute(UserLogin.USER_ATTRIBUTE);
		User user = storageDao.getUserById(userId);
		return user;
	}

}
