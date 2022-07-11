package dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.TaskDAO;
import domain.TaskEntity;

/**
 * This Jdbc DAO class is responsible for Jdbc operations with TaskEntity object using direct sql queries
 * @author dcuciuc
 */
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

	/**
	 * This method is a part of realization of the Singleton pattern, which allows to get a static list of tasks from anywhere
	 * @return List<TaskEntity>
	 */
	public static List<TaskEntity> getTasks() {
		if (TaskJdbcDAO.tasks == null) {
			TaskJdbcDAO.tasks = new ArrayList<TaskEntity>();
		}
		return TaskJdbcDAO.tasks;
	}

	/**
	 * Method saves TaskEntity object's values in database.
	 * Task must have user object already, because need to insert userId
	 * @param task
	 */
	@Override
	public void createTask(TaskEntity task) {
		String query = "INSERT INTO tasks(UserId, Title, Describtion) VALUES (?, ?, ?)";
		try (Connection connection = getConnection(); PreparedStatement  stmt = connection.prepareStatement(query)) {
			stmt.setInt(1, task.getUser().getId());
			stmt.setString(2,  task.getTitle());
			stmt.setString(3,  task.getDescription());
			stmt.executeUpdate();
			System.out.println("Task saved!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method gets all user's tasks from database using his id.
	 * If the method returns null, it means that user has no tasks
	 * @param userId
	 * @return List<TaskEntity>
	 */
	@Override
	public List<TaskEntity> getAllUsersTasks(int userId) {
		String query = "SELECT * FROM tasks WHERE userName = " + userId;
		try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			List<TaskEntity> tasks = new ArrayList<TaskEntity>();
			while (rs.next()) {
				tasks.add(new TaskEntity(rs.getInt("Id"), rs.getString("Title"), rs.getString("Describtion")));
			}
			return tasks;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
