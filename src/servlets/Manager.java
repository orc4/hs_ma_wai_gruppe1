package servlets;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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

public class Manager extends HttpServlet {
	private static final long serialVersionUID = -605260502302364704L;

	private static Logger jlog = Logger.getLogger(Manager.class);

	// Allgemeiner Parameter
	private final int MAX_PICTURES = 200;
	private final String ACTION_HANDLE_USER_MOD = "user_mod";
	private final String ACTION_HANDLE_USER_ADD_VIEW = "user_add_view";
	private final String ACTION_HANDLE_USER_MOD_VIEW = "user_mod_view";
	private final String ACTION_HANDLE_USER_LIST = "user_list";
	private final String ACTION_HANDLE_USER_DEL = "user_del";
	private final String ACTION_HANDLE_CAM_ADD = "cam_add";
	private final String ACTION_HANDLE_CAM_MOD = "cam_mod";
	private final String ACTION_HANDLE_CAM_MOD_VIEW = "cam_mod_view";
	private final String ACTION_HANDLE_CAM_ADD_VIEW = "cam_add_view";
	private final String ACTION_HANDLE_CAM_DEL = "cam_del";
	private final String ACTION_HANDLE_CAM_LIST = "cam_list";
	private final String ACTION_HANDLE_USER_CAM_DELEGATE_MOD = "user_cam_delegate_mod";
	private final String ACTION_HANDLE_USER_CAM_DELEGATE_LIST = "user_cam_delegate_list";
	private final String ACTION_HANDLE_PASSWORD_CHANGE = "password_change";
	private final String ACTION_HANDLE_PASSWORD_CHANGE_VIEW = "password_change_view";
	private final String ACTION_HANDLE_VIEW_CAMS = "view_cams";
	private final String ACTION_HANDLE_VIEW_CAMS_SEARCH = "view_cams_search";
	private final String ACTION_HANDLE_VIEW_CAMS_SEARCH_VIEW = "view_cams_search_view";
	private final String ACTION_HANDLE_VIEW_CAM_SINGLE = "view_cam_single";
	private final String ACTION_LOGOUT = "logout";
	private final String ACTION_DASHBOARD = "handle_dashboard";
	private final String ACTION_LOGIN = "login";

	// User Variablen
	private final String PARAMETER_USER_ID = "userId";
	private final String PARAMETER_USER_VORNAME = "firstname";
	private final String PARAMETER_USER_NACHNAME = "lastname";
	private final String PARAMETER_USER_USERNAME = "username";
	private final String PARAMETER_USER_PASSWORD = "password";
	private final String PARAMETER_USER_PASSWORD_NEW1 = "passwordNew1";
	private final String PARAMETER_USER_PASSWORD_NEW2 = "passwordNew2";
	private final String PARAMETER_USER_SALT = "salt";
	private final String PARAMETER_USER_CAN_MOD_CAM = "user_can_mod_cam";
	private final String PARAMETER_USER_CAN_MOD_USER = "user_can_mod_user";
	private final String PARAMETER_USER_CAN_SEE_ALL_CAM = "user_can_see_all_cam";
	private final String PARAMETER_USER_CAN_DELEGATE_CAM = "user_can_delegate_cam";
	private final String PARAMETER_CAM_ID = "camId";
	private final String PARAMETER_CAM_DATE_FROM = "cam_date_from";
	private final String PARAMETER_CAM_DATE_TO = "cam_date_to";
	private final String PARAMETER_CAM_YEAR = "cam_date_year";
	private final String PARAMETER_CAM_MONTH = "cam_date_month";
	private final String PARAMETER_CAM_DAY = "cam_date_day";
	private final String PARAMETER_CAM_HOUR = "cam_date_hour";

	private final String PARAMETER_STATUS = "camStatus";
	final Storage storageDao = StorageFactory.getInstance().getStorage();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		progressRequest(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	private void handle_cam_add_view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted {
		// user holen
		User user = this.getLoggedInUser(request, response);

		// checken ob rechte zum cam mod
		if (!user.isCan_mod_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// umleiten
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/cam_add.jsp");
		dispatcher.forward(request, response);

	}

	private void handle_cam_del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		// User holen
		User user = this.getLoggedInUser(request, response);

		// Berechtigungen Prüfen
		if (!user.isCan_mod_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Checken ob alle Params da sind!
		if (request.getParameter(PARAMETER_CAM_ID) != null) {
			long camId = Long.parseLong(request.getParameter(PARAMETER_CAM_ID));
			storageDao.delCam(camId);

		} else {
			throw new MissingParameterException();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
		dispatcher.forward(request, response);

	}

	private void handle_cam_list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted {

		// User Holen
		User user = this.getLoggedInUser(request, response);

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

	private void handle_cam_mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		// User holen
		User user = this.getLoggedInUser(request, response);

		// Berechitgungen Prüfen
		if (!user.isCan_mod_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}
		// Parameter aus Request holen
		URL camUri;
		long camId;
		if (request.getParameter("camId") != null) {
			camId = Long.parseLong(request.getParameter("camId"));
		} else {
			camId = -1;
		}

		String camName = request.getParameter("camName");
		camUri = new URL(request.getParameter("camUri"));

		// Überprüfen der Parameter
		if (camName == null | camUri == null) {
			throw new MissingParameterException();
		}

		if (camId == -1) {// cam add
			storageDao.addCam(new Cam(-1, camName, camUri));
		} else {
			storageDao.editCam(camId, new Cam(camId, camName, camUri));
		}
		handle_cam_list(request, response);
	}

	private void handle_cam_mod_view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		// user holen
		User user = this.getLoggedInUser(request, response);

		// checken ob rechte zum cam mod
		if (!user.isCan_mod_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Parameter holen
		long camId;
		if (request.getParameter(PARAMETER_CAM_ID) != null) {
			camId = Long.parseLong(request.getParameter(PARAMETER_CAM_ID));
		} else {
			throw new MissingParameterException();
		}

		List<Cam> cams = storageDao.getCamList();
		Cam camToEdit = null;
		for (Cam cam : cams) {
			if (cam.getId() == camId)
				camToEdit = cam;
		}
		// cam als parameter setzen!
		request.setAttribute("cam", camToEdit);

		// umleiten
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/cam_edit.jsp");
		dispatcher.forward(request, response);
	}

	private void handle_dashboard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = this.getLoggedInUser(request, response);
		request.setAttribute("user", user);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	private void handle_logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (session.getAttribute(UserLogin.USER_ATTRIBUTE) != null) {
			session.removeAttribute(UserLogin.USER_ATTRIBUTE);
		}
		// RequestDispatcher dispatcher =
		// getServletContext().getRequestDispatcher("/login");
		// dispatcher.forward(request, response);
		response.sendRedirect(request.getContextPath() + "/login");
	}

	private void handle_password_change(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException, UserLoginIncorrect {
		// User holen
		User user = this.getLoggedInUser(request, response);

		if (request.getParameter(PARAMETER_USER_PASSWORD) == null
				| request.getParameter(PARAMETER_USER_PASSWORD_NEW1) == null
				| request.getParameter(PARAMETER_USER_PASSWORD_NEW2) == null) {
			throw new MissingParameterException();
		}

		String passwordOld = request.getParameter(PARAMETER_USER_PASSWORD);
		String passwordNew1 = request.getParameter(PARAMETER_USER_PASSWORD_NEW1);
		String passwordNew2 = request.getParameter(PARAMETER_USER_PASSWORD_NEW2);
		if (!passwordOld.equals(user.getPassword())) {
			throw new UserLoginIncorrect(user.getUsername());
		}
		if (!passwordNew1.equals(passwordNew2)) {
			throw new MissingParameterException();
			// FIXME: vill hier noch andere exception mit meldung werfen?
		}

		User u = new User(user.getId(), user.getUsername(), user.getVorname(), user.getNachname(), passwordNew1,
				user.getSalt(), user.isCan_mod_cam(), user.isCan_mod_user(), user.isCan_see_all_cam(),
				user.isCan_delegate_cam());
		storageDao.editUser(u.getId(), u);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/manager");
		dispatcher.forward(request, response);

	}

	private void handle_password_change_view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// user holen
		User user = this.getLoggedInUser(request, response);

		// darf jeder!

		// request umleiten
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_password_change.jsp");
		dispatcher.forward(request, response);
	}

	private void handle_redirect_login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_login.jsp");
		dispatcher.forward(request, response);

	}

	private void handle_user_add_view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted {
		// user holen
		User user = this.getLoggedInUser(request, response);

		// checken ob user mod
		if (!user.isCan_mod_user()) {
			throw new UserNotPermitted(user.getUsername());
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_add.jsp");
		dispatcher.forward(request, response);

	}

	private void handle_user_cam_delegate_list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted {

		// User holen
		User user = this.getLoggedInUser(request, response);

		// Berechitgungen Prüfen
		if (!user.isCan_delegate_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Ergebnis ist ein Hash-array - userid - camid - boolean (nur true -
		// false gibt es nicht!)
		List<User> userList = storageDao.getListUser();
		List<Cam> camList = storageDao.getCamList();

		// Alternative mapping Array:
		boolean[][] userCamArray = new boolean[userList.size()][camList.size()];
		for (int i = 0; i < userList.size(); i++) {
			User listUser = userList.get(i);
			List<Cam> allowedCamList = storageDao.getCamForUser(listUser.getId());
			for (int j = 0; j < camList.size(); j++) {
				Cam cam = camList.get(j);
				Boolean allowed = false;
				for (Cam cam2 : allowedCamList) {
					if (cam.getId() == cam2.getId()) {
						allowed = true;
					}
				}
				userCamArray[i][j] = allowed;
			}
		}

		// HashMap<Long, HashMap<Long, Boolean>> userCamHashMap = new
		// HashMap<>();
		//
		// for (User listUser : userList) {
		// List<Cam> allowedCamList =
		// storageDao.getCamForUser(listUser.getId());
		// HashMap<Long, Boolean> userMap = new HashMap<>();
		// for (Cam cam : allowedCamList) {
		// userMap.put(cam.getId(), Boolean.TRUE);
		// }
		// userCamHashMap.put(listUser.getId(), userMap);
		// }

		// Liste als Parameter setzen
		request.setAttribute("cams", camList);
		request.setAttribute("users", userList);
		request.setAttribute("userCamArray", userCamArray);

		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_cam_list.jsp");
		dispatcher.forward(request, response);

		// Dispatcher usw.
		// users setzen
		// cam setzen - und jeweils die user dazu?!

	}

	private void handle_user_cam_delegate_mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {

		// User holen
		User user = this.getLoggedInUser(request, response);

		// Berechitgungen Prüfen
		if (!user.isCan_delegate_cam()) {
			throw new UserNotPermitted(user.getUsername());
		}
		// Parameter prüfen
		if (request.getParameter(PARAMETER_USER_ID) == null | request.getParameter(PARAMETER_CAM_ID) == null) {
			throw new MissingParameterException();
		}

		// Parameter aus Request holen
		long userId = Long.parseLong(request.getParameter(PARAMETER_USER_ID));
		long camId = Long.parseLong(request.getParameter(PARAMETER_CAM_ID));
		// camStatus = true wenn Erlauben - false wenn nicht!
		boolean camStatus = Boolean.parseBoolean(request.getParameter(PARAMETER_STATUS));

		// in Dao Schreiben
		if (camStatus == true) {
			storageDao.unsetUserCamAllow(userId, camId);
		} else {
			storageDao.setUserCamAllow(userId, camId);
		}
		handle_user_cam_delegate_list(request, response);

	}

	private void handle_user_del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		// User holen
		User user = this.getLoggedInUser(request, response);

		// Berechtigungen Prüfen
		if (!user.isCan_mod_user()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Checken ob alle Params da sind!
		if (request.getParameter(PARAMETER_USER_ID) != null) {
			long userId = Long.parseLong(request.getParameter(PARAMETER_USER_ID));
			storageDao.delUser(userId);

		} else {
			throw new MissingParameterException();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
		dispatcher.forward(request, response);
	}

	private void handle_user_list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted {

		// User Holen
		User user = this.getLoggedInUser(request, response);
		// Berechtigungen Prüfen
		if (!user.isCan_mod_user()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Liste von Storage holen
		List<User> userList = storageDao.getListUser();

		// Liste als Parameter setzen
		request.setAttribute("users", userList);
		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_list.jsp");
		dispatcher.forward(request, response);
	}

	private void handle_user_mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		// User holen
		User user = this.getLoggedInUser(request, response);
		// Berechtigungen Prüfen
		if (!user.isCan_mod_user()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Checken ob alle Params da sind!
		if (request.getParameter(PARAMETER_USER_VORNAME) == null | request.getParameter(PARAMETER_USER_NACHNAME) == null
				| request.getParameter(PARAMETER_USER_USERNAME) == null
				| request.getParameter(PARAMETER_USER_PASSWORD) == null
				| request.getParameter(PARAMETER_USER_SALT) == null
				| request.getParameter(PARAMETER_USER_CAN_MOD_CAM) == null
				| request.getParameter(PARAMETER_USER_CAN_MOD_USER) == null
				| request.getParameter(PARAMETER_USER_CAN_SEE_ALL_CAM) == null
				| request.getParameter(PARAMETER_USER_CAN_DELEGATE_CAM) == null) {
			throw new MissingParameterException();
		}
		boolean addUser = false;
		if (request.getParameter(PARAMETER_USER_ID) == null) {
			addUser = true;
		}
		// FIXME: refactor Salt irgendwie separat behandeln
		// Parameter aus Request holen

		long userId;
		String vorname = null, nachname = null, username = null;
		String password = null, salt = null;
		boolean can_mod_cam = false, can_mod_user = false, can_see_all_cam = false, can_delegate_cam = false;

		vorname = request.getParameter(PARAMETER_USER_VORNAME);
		nachname = request.getParameter(PARAMETER_USER_NACHNAME);
		username = request.getParameter(PARAMETER_USER_USERNAME);
		password = request.getParameter(PARAMETER_USER_PASSWORD);
		salt = request.getParameter(PARAMETER_USER_SALT);
		can_mod_cam = Boolean.parseBoolean(request.getParameter(PARAMETER_USER_CAN_MOD_CAM));
		can_mod_user = Boolean.parseBoolean(request.getParameter(PARAMETER_USER_CAN_MOD_USER));
		can_see_all_cam = Boolean.parseBoolean(request.getParameter(PARAMETER_USER_CAN_SEE_ALL_CAM));
		can_delegate_cam = Boolean.parseBoolean(request.getParameter(PARAMETER_USER_CAN_DELEGATE_CAM));

		if (addUser) {
			userId = -1;
		} else {
			userId = Long.parseLong(request.getParameter(PARAMETER_USER_ID));
		}

		User u = new User(userId, username, vorname, nachname, password, salt, can_mod_cam, can_mod_user,
				can_see_all_cam, can_delegate_cam);

		if (addUser) {
			storageDao.addUser(u);
		} else {
			storageDao.editUser(userId, u);
		}

		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/");
		dispatcher.forward(request, response);
	}

	private void handle_user_mod_view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		// user holen
		User user = this.getLoggedInUser(request, response);

		// checken ob user mod
		if (!user.isCan_mod_user()) {
			throw new UserNotPermitted(user.getUsername());
		}

		// Parameter holen
		long userid;
		if (request.getParameter(PARAMETER_USER_ID) != null) {
			userid = Long.parseLong(request.getParameter(PARAMETER_USER_ID));
		} else {
			throw new MissingParameterException();
		}

		User userToEdit = storageDao.getUserById(userid);
		// user als parameter setzen!
		request.setAttribute("user", userToEdit);
		// umleisten
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/user_edit.jsp");
		dispatcher.forward(request, response);

	}

	private void handle_view_cam_single(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		User user = this.getLoggedInUser(request, response);

		// Prüfen ob parameter gesetzt sind
		if (request.getParameter(PARAMETER_CAM_ID) == null) {
			throw new MissingParameterException();
		}

		// Pflicht Parameter
		long camId = Long.parseLong(request.getParameter(PARAMETER_CAM_ID));

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

		request.setAttribute("camId", camId);
		int year = -1;
		int month = -1;
		int day = -1;
		int hour = -1;

		// Wenn Tag + Stunden gegeben - direkt Bilder zurück
		if (request.getParameter(PARAMETER_CAM_YEAR) != null && request.getParameter(PARAMETER_CAM_MONTH) != null
				&& request.getParameter(PARAMETER_CAM_DAY) != null
				&& request.getParameter(PARAMETER_CAM_HOUR) != null) {
			int tmp = Integer.parseInt(request.getParameter(PARAMETER_CAM_HOUR));
			if (tmp >= 0 && tmp <= 24) {
				hour = tmp % 24; // Falls 24 - dann 0!
			}
			year = Integer.parseInt(request.getParameter(PARAMETER_CAM_YEAR));
			month = Integer.parseInt(request.getParameter(PARAMETER_CAM_MONTH));
			day = Integer.parseInt(request.getParameter(PARAMETER_CAM_DAY));

		} // Wenn nur Tag gegeben - Stunden zurück
		else if (request.getParameter(PARAMETER_CAM_YEAR) != null && request.getParameter(PARAMETER_CAM_MONTH) != null
				&& request.getParameter(PARAMETER_CAM_DAY) != null) {
			year = Integer.parseInt(request.getParameter(PARAMETER_CAM_YEAR));
			month = Integer.parseInt(request.getParameter(PARAMETER_CAM_MONTH));
			day = Integer.parseInt(request.getParameter(PARAMETER_CAM_DAY));
			// Wenn nichts - nur Tage zurück
		} else {
			// Wird darunter unterschieden!
		}

		// Nr. 1 - nur camId - dann Monate mit Bilder anzeigen
		if (month == -1 | year == -1 | day == -1) {
			// Nr. 2 - Tage mit Bilder anzeigen
			List<Date> dayList = storageDao.getDaysWithPictures(camId,
					new Date(Calendar.getInstance().getTimeInMillis() - 1728000000),
					new Date(Calendar.getInstance().getTimeInMillis()));
			request.setAttribute("days", dayList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/normal_cam.jsp");
			dispatcher.forward(request, response);
		} else if (hour == -1) {
			// Nr 3. - Tag - Stunden von Tag
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
			String target = year + "." + month + "." + day;
			java.util.Date result = null;
			try {
				result = df.parse(target);
			} catch (ParseException e) {
				// Darf eigentlich NIEEE knallen!
				e.printStackTrace();
			}
			Date date = new Date(result.getTime());
			List<Integer> hourList = storageDao.getHoursWithPictures(camId, date);
			request.setAttribute("hours", hourList);
			request.setAttribute("currentDate", date);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/normal_cam.jsp");
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
			request.setAttribute("currentDate", result);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/normal_cam.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void handle_view_cams(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// User Holen
		User user = this.getLoggedInUser(request, response);

		// Liste von Storage holen
		List<Cam> camList;
		if (user.isCan_see_all_cam()) {
			camList = storageDao.getCamList();
		} else {
			camList = storageDao.getCamForUser(user.getId());
		}

		// Jeweils das letze Bild holen
		List<Picture> pics = new ArrayList<>();
		for (Cam cam : camList) {
			pics.add(storageDao.getLatestPicture(cam.getId()));
		}

		// Liste als Parameter setzen
		request.setAttribute("cams", camList);
		request.setAttribute("pics", pics);

		// Return
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/normal_cams.jsp");
		dispatcher.forward(request, response);

	}

	private void handle_view_cams_search(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, UserNotPermitted, MissingParameterException {
		// User Holen
		User user = this.getLoggedInUser(request, response);

		// Prüfen ob parameter gesetzt sind
		if (request.getParameter(PARAMETER_CAM_ID) == null | request.getParameter(PARAMETER_CAM_DATE_FROM) == null
				| request.getParameter(PARAMETER_CAM_DATE_TO) == null) {
			throw new MissingParameterException();

		}

		// Liste von Storage holen
		List<Cam> camList = storageDao.getCamForUser(user.getId());

		long camId = Long.parseLong(request.getParameter(PARAMETER_CAM_ID));

		Date dateFrom = Date.valueOf(request.getParameter(PARAMETER_CAM_DATE_FROM));
		Date dateTo = Date.valueOf(request.getParameter(PARAMETER_CAM_DATE_TO));
		int timeFrom = Integer.parseInt(request.getParameter("cam_time_from"));
		int timeTo = Integer.parseInt(request.getParameter("cam_time_to"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFrom);
		cal.add(Calendar.HOUR_OF_DAY, timeFrom);
		dateFrom = new java.sql.Date(cal.getTimeInMillis());
		cal.setTime(dateTo);
		cal.add(Calendar.HOUR_OF_DAY, timeTo);
		dateTo = new java.sql.Date(cal.getTimeInMillis());
		// cal.setTime(dateTo);
		// cal.add(Calendar.HOUR_OF_DAY, 23);
		// cal.add(Calendar.MINUTE, 59);
		// cal.add(Calendar.SECOND, 59);
		// dateTo = new java.sql.Date(cal.getTimeInMillis());

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

		request.setAttribute(PARAMETER_CAM_DATE_FROM, dateFrom);
		request.setAttribute(PARAMETER_CAM_DATE_TO, dateTo);
		request.setAttribute("cam_time_from", timeFrom);
		request.setAttribute("cam_time_to", timeTo);

		// Auf das gleiche zurück wie angefragt
		handle_view_cams_search_view(request, response);
	}

	private void handle_view_cams_search_view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// user holen
		User user = this.getLoggedInUser(request, response);

		// darf eigentlich jeder?!
		long camId = Long.parseLong(request.getParameter(PARAMETER_CAM_ID));

		request.setAttribute("camId", camId);

		// request umleiten
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/normal_cam_search.jsp");
		dispatcher.forward(request, response);
	}

	private void progressRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String action = ACTION_DASHBOARD;
			if (request.getParameter("action") != null) {
				action = request.getParameter("action");
			}

			String pathInfo = request.getPathInfo();
			String servletPath = request.getServletPath();
			if (pathInfo != null && pathInfo.startsWith("/")) { // remove
																// leading /
				pathInfo = pathInfo.substring(1);
				pathInfo = pathInfo.split("&")[0];
			}
			jlog.info("servletPath: " + servletPath);
			jlog.info("pathInfo: " + pathInfo);

			// Eingelloggter User holen
			User user = this.getLoggedInUser(request, response);
			if (user != null) {
				jlog.info("user " + user.getUsername() + "is logged in");
				if (pathInfo != null) {
					action = pathInfo;
				}
			} else {
				jlog.info("no user logged in");
				action = ACTION_LOGIN;
			}
			jlog.info("action: " + action);

			switch (action) {
			// WICHTIG! REIHENFOLGE BEACHTEN - der macht irgendwie contains!!!

			case ACTION_LOGIN:
				this.handle_redirect_login(request, response);
				break;
			case ACTION_DASHBOARD:
				this.handle_dashboard(request, response);
				break;
			case ACTION_HANDLE_USER_MOD:
				this.handle_user_mod(request, response);
				break;
			case ACTION_HANDLE_USER_MOD_VIEW:
				this.handle_user_mod_view(request, response);
				break;
			case ACTION_HANDLE_USER_ADD_VIEW:
				this.handle_user_add_view(request, response);
				break;
			case ACTION_HANDLE_CAM_MOD:
				this.handle_cam_mod(request, response);
				break;
			case ACTION_HANDLE_CAM_ADD_VIEW:
				this.handle_cam_add_view(request, response);
				break;
			case ACTION_HANDLE_CAM_ADD:
				this.handle_cam_mod(request, response);
				break;
			case ACTION_HANDLE_CAM_MOD_VIEW:
				this.handle_cam_mod_view(request, response);
				break;
			case ACTION_HANDLE_CAM_DEL:
				this.handle_cam_del(request, response);
				break;
			case ACTION_HANDLE_PASSWORD_CHANGE_VIEW:
				this.handle_password_change_view(request, response);
				break;
			case ACTION_HANDLE_VIEW_CAMS_SEARCH_VIEW:
				this.handle_view_cams_search_view(request, response);
				break;
			case ACTION_LOGOUT:
				this.handle_logout(request, response);
				break;
			case ACTION_HANDLE_USER_DEL:
				this.handle_user_del(request, response);
				break;
			case ACTION_HANDLE_USER_LIST:
				this.handle_user_list(request, response);
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
				this.handle_dashboard(request, response);
				break;
			}
		} catch (CamNotDeletedException | MissingParameterException | NotFoundException | UserLoginIncorrect
				| UserNotLoggedIn | UserNotPermitted e) {
			request.setAttribute("error", e);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
			dispatcher.forward(request, response);
		}

	}

}
