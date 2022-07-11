package service.receivers;

/**
 * This class represents an interface for tasks methods realization.
 * By calling this class we can access to all commands realization and execute all operations with tasks
 * @author dcuciuc
 */
public interface TaskService {

	/**
	 * Method creates new task and add it to the user
	 */
	void addTask();

	/**
	 * Method shows / prints all user's tasks information
	 */
	void showAllUsersTasks();
}
