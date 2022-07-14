package service.impl;


import java.util.Scanner;
import java.util.Set;


import domain.UserEntity;
import domain.TaskEntity;
import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.TaskService;
import service.exceptions.InvalidUserException;


public class TaskServiceImpl implements TaskService {

	private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);


	@Override
	public void addTask() {
		String userName = getUserNameFromConsole();
		UserEntity user = getUserByUserNameHibernateMySQL(userName);

		TaskEntity task = getTaskDataFromConsole();
		task.setUser(user);

		saveTaskHibernateMySQLByUserId(task);
	}


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
			logger.error("Exception while getting user name from console. " + e.getMessage());
		}
		return null;
	}
	private void displayTasksConsole(String userName, Set<TaskEntity> tasks) {
		System.out.println(userName + "'s number of tasks: " + tasks.size());
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
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getGenericDAO().create(task);
	}
}
