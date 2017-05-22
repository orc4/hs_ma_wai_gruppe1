package storage;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import data_model.Cam;
import data_model.Picture;
import data_model.User;
import exception.CamNotDeletedException;
import utils.JNDIFactory;

public class StorageImpl implements Storage {
	// Private Variablen
	protected JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	
	// ----INTERFACE----
	// ------USER-------
	@Override
	public List<User> getListUser() {
		ArrayList<User> userList = new ArrayList<>();

		try {
			String sqlStatement = "SELECT id, username, vorname, nachname, password, salt, can_mod_cam, can_mod_user, can_see_all_cam, can_delegate_cam FROM wai_user";
			progressSql(sqlStatement);
			
			while (resultSet.next()) {
				User u = new User(
						resultSet.getLong("id"), 
						resultSet.getString("username"),
						resultSet.getString("vorname"),
						resultSet.getString("nachname"),
						resultSet.getString("password"),
						resultSet.getString("salt"),
						resultSet.getBoolean("can_mod_cam"),
						resultSet.getBoolean("can_mod_user"),
						resultSet.getBoolean("can_see_all_cam"),
						resultSet.getBoolean("can_delegate_cam"));
				userList.add(u);
			}
		} catch (SQLException e) {						// Sollte nie vorkommen!!!
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);				// Schlieﬂen der Verbindungen!!!
		}
		return (userList);
	}
	
	@Override
	public void addUser(User user) {
		try {
			Connection connection = jndiFactory.getConnection("jdbc/wai_gr1");
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO wai_user (username, vorname, nachname, password, salt, can_mod_cam, can_mod_user, can_see_all_cam, can_delegate_cam) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getVorname());
			pstmt.setString(3, user.getNachname());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getSalt());
			pstmt.setBoolean(6, user.isCan_mod_cam());
			pstmt.setBoolean(7, user.isCan_mod_user());
			pstmt.setBoolean(8, user.isCan_see_all_cam());
			pstmt.setBoolean(9, user.isCan_delegate_cam());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
	}
	
	@Override
	public void delUser(long id) {
		try {
			Connection connection = jndiFactory.getConnection("jdbc/wai_gr1");
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM wai_user WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
	}

	@Override
	public void editUser(long id, User user) {	
		try {
			Connection connection = jndiFactory.getConnection("jdbc/wai_gr1");
			PreparedStatement pstmt = connection.prepareStatement("UPDATE wai_user SET username = ?, vorname = ?, nachname = ?, password = ?, salt = ?, can_mod_cam = ?, can_mod_user = ?, can_see_all_cam = ?, can_delegate_cam = ? WHERE id = ?");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getVorname());
			pstmt.setString(3, user.getNachname());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getSalt());
			pstmt.setBoolean(6, user.isCan_mod_cam());
			pstmt.setBoolean(7, user.isCan_mod_user());
			pstmt.setBoolean(8, user.isCan_see_all_cam());
			pstmt.setBoolean(9, user.isCan_delegate_cam());
			pstmt.setLong(10, user.getId());
			pstmt.executeUpdate();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getUserByName(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserCamAllow(long userId, long camId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unsetUserCamAllow(long userId, long camId) {
		// TODO Auto-generated method stub

	}
	
	
	// ------CAMERA-----
	@Override
	public List<Cam> getCamList() {
		ArrayList<Cam> camList = new ArrayList<>();

		try {
			String sqlStatement = "SELECT id, name, uri, interval FROM wai_cam";
			progressSql(sqlStatement);

			while (resultSet.next()) {
				URI uri = new URI(resultSet.getString("uri"));
				Cam c = new Cam(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						uri, 
						resultSet.getLong("interval"));
				camList.add(c);
			}
		} catch (NamingException | SQLException | URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
		return (camList);
	}

	@Override
	public List<Cam> getCamForUser(long User) {
		return null;
	}
	
	@Override
	public Object addCam(Cam cam) throws SQLException, NamingException {
				connection = jndiFactory.getConnection("jdbc/wai_gr1");
				PreparedStatement pstmt = connection.prepareStatement("INSERT INTO wai_cam (name, url, interval) VALUES (?,?,?)");
				pstmt.setString(1, cam.getName());
				pstmt.setString(2, resultSet.getString("uri"));
				pstmt.setLong(3, cam.getInterval());
				return (pstmt.executeUpdate());
	}
	
	@Override
	public void delCam(long id) {
		Connection connection = null;
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM wai_cam WHERE id = ?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new CamNotDeletedException(id);
		} finally {
			if (connection != null) {
				try {
					connection.close();
					connection = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void editCam(long id, Cam newCam) {
		try {
			Connection connection = jndiFactory.getConnection("jdbc/wai_gr1");
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO wai_cam (name, uri, interval) VALUES (?, ?, ?)");
			pstmt.setString(1, newCam.getName());
//			URI uri = new URI(resultSet.getString("uri"));
//			pstmt.setURL(2, newCam.setUri(uri)); // ????
			pstmt.setLong(3, newCam.getInterval());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
	}
	
	
	// -----PICTURE-----
	@Override
	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addPic(Picture pic) {
	}

	@Override
	public Picture getPicture(long id) {
		return null;
	}

	
	// ----SONSTIGES----
	// Herrstellung der Verbindung mit Connection, Statement, ResultSet ect.
	public ResultSet progressSql(String sqlStatement) throws Exception, SQLException {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlStatement);
		return (resultSet);
	}

	// Test
//	public PreparedStatement pstm(String statement2) throws SQLException, NamingException {
//		Connection connection = jndiFactory.getConnection("jdbc/wai_gr1");
//		PreparedStatement pstmt = connection.prepareStatement(statement2);
//		return (pstmt);
//	}
	
	// Schlieﬂen der Verbindung von Connection, Statement, ResultSet 
	// Beenden der Verbindungen mit der Datenbank
	private void closeVerbindungen(Connection connection) {
		if (connection != null)
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (statement != null)
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public List<Date> getMonthsWithPictures(long camId, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Date> getDaysWithPictures(long camId, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Date> getHoursWithPictures(long camId, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}
}