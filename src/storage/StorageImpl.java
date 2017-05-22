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
	protected JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	public ResultSet progressSql(String sqlStatement) {
		// Example !!!
		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlStatement);

			// while (resultSet.next())
			// jlog.info(resultSet.getInt("id") + " has value: " +
			// resultSet.getString("value"));

		} catch (NamingException | SQLException e) {
			// sollte Nie vorkommen!!
			e.printStackTrace();
		} finally {
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
		return resultSet;

	}
	
	// Interface 
	@Override
	public List<Cam> getCamList() {
		ArrayList<Cam> camList = new ArrayList<>();

		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT id, name, uri, interval FROM wai_cam");

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
			// sollte Nie vorkommen!
			e.printStackTrace();
		} finally {
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
		return (camList);
	}

	@Override
	public void addPic(Picture pic) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Cam> getCamForUser(long User) {
		// TODO Auto-generated method stub
		return null;
	}
	// ???
	@Override
	public Object addCam(Cam cam) throws SQLException, NamingException {
				connection = jndiFactory.getConnection("jdbc/wai_gr1");
				PreparedStatement pstmt = connection.prepareStatement("INSERT INTO wai_cam (name, url, interval) VALUES (?,?,?)");
				pstmt.setString(1, cam.getName());
				pstmt.setString(2, resultSet.getString("uri"));
				pstmt.setLong(3, cam.getInterval());
				return pstmt.executeUpdate();
	}
	
	@Override
	public void editCam(long id, Cam newCam) {
		// TODO Auto-generated method stub

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
	public void addUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delUser(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editUser(long id, User user) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public List<User> listUser() {
		ArrayList<User> userList = new ArrayList<>();

		try {
			connection = jndiFactory.getConnection("jdbc/wai_gr1");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT id, username, vorname, nachname, can_mod_cam, can_mod_user, can_delegate_cam, can_see_all, salt, password  FROM wai_user");

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
						resultSet.getBoolean("can_delegate_cam"),
						resultSet.getBoolean("can_see_all"));
				userList.add(u);
			}
		} catch (NamingException | SQLException e) {
			// sollte Nie vorkommen!
			e.printStackTrace();
		} finally {
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
		return (userList);
	}

	@Override
	public List<Picture> getPictureBetween(long camId, Date from, Date to, long limit) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Picture getPicture(long id) {
		// TODO Auto-generated method stub
		return null;
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

}