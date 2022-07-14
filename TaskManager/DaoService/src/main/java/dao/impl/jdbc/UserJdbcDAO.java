package dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dao.DAOFactory;
import dao.UserDAO;
import dao.enums.AvailableDAOFactories;
import dao.impl.jdbc.util.JdbcFactory;
import domain.TaskEntity;
import domain.UserEntity;


public class UserJdbcDAO implements UserDAO {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	private static UserJdbcDAO userJdbcDAO;
	
	public UserJdbcDAO() {
		
	}
	
	@SuppressWarnings("unused")
	private UserJdbcDAO(UserJdbcDAO users) {
		UserJdbcDAO.userJdbcDAO = userJdbcDAO;
	}


	public static UserJdbcDAO getInstance() {
		if (UserJdbcDAO.userJdbcDAO == null) {
			UserJdbcDAO.userJdbcDAO = new UserJdbcDAO();
		}
		return UserJdbcDAO.userJdbcDAO;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = JdbcFactory.getInstance().getConnection();
		return conn;
	}


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


	@Override
	public UserEntity getUserByUserName(String userName) {
		return null;
	}
}
