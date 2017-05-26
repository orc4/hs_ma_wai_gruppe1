package storage;

import java.net.URL;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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


	
	// ----INTERFACE----
	// ------USER-------
	@Override
	public List<User> getListUser() {
		ArrayList<User> userList = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			String sqlStatement = "SELECT id, username, vorname, nachname, password, salt, can_mod_cam, can_mod_user, can_see_all_cam, can_delegate_cam FROM wai_user";
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlStatement);
			
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
			closeVerbindungen(resultSet, statement, connection);				// Schlie�en der Verbindungen!!!
		}
		return (userList);
	}
	
	@Override
	public void addUser(User user) {
		Connection connection = null;
		PreparedStatement pstmt = null;
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
			closeVerbindungen(null, pstmt, connection);
		}
	}
	
	@Override
	public void delUser(long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("DELETE FROM wai_user WHERE id=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(null, pstmt, connection);
		}
	}

	@Override
	public void editUser(long id, User user) {	
		Connection connection = null;
		PreparedStatement pstmt = null;
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
			closeVerbindungen(null, pstmt, connection);
		}
	}

	@Override
	public User getUserByName(String username) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
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
			closeVerbindungen(resultSet, pstmt, connection);
		}
	}

	@Override
	public User getUserById(Long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
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
			closeVerbindungen(resultSet, pstmt, connection);
		}
	}

	@Override
	public void setUserCamAllow(long userId, long camId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try{	
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			pstmt = connection.prepareStatement("INSERT INTO wai_cam_user (cam_id, user_id) VALUES (?,?)");
//			String sqlStatement = "SELECT u.id AS uid, c.id AS cid, c.name AS cname, "
//								+ "c.url AS curl, cu.cam_id AS caid, cu.user_id AS cuid "
//								+ "FROM wai_user u, wai_cam c, wai_cam_user cu "
//								+ "WHERE u.id=cu.user_id AND c.id=cu.cam_id AND u.id=? AND c.id=?;";	
			pstmt.setLong(1, camId);
			pstmt.setLong(2, userId);
			pstmt.executeUpdate();
			
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(null, pstmt, connection);
		}
	}

	@Override
	public void unsetUserCamAllow(long userId, long camId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("DELETE FROM wai_cam_user WHERE cam_id = ? and user_id = ?");
			pstmt.setLong(1, camId);
			pstmt.setLong(2, userId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(null, pstmt, connection);
		}
	}
	
	
	// ------CAMERA-----
	@Override
	public List<Cam> getCamList() {
		ArrayList<Cam> camList = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			String sqlStatement = "SELECT id, name, url FROM wai_cam";
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlStatement);


			while (resultSet.next()) {				
				URL url = new URL(resultSet.getString("url"));
				Cam c = new Cam(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						url);
				camList.add(c);
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(resultSet, statement, connection);
		}
		return (camList);
	}

	@Override
	public List<Cam> getCamForUser(long userId) {
		ArrayList<Cam> camListUser = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

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
			closeVerbindungen(resultSet, pstmt, connection);
		}
		return (camListUser);
	}
	
	@Override
	public void addCam(Cam cam) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try{
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("INSERT INTO wai_cam (name, url) VALUES (?,?)");
			pstmt.setString(1, cam.getName());
			pstmt.setString(2, cam.getUrl().toString());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(null, pstmt, connection);
		}
	}
	
	@Override
	public void delCam(long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("DELETE FROM wai_cam WHERE id = ?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(null, pstmt, connection);
		}
	}
	
	@Override
	public void editCam(long id, Cam newCam) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("UPDATE wai_cam SET name = ?, url = ? WHERE id = ?");
			pstmt.setString(1, newCam.getName()); 				//Setzt den 1. String
			pstmt.setString(2, newCam.getUrl().toString());		//Setzt den 2. String
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(null, pstmt, connection);
		}
	}
	
	
	// -----PICTURE-----
	@Override
	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<Picture> pics = new ArrayList<>();
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT * FROM wai_picture WHERE cam_id=? and date >= ? and date <= ?;";		

			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, camId);
			pstmt.setTimestamp(2, new Timestamp(from.getTime()));
			pstmt.setTimestamp(3, new Timestamp(to.getTime()));
			
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				Picture p = new Picture(
					resultSet.getLong("id"), 
					resultSet.getLong("cam_id"), 
					new Date(resultSet.getTimestamp("date").getTime()), 
					resultSet.getString("path"));
					pics.add(p);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(resultSet, pstmt, connection);
		}
		return pics;
	}
	
	@Override
	public void addPic(Picture pic) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			pstmt = connection.prepareStatement("INSERT INTO wai_picture (date, path, cam_id) VALUES (?, ?, ?)");
			pstmt.setTimestamp(1, new Timestamp(pic.getDate().getTime()));
			pstmt.setString(2, pic.getPath());
			pstmt.setLong(3, pic.getCamId());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(null, pstmt, connection);
		}
	}

	@Override
	public Picture getPicture(long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT * FROM wai_picture WHERE id=?;";		
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, id);
			resultSet = pstmt.executeQuery();
			
			if (resultSet.next()) {
				Picture p = new Picture(
					resultSet.getLong("id"), 
					resultSet.getLong("cam_id"), 
					new Date(resultSet.getTimestamp("date").getTime()), 
					resultSet.getString("path"));
					return(p);
			} else {
				throw new NotFoundException(id);
			}
		} catch (Exception e) {
			throw new NotFoundException(id);
		} finally {
			closeVerbindungen(resultSet, pstmt, connection);
		}
	}

	@Override
	public Picture getLatestPicture(long camId) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
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
					resultSet.getLong("cam_id"), 
					new Date(resultSet.getTimestamp("date").getTime()),
					resultSet.getString("path"));
					return(p);
			} else {
				throw new NotFoundException("ID: " + camId + " wurde nicht gefunden!");
			}
		} catch (Exception e) {
			throw new NotFoundException(camId);
		} finally {
			closeVerbindungen(resultSet, pstmt, connection);
		}
	}
	
	// ----SONSTIGES----
	// Herrstellung der Verbindung mit Connection, Statement, ResultSet ect.
//	public ResultSet progressSql(String sqlStatement) throws Exception, SQLException {
//			Connection connection = jndiFactory.getConnection("jdbc/wai_gr1");
//			Statement statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery(sqlStatement);
//		return (resultSet);
//	}
//	
	// Schlie�en der Verbindung von Connection, Statement, ResultSet 
	// Beenden der Verbindungen mit der Datenbank
	private void closeVerbindungen(ResultSet resultSet, Statement stmt, Connection connection ) {
		if (connection != null)
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
//		if (pstmt != null)
//			try {
//				pstmt.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	

	@Override
	public List<Date> getDaysWithPictures(long camId, Date from, Date to) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<Date> daysWithPics = new ArrayList<>();
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT Date(date) as datetime "
					+ "FROM wai_picture "
					+ "WHERE cam_id=? and date >= ? and date <= ?"
					+ "GROUP BY datetime;";
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, camId);
			pstmt.setTimestamp(2, new Timestamp(from.getTime()));
			pstmt.setTimestamp(3, new Timestamp(to.getTime()));
			
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				daysWithPics.add(resultSet.getDate("datetime"));
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(resultSet, pstmt, connection);
		}
		return daysWithPics;
	}

	@Override
	public List<Integer> getHoursWithPictures(long camId, Date date) {
		Connection connection = null;
		PreparedStatement pstmt = null;	
		ResultSet resultSet = null;
		List<Integer> hoursWithPics = new ArrayList<>();
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");			
			String sqlStatement = "SELECT "
					+ "to_timestamp(floor((extract('epoch' from date) / 3600 )) * 3600) AT TIME ZONE 'UTC' as hours "
					+ "FROM wai_picture "
					+ "WHERE cam_id=? and Date(date) = ?"
					+ "GROUP BY hours";
			pstmt = connection.prepareStatement(sqlStatement);
			pstmt.setLong(1, camId);
			pstmt.setDate(2, date);
			
			resultSet = pstmt.executeQuery();
			
			while (resultSet.next()) {
				Timestamp p = resultSet.getTimestamp("hours");
				hoursWithPics.add(p.getHours());
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeVerbindungen(resultSet, pstmt, connection);
		}
		return hoursWithPics;
	}
}