package dao;


import java.util.List;

import domain.entities.TaskEntity;


/**
 * Through this interface we get access to low level operations,
 * which have their own classes that implements this class, for example TaskJdbcDAO and TaskHibernateDAO
 * @author dcuciuc
 *
 */
public interface TaskDAO {
	void createTask(TaskEntity task);
	List<TaskEntity> getAllUsersTasks(int userId);
}
