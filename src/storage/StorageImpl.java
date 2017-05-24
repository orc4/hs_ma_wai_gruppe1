package storage;

import java.net.URL;
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
import exception.NotFoundException;
import utils.JNDIFactory;

public class StorageImpl implements Storage {
	// Private Variablen
	protected JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pstmt = null;
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
			closeVerbindungen(connection);				// Schlie�en der Verbindungen!!!
		}
		return (userList);
	}
	
	@Override
	public void addUser(User user) {
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("INSERT INTO wai_user (username, vorname, nachname, password, salt, can_mod_cam, can_mod_user, can_see_all_cam, can_delegate_cam) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("DELETE FROM wai_user WHERE id=?");
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
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("UPDATE wai_user SET username = ?, vorname = ?, nachname = ?, password = ?, salt = ?, can_mod_cam = ?, can_mod_user = ?, can_see_all_cam = ?, can_delegate_cam = ? WHERE id = ?");
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
		}  finally {
			closeVerbindungen(connection);
		}
	}

	@Override
	public User getUserByName(String username) {
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT * FROM wai_user WHERE username=?;";		
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setString(1, username);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
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
					return(u);
			} else {
				throw new NotFoundException(username);
			}
		} catch (Exception e) {
			throw new NotFoundException(username);
		} finally {
			closeVerbindungen(connection);
		}
	}

	@Override
	public User getUserById(Long id) {
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT * FROM wai_user WHERE id=?;";		
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, id);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
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
					return(u);
			} else {
				throw new NotFoundException(id);
			}
		} catch (Exception e) {
			throw new NotFoundException(id);
		} finally {
			closeVerbindungen(connection);
		}
	}

	@Override
	public void setUserCamAllow(long userId, long camId) {
		try{
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			String sqlStatement = "SELECT u.id AS uid, c.id AS cid, c.name AS cname, "
								+ "c.url AS curl, cu.cam_id AS caid, cu.user_id AS cuid "
								+ "FROM wai_user u, wai_cam c, wai_cam_user cu "
								+ "WHERE u.id=cu.user_id AND c.id=cu.cam_id AND u.id=? AND c.id=?;";	
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, userId);
			pstmt.setLong(2, camId);
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
// ????
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
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
			String sqlStatement = "SELECT id, name, url FROM wai_cam";
			progressSql(sqlStatement);

			while (resultSet.next()) {				
				URL url = new URL(resultSet.getString("url"));
				Cam c = new Cam(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						url);
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
	public List<Cam> getCamForUser(long userId) {
		ArrayList<Cam> camListUser = new ArrayList<>();

		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT u.id AS uid, c.id AS cid, c.name AS cname, "
								+ "c.url AS curl, cu.cam_id AS caid, cu.user_id AS cuid "
								+ "FROM wai_user u, wai_cam c, wai_cam_user cu "
								+ "WHERE u.id=cu.user_id AND c.id=cu.cam_id AND u.id=?;";		
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, userId);
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				Cam c = new Cam(
						resultSet.getLong("cid"),
						resultSet.getString("cname"),
						new URL(resultSet.getString("curl")));
				camListUser.add(c);
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
		return (camListUser);
	}
	
	@Override
	public void addCam(Cam cam) {
		try{
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("INSERT INTO wai_cam (name, url) VALUES (?,?)");
			pstmt.setString(1, cam.getName());
			pstmt.setString(2, cam.getUrl().toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
	}
	
	@Override
	public void delCam(long id) {
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("DELETE FROM wai_cam WHERE id = ?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
	}
	
	@Override
	public void editCam(long id, Cam newCam) {
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("UPDATE wai_cam SET name = ?, url = ? WHERE id = ?");
			pstmt.setString(1, newCam.getName()); 				//Setzt den 1. String
			pstmt.setString(2, newCam.getUrl().toString());		//Setzt den 2. String
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
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("INSERT INTO wai_picture (date, path) VALUES (?, ?)");
			pstmt.setDate(1, pic.getDate());
			pstmt.setString(2, pic.getPath());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(connection);
		}
	}

	@Override
	public Picture getPicture(long id) {
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT * FROM wai_picture WHERE id=?;";		
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, id);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
				Picture p = new Picture(
					resultSet.getLong("id"), 
					resultSet.getLong("camId"), 
					resultSet.getDate("date"), 
					resultSet.getString("path"));
					return(p);
			} else {
				throw new NotFoundException(id);
			}
		} catch (Exception e) {
			throw new NotFoundException(id);
		} finally {
			closeVerbindungen(connection);
		}
	}

	@Override
	public Picture getLatestPicture(long camId) {
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT * FROM wai_picture WHERE cam_id=? "
								+ "ORDER BY id DESC LIMIT 1;";		
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, camId);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
				Picture p = new Picture(
					resultSet.getLong("id"), 
					resultSet.getLong("camId"), 
					resultSet.getDate("date"), 
					resultSet.getString("path"));
					return(p);
			} else {
				throw new NotFoundException("ID: " + camId + " wurde nicht gefunden!");
			}
		} catch (Exception e) {
			throw new NotFoundException(camId);
		} finally {
			closeVerbindungen(connection);
		}
	}
	
	// ----SONSTIGES----
	// Herrstellung der Verbindung mit Connection, Statement, ResultSet ect.
	public ResultSet progressSql(String sqlStatement) throws Exception, SQLException {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlStatement);
		return (resultSet);
	}
	
	// Schlie�en der Verbindung von Connection, Statement, ResultSet 
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
		if (pstmt != null)
			try {
				pstmt.close();
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
	public List<Date> getDaysWithPictures(long camId, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getHoursWithPictures(long camId, Date date) {
		// TODO Auto-generated method stub
		return null;
	}
}