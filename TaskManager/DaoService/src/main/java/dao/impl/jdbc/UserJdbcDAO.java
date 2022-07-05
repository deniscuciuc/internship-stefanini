package dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import dao.UserDAO;
import dao.enums.AvailableDAOFactories;
import domain.UserEntity;

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

	@Override
	public void createUser(UserEntity user) {
		String query = "INSERT INTO users(UserName, FirstName, LastName) VALUES (?, ?, ?)";
		try (Connection connection = getConnection(); PreparedStatement  stmt = connection.prepareStatement(query)) {
			stmt.setString(1, user.getUserName());
			stmt.setString(2,  user.getFirstName());
			stmt.setString(3,  user.getLastName());
			stmt.executeUpdate();
			System.out.println("User added to MYSQL database succsesfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public List<UserEntity> getAllUsers() {
		String query = "SELECT * FROM users";
		try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			List<UserEntity> users = new ArrayList<UserEntity>();
			while (rs.next()) {
				users.add(new UserEntity(rs.getInt("Id"), rs.getString("UserName"),
						rs.getString("FirstName"), rs.getString("LastName"),
						  DAOFactory.getDAOFactory(AvailableDAOFactories.JDBC).getTaskDAO().getAllUsersTasks(rs.getInt("Id"))));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
