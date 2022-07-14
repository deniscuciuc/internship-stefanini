package dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.TaskDAO;
import dao.impl.jdbc.util.JdbcFactory;
import domain.TaskEntity;


public class TaskJdbcDAO implements TaskDAO {

	private static TaskJdbcDAO taskJdbcDAO;

	public TaskJdbcDAO() {

	}

	private TaskJdbcDAO(TaskJdbcDAO taskJdbcDAO) {
		TaskJdbcDAO.taskJdbcDAO = taskJdbcDAO;
	}


	public static TaskJdbcDAO getInstance() {
		if (TaskJdbcDAO.taskJdbcDAO == null) {
			TaskJdbcDAO.taskJdbcDAO = new TaskJdbcDAO();
		}
		return TaskJdbcDAO.taskJdbcDAO;
	}

	private Connection getConnection() throws SQLException {
		Connection connection = JdbcFactory.getInstance().getConnection();
		return connection;
	}


	@Override
	public List<TaskEntity> getAllUsersTasks(int userId) {
		String query = "SELECT * FROM tasks WHERE userName = " + userId;
		try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
			List<TaskEntity> tasks = new ArrayList<>();
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
