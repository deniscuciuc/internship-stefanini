package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.enums.AvaibleDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;

public class UserJdbcDAO implements UserDAO {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	private static List<UserEntity> users;
	
	public UserJdbcDAO() {
		
	}
	
	private UserJdbcDAO(List<UserEntity> users) {
		UserJdbcDAO.users = users;
	}

	public static List<UserEntity> getUsers() {
		if (UserJdbcDAO.users == null) {
			UserJdbcDAO.users = new ArrayList<>();
		}
		return UserJdbcDAO.users;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = JdbcDAOFactory.getInstance().getConnection();
		return conn;
	}

	@Override
	public void createUser(UserEntity user) {
		try {
			String query = "INSERT INTO users(Id, UserName, FirstName, LastName)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(2, user.getUserName());
			preparedStatement.setString(3,  user.getFirstName());
			preparedStatement.setString(4,  user.getLastName());
			preparedStatement.executeUpdate();
			System.out.println("User added to MYSQL database succsesfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public List<UserEntity> getAllUsers(List<UserEntity> users) {
		try {
			String query = "SELECT * FROM users";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				users.add(new UserEntity(resultSet.getInt("Id"), resultSet.getString("UserName"),
								   resultSet.getString("FirstName"), resultSet.getString("LastName"),
								   new ArrayList<TaskEntity>()));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null) preparedStatement.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
