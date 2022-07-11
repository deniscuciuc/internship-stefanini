package service.receivers.impl;


import java.util.Scanner;
import java.util.Set;


import domain.UserEntity;
import domain.TaskEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import service.receivers.TaskService;
import service.receivers.exceptions.InvalidUserException;

/**
 * This class represents all command implementations for tasks operations.
 * Calling by concrete commands methods executes all operations with databases and client UI
 * @author dcuciuc
 */
public class TaskServiceImpl implements TaskService {
	
	private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);

	/**
	 * This method should generate task and save it in database.
	 * Method takes the username from the console, then gets the user from the database.
	 * Then asks for all task details from the console and sets user to the task
	 * After all method save task in database
	 */
	@Override
	public void addTask() {
		String userName = getUserNameFromConsole();
		UserEntity user = getUserByUserNameHibernateMySQL(userName);

		TaskEntity task = getTaskDataFromConsole();
		task.setUser(user);

		saveTaskHibernateMySQLByUserId(task);
	}

	/**
	 * Method prints all user's tasks in console.
	 * Once the method has requested a username from the console,
	 * it retrieves the user's tasks from the database by username and displays them from the set of
	 */
	@Override
	public void showAllUsersTasks() {
		String userName = getUserNameFromConsole();
		Set<TaskEntity> tasks = getUsersTasksByUserNameHibernateMySQL(userName);
		displayTasksConsole(userName, tasks);
 	}


	private TaskEntity getTaskDataFromConsole() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Task title: ");
		String title = scanner.nextLine();

		System.out.print("Task description: ");
		String description = scanner.nextLine();

		return new TaskEntity(title, description);
	}

	private String getUserNameFromConsole() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Username: ");
			String userName = scanner.nextLine();

			checkIfUserWithSuchUserNameExists(userName);

			return userName;
		} catch (InvalidUserException e) {
			System.out.println(e);
		}
		return null;
	}
	private void displayTasksConsole(String userName, Set<TaskEntity> tasks) {
		System.out.println("User: " + userName + " have " + tasks.size() + " task/tasks");
		tasks.stream().forEach(taskEntity -> System.out.println(taskEntity.toString()));
	}

	private void checkIfUserWithSuchUserNameExists(String userName) throws InvalidUserException {
		UserEntity user = DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getUserByUserName(userName);
		if (user == null) {
			throw new InvalidUserException("The user with this name does not exist!");
		}
	}

	private Set<TaskEntity> getUsersTasksByUserNameHibernateMySQL(String userName) {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getUserByUserName(userName).getTasks();
	}

	private UserEntity getUserByUserNameHibernateMySQL(String userName) {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getUserByUserName(userName);
	}

	private void saveTaskHibernateMySQLByUserId(TaskEntity task) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getTaskDAO().createTask(task);
	}
}
