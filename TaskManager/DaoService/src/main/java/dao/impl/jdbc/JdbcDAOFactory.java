package dao.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.TaskDAO;
import dao.UserDAO;

/**
 * This class represents constructor for impl of TaskDao and UserDAO.
 * Also, it makes connections with Database
 * @author dcuciuc
 */
public class JdbcDAOFactory extends DAOFactory {
	public static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/task_manager";
	public static final String USER = "root";
	public static final String PASSWORD = "password";
	
	private static JdbcDAOFactory connectionFactory = null;
	
	public JdbcDAOFactory() {
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Connecting to database
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(URL, USER, PASSWORD);
		return conn;
    }
	
	public static JdbcDAOFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new JdbcDAOFactory();
		}
		return connectionFactory;
	}


	/**
	 * Create new UserJdbcDAO to execute operations with users through jdbc
	 * @return UserDAO
	 */
	@Override
	public UserDAO getUserDAO() {
		return new UserJdbcDAO();
	}


	/**
	 * Create new TaskJdbcDAO to execute operations with tasks through jdbc
	 * @return TaskDAO
	 */
	@Override
	public TaskDAO getTaskDAO() {
		return new TaskJdbcDAO();
	}
	
	
}
