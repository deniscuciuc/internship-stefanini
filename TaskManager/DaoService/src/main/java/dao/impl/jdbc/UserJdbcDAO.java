package dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.DAOFactory;
import dao.UserDAO;
import dao.enums.AvailableDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;

/**
 * This Jdbc DAO class is responsible for Jdbc operations with UserEntity object using direct sql queries
 * @author dcuciuc
 */
public class UserJdbcDAO implements UserDAO {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	private static List<UserEntity> users;
	
	public UserJdbcDAO() {
		
	}
	
	@SuppressWarnings("unused")
	private UserJdbcDAO(List<UserEntity> users) {
		UserJdbcDAO.users = users;
	}

	/**
	 * This method is a part of realization of the Singleton pattern, which allows to get a static list of users from anywhere
	 * @return List<UserEntity>
	 */
	public static List<UserEntity> getUsers() {
		if (UserJdbcDAO.users == null) {
			UserJdbcDAO.users = new ArrayList<UserEntity>();
		}
		return UserJdbcDAO.users;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = JdbcDAOFactory.getInstance().getConnection();
		return conn;
	}

	/**
	 * Method saves UserEntity object's values in database
	 * @param user
	 */
	@Override
	public void createUser(UserEntity user) {
		String query = "INSERT INTO users(UserName, FirstName, LastName) VALUES (?, ?, ?)";
		try (Connection connection = getConnection(); PreparedStatement  stmt = connection.prepareStatement(query)) {
			stmt.setString(1, user.getUserName());
			stmt.setString(2,  user.getFirstName());
			stmt.setString(3,  user.getLastName());
			stmt.executeUpdate();
			System.out.println("User saved!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method gets all users from database.
	 * If the method returns null, it means that there are no users in the database
	 * @return List<UserEntity>
	 */
	@Override
	public List<UserEntity> getAllUsers() {
		String query = "SELECT * FROM users";
		try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			List<UserEntity> users = new ArrayList<UserEntity>();

			while (rs.next()) {
				users.add(new UserEntity(rs.getInt("Id"), rs.getString("UserName"),
						rs.getString("FirstName"), rs.getString("LastName"),
						(Set<TaskEntity>) DAOFactory.getDAOFactory(AvailableDAOFactories.JDBC).getTaskDAO().getAllUsersTasks(rs.getInt("Id"))));
			}

			return users;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * If the method returns null, it means that there are no users with that username in the database
	 * @param userName
	 * @return UserEntity
	 */
	@Override
	public UserEntity getUserByUserName(String userName) {
		return null;
	}
}
