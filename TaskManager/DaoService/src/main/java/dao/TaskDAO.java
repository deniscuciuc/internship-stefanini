package dao;


import java.util.Set;

import domain.beans.TaskBean;


/**
 * Through this interface we get access to low level operations,
 * which have their own classes that implements this class, for example TaskJdbcDAO and TaskHibernateDAO
 * @author dcuciuc
 *
 */
public interface TaskDAO {
	void createTask(TaskBean task);
	Set<TaskBean> getAllUsersTasks(int userId);
}
