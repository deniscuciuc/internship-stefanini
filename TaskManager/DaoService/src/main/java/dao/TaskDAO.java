package dao;


import domain.TaskEntity;

import java.util.List;


/**
 * Through this interface we get access to low level operations,
 * which have their own classes that implements this class, for example TaskJdbcDAO and TaskHibernateDAO
 * @author dcuciuc
 */
public interface TaskDAO {

	/**
	 * Methods calls its implementation to create/save the task in the database
	 * @param task
	 */
	void createTask(TaskEntity task);

	/**
	 * The methods call its implementation to get all user's tasks as a list from database
	 * @param userId
	 * @return List<TaskEntity>
	 */
	List<TaskEntity> getAllUsersTasks(int userId);
}
