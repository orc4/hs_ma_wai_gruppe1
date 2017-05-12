package storage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import data_model.Cam;
import data_model.Picture;
import data_model.User;
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

	@Override
	public List<Cam> getCamList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCam(Cam cam) {
		// TODO Auto-generated method stub

	}

	@Override
	public void editCam(long id, Cam newCam) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delCam(long id) {
		// TODO Auto-generated method stub

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
			resultSet = statement.executeQuery("select id, vorname, nachname from wai_user");

			while (resultSet.next()) {
				User u = new User(resultSet.getLong("id"), resultSet.getString("vorname"),
						resultSet.getString("nachname"));
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
		return userList;
	}

	@Override
	public List<Picture> getPictureBetween(Cam cam, Date from, Date to, long limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Date> getMonthsWithPictures(Cam cam, Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture getPicture(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cam> getCamForUser(long User) {
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

}
