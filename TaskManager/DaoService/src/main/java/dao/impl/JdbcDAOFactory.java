package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.TaskDAO;
import dao.UserDAO;


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


	@Override
	public UserDAO getUserDAO() {
		return new UserJdbcDAO();
	}


	@Override
	public TaskDAO getTaskDAO() {
		return new TaskJdbcDAO();
	}
	
	
}
