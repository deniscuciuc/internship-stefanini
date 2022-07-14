package dao;


import domain.TaskEntity;

import java.util.List;


/**
 * Through this interface we get access to low level operations
 * @author dcuciuc
 */
public interface TaskDAO {

	/**
	 * The methods call its implementation to get all user's tasks as a list from database
	 * @param userId
	 * @return List<TaskEntity>
	 */
	List<TaskEntity> getAllUsersTasks(int userId);
}
