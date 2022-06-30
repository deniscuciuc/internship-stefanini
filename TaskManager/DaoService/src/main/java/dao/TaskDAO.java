package dao;


import java.util.List;

import domain.TaskEntity;

public interface TaskDAO {
	void createTask(TaskEntity task);
	List<TaskEntity> getAllUsersTasks(int userId);
}
