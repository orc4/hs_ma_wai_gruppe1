package servlets;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data_model.Cam;
import data_model.Picture;
import data_model.User;
import exception.MissingParameterException;
import exception.UserLoginIncorrect;
import exception.UserNotLoggedIn;
import exception.UserNotPermitted;
import storage.Storage;
import storage.StorageFactory;

public class Manager extends HttpServlet {
	// Allgemeiner Parameter
	private final int MAX_PICTURES = 200;

	private final String ACTION_HANDLE_USER_MOD = "handle_user_mod";
	private final String ACTION_HANDLE_USER_LIST = "handle_user_list";
	private final String ACTION_HANDLE_CAM_MOD = "handle_cam_mode";
	private final String ACTION_HANDLE_CAM_LIST = "handle_cam_list";
	private final String ACTION_HANDLE_USER_CAM_DELEGATE_MOD = "handle_user_cam_delegate_mod";
	private final String ACTION_HANDLE_USER_CAM_DELEGATE_LIST = "handle_user_cam_delegate_list";
	private final String ACTION_HANDLE_PASSWORD_CHANGE = "handle_password_change";
	private final String ACTION_HANDLE_VIEW_CAMS = "handle_view_cams";
	private final String ACTION_HANDLE_VIEW_CAMS_SEARCH = "handle_view_cams_search";
	private final String ACTION_HANDLE_VIEW_CAM_SINGLE = "handle_view_cam_single";

	// User Variablen
	private final String PARAMETER_USER_ID = "userId";
	private final String PARAMETER_USER_VORNAME = "userVorname";
	private final String PARAMETER_USER_NACHNAME = "userNachname";
	private final String PARAMETER_USER_USERNAME = "userUsername";
	private final String PARAMETER_USER_PASSWORD = "userPassword";
	private final String PARAMETER_USER_PASSWORD_NEW1 = "userPasswordNew1";
	private final String PARAMETER_USER_PASSWORD_NEW2 = "userPasswordNew2";
	private final String PARAMETER_USER_SALT = "userSalt";
	private final String PARAMETER_USER_CAN_MOD_CAM = "user_can_mode_cam";
	private final String PARAMETER_USER_CAN_MOD_USER = "user_can_mod_user";
	private final String PARAMETER_USER_CAN_SEE_ALL_CAM = "user_can_see_all_cam";
	private final String PARAMETER_USER_CAN_DELEGATE_CAM = "user_can_delegate_cam";
	private final String PARAMETER_CAM_ID = "cam_id";
	private final String PARAMETER_CAM_DATE_FROM = "cam_date_from";
	private final String PARAMETER_CAM_DATE_TO = "cam_date_to";
	private final String PARAMETER_CAM_YEAR = "cam_date_year";
	private final String PARAMETER_CAM_MONTH = "cam_date_month";
	private final String PARAMETER_CAM_DAY = "cam_date_day";
	private final String PARAMETER_CAM_HOUR = "cam_date_hour";

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
			System.out.println("action = " + action);
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
		case ACTION_HANDLE_PASSWORD_CHANGE:
			this.handle_password_change(request, response);
			break;
		case ACTION_HANDLE_VIEW_CAMS:
			this.handle_view_cams(request, response);
			break;
		case ACTION_HANDLE_VIEW_CAMS_SEARCH:
			this.handle_view_cams_search(request, response);
			break;
		case ACTION_HANDLE_VIEW_CAM_SINGLE:
			this.handle_view_cam_single(request, response);
			break;

		default:
			break;
		}

	}

	private void handle_view_cam_single(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}

		// Prüfen ob parameter gesetzt sind
		if (request.getParameter(PARAMETER_CAM_ID) == null) {
			throw new MissingParameterException();
		}

		// Pflicht Parameter
		long camId = Long.getLong(request.getParameter(PARAMETER_CAM_ID));

		// Liste von Storage holen
		List<Cam> camList = storageDao.getCamForUser(user.getId());

		// Prüfen ob recht auf Cam?
		boolean hasRights = false;
		if (user.isCan_see_all_cam()) {
			hasRights = true;
		} else {
			for (Cam cam : camList) {
				if (cam.getId() == camId) {
					hasRights = true;
				}
			}
		}

		if (!hasRights)
			throw new UserNotPermitted(user.getUsername());

		int year = -1;
		int month = -1;
		int day = -1;
		int hour = -1;

		// Versuchen Jahr zu holen
		if (request.getParameter(PARAMETER_CAM_YEAR) != null) {
			int tmp = Integer.parseInt(request.getParameter(PARAMETER_CAM_YEAR));
			if (tmp >= 1970) {
				year = tmp;
			}
		}

		// Versuchen Monat zu holen
		if (request.getParameter(PARAMETER_CAM_MONTH) != null) {
			int tmp = Integer.parseInt(request.getParameter(PARAMETER_CAM_MONTH));
			if (tmp >= 1 && tmp <= 12) {
				month = tmp;
			}
		}

		// Versuchen Tag zu holen
		if (request.getParameter(PARAMETER_CAM_DAY) != null) {
			int tmp = Integer.parseInt(request.getParameter(PARAMETER_CAM_DAY));
			if (tmp >= 0 && tmp <= 31) {
				day = tmp;
			}
		}

		// Versuchen Stunde zu holen
		if (request.getParameter(PARAMETER_CAM_HOUR) != null) {
			int tmp = Integer.parseInt(request.getParameter(PARAMETER_CAM_HOUR));
			if (tmp >= 0 && tmp <= 24) {
				day = tmp % 24; // Falls 24 - dann 0!
			}
		}

		// Nr. 1 - nur camId - dann Monate mit Bilder anzeigen
		if (month == -1 | year == -1) {
			List<Date> monthList = storageDao.getMonthsWithPictures(camId, new Date(2011, 1, 1),
					new Date(Calendar.getInstance().getTimeInMillis()));
			request.setAttribute("months", monthList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/cam_view_months.jsp");
			dispatcher.forward(request, response);
		} else if (day == -1) {
			// Nr. 2 - mit monat - Tage mit Bilder anzeigen
			List<Date> dayList = storageDao.getDaysWithPictures(camId, new Date(2011, 1, 1),
					new Date(Calendar.getInstance().getTimeInMillis()));
			request.setAttribute("days", dayList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/cam_view_days.jsp");
			dispatcher.forward(request, response);
		} else if (hour == -1) {
			// Nr 3. - Tag - Stunden von Tag
			List<Date> hourList = storageDao.getHoursWithPictures(camId, new Date(2011, 1, 1),
					new Date(Calendar.getInstance().getTimeInMillis()));
			request.setAttribute("days", hourList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/cam_view_hours.jsp");
			dispatcher.forward(request, response);

		} else {
			// Nr 4. - Stunde - Alle Bilder einer Stunde (max 60?!)
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH");
			String target = year + "." + month + "." + day + " " + hour;
			java.util.Date result = null;
			try {
				result = df.parse(target);
			} catch (ParseException e) {
				// Darf eigentlich NIEEE knallen!
				e.printStackTrace();
			}
			Date dateFrom = new Date(result.getTime());
			Date dateTo = new Date(result.getTime() + 3600000);

			List<Picture> picList = storageDao.getPictureBetween(camId, dateFrom, dateTo, MAX_PICTURES);
			request.setAttribute("pics", picList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/cam_view_pics.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void handle_view_cams_search(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// User Holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}

		// Prüfen ob parameter gesetzt sind
		if (request.getParameter(PARAMETER_CAM_ID) == null | request.getParameter(PARAMETER_CAM_DATE_FROM) == null
				| request.getParameter(PARAMETER_CAM_DATE_TO) == null) {
			throw new MissingParameterException();

		}

		// Liste von Storage holen
		List<Cam> camList = storageDao.getCamForUser(user.getId());

		long camId = Long.getLong(request.getParameter(PARAMETER_CAM_ID));
		Date dateFrom = Date.valueOf(request.getParameter(PARAMETER_CAM_DATE_FROM));
		Date dateTo = Date.valueOf(request.getParameter(PARAMETER_CAM_DATE_TO));

		// Prüfen ob recht auf Cam?
		boolean hasRights = false;
		if (user.isCan_see_all_cam()) {
			hasRights = true;
		} else {
			for (Cam cam : camList) {
				if (cam.getId() == camId) {
					hasRights = true;
				}
			}
		}

		if (!hasRights)
			throw new UserNotPermitted(user.getUsername());

		// Bilder Aus db holen
		List<Picture> picList = storageDao.getPictureBetween(camId, dateFrom, dateTo, MAX_PICTURES);

		// Liste als Parameter setzen
		request.setAttribute("pics", picList);

		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_cam_search.jsp");
		dispatcher.forward(request, response);

	}

	private void handle_view_cams(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// User Holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}

		// Liste von Storage holen
		List<Cam> camList = storageDao.getCamForUser(user.getId());

		// Liste als Parameter setzen
		request.setAttribute("cams", camList);
		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_cam_list.jsp");
		dispatcher.forward(request, response);

	}

	private void handle_password_change(HttpServletRequest request, HttpServletResponse response) {
		// User holen
		User user = this.getLoggedInUser(request);
		if (user == null) {
			throw new UserNotLoggedIn();
		}

		if (request.getParameter(PARAMETER_USER_PASSWORD) == null
				| request.getParameter(PARAMETER_USER_PASSWORD_NEW1) == null
				| request.getParameter(PARAMETER_USER_PASSWORD_NEW2) == null) {
			throw new MissingParameterException();
		}

		String passwordOld = request.getParameter(PARAMETER_USER_PASSWORD);
		String passwordNew1 = request.getParameter(PARAMETER_USER_PASSWORD_NEW1);
		String passwordNew2 = request.getParameter(PARAMETER_USER_PASSWORD_NEW2);
		if (passwordOld.equals(user.getPassword())) {
			throw new UserLoginIncorrect(user.getUsername());
		}
		if (passwordNew1 != passwordNew2) {
			throw new MissingParameterException();
			// FIXME: vill hier noch andere exception mit meldung werfen?
		}

		user.setPassword(passwordNew1);
		storageDao.editUser(user.getId(), user);
		// TODO: Wohin soll der request laufen?!

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
		// FIXME: wieder einkommentieren
		// // User Holen
		// User user = this.getLoggedInUser(request);
		// if (user == null) {
		// throw new UserNotLoggedIn();
		// }
		// // Berechtigungen Prüfen
		// if (!user.isCan_see_all_cam()) {
		// throw new UserNotPermitted(user.getUsername());
		// }

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
