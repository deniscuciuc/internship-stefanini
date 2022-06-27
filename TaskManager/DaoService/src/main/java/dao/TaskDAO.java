package dao;


import domain.TaskEntity;
import domain.UserEntity;

public interface TaskDAO {
	void addTask(UserEntity user, TaskEntity task);
	void getAllTasks(UserEntity user);
}
