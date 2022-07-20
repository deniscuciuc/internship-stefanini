package service;

import domain.TaskEntity;

/**
 * This class represents an interface for tasks methods realization.
 * By calling this class we can access to all commands realization and execute all operations with tasks
 * @author dcuciuc
 */
public interface TaskService {

	/**
	 * Method takes the username from the console, then gets the user from the database.
	 * Then asks for all task details from the console and sets user to the task
	 * After all method save task in database
	 * @param task
	 */
	void addTask(TaskEntity task);

	/**
	 * Method shows / prints all user's tasks information
	 * @param userName
	 */
	void showAllUsersTasks(String userName);
}
