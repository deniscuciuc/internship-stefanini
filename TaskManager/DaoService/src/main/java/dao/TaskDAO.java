package dao;

import java.util.List;

import domain.TaskEntity;
import domain.UserEntity;

public interface TaskDAO {
	void addTask(UserEntity user, TaskEntity task);
	List<TaskEntity> getAllTasks(UserEntity user);
}
