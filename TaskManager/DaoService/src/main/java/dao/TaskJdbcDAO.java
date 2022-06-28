package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.TaskEntity;
import domain.UserEntity;

public class TaskJdbcDAO implements TaskDAO {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	private static List<TaskEntity> tasks;
	
	
	public TaskJdbcDAO() {
		
	}
	
	@SuppressWarnings("unused")
	private TaskJdbcDAO(List<TaskEntity> tasks) {
		TaskJdbcDAO.tasks = tasks;
	}
	

	private Connection getConnection() throws SQLException {
		Connection conn = JdbcDAOFactory.getInstance().getConnection();
		return conn;
	}
	
	public static List<TaskEntity> getTasks() {
		if (TaskJdbcDAO.tasks == null) {
			TaskJdbcDAO.tasks = new ArrayList<TaskEntity>();
		}
		return TaskJdbcDAO.tasks;
	}

	@Override
	public void addTask(UserEntity user, TaskEntity task) {
		try {
			String query = "INSERT INTO tasks(UserId, Title, Describtion) VALUES (?, ?, ?)";
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2,  task.getTitle());
			preparedStatement.setString(3,  task.getDescribtion());
			preparedStatement.executeUpdate();
			System.out.println("Task added to MYSQL database succsesfully");
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
	public UserEntity getAllUsersTasks(UserEntity user) {
		try {
			String query = "SELECT * FROM tasks WHERE UserId = " + user.getId();
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				user.getTasks().add(new TaskEntity(resultSet.getInt("Id"), resultSet.getString("Title"),
								   resultSet.getString("Describtion")));
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
		return user;
	}
}
