package dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.TaskDAO;
import domain.TaskEntity;

public class TaskJdbcDAO implements TaskDAO {
	
	private static List<TaskEntity> tasks;
	
	
	public TaskJdbcDAO() {
		
	}
	
	@SuppressWarnings("unused")
	private TaskJdbcDAO(List<TaskEntity> tasks) {
		TaskJdbcDAO.tasks = tasks;
	}
	

	private Connection getConnection() throws SQLException {
		Connection connection = JdbcDAOFactory.getInstance().getConnection();
		return connection;
	}

	public static List<TaskEntity> getTasks() {
		if (TaskJdbcDAO.tasks == null) {
			TaskJdbcDAO.tasks = new ArrayList<TaskEntity>();
		}
		return TaskJdbcDAO.tasks;
	}

	@Override
	public void createTask(TaskEntity task) {
		String query = "INSERT INTO tasks(UserId, Title, Describtion) VALUES (?, ?, ?)";
		try (Connection connection = getConnection(); PreparedStatement  stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, task.getUserId());
			stmt.setString(2,  task.getTitle());
			stmt.setString(3,  task.getDescribtion());
			stmt.executeUpdate();
			System.out.println("Task added to MYSQL database succsesfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<TaskEntity> getAllUsersTasks(int userId) {
		String query = "SELECT * FROM tasks WHERE UserId = " + userId;
		try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			List<TaskEntity> tasks = new ArrayList<TaskEntity>();
			while (rs.next()) {
				tasks.add(new TaskEntity(userId, rs.getInt("Id"), rs.getString("Title"), rs.getString("Describtion")));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}
}
