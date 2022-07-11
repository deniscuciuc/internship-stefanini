package service.receivers.impl;

import java.util.*;

import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;
import service.receivers.UserService;
import service.receivers.exceptions.InvalidUserException;

/**
 * This class represents all command implementations for user operations.
 * Calling by concrete commands methods executes all operations with databases and client UI
 * @author dcuciuc
 */
public class UserServiceImpl implements UserService {

	/**
	 * Method gets user details from console, generates user object, then save it in database
	 */
	@Override
	public void createUser() {
		UserEntity user = getUserDataFromConsole();
		saveUserHibernateMySQL(user);
	}

	/**
	 * Method gets user and tasks details from console, generate both objects, then save user in database
	 */
	@Override
	public void createUserWithTask() {
		UserEntity user = getUserDataFromConsole();

		if (user == null) return;

		TaskEntity task = getTaskDataFromConsole();

		task.setUser(user);
		user.getTasks().add(task);

		saveUserHibernateMySQL(user);
	}

	/**
	 * Method shows / prints all users' information by calling them from database
	 */
	@Override
	public void showAllUsers() {
		List<UserEntity> users = getAllUsersFromMySQLHibernate();
		displayUsersConsole(users);
	}

	private UserEntity getUserDataFromConsole() {
		try {
			Scanner scanner = new Scanner(System.in);

			System.out.print("Username: ");
			String userName = scanner.nextLine();

			checkIfUserNameAlreadyExists(userName);

			System.out.print("First name: ");
			String firstName = scanner.nextLine();

			System.out.print("Last name: ");
			String lastName = scanner.nextLine();


			Set<TaskEntity> tasks = new HashSet<TaskEntity>();

			return new UserEntity(firstName, lastName, userName, tasks);
		} catch (InvalidUserException e) {
			System.out.println(e);
		}
		return null;
	}

	private TaskEntity getTaskDataFromConsole() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Task title: ");
		String title = scanner.nextLine();

		System.out.print("Task description: ");
		String description = scanner.nextLine();

		return new TaskEntity(title, description);
	}

	private void checkIfUserNameAlreadyExists(String userName) throws InvalidUserException {
		UserEntity user = DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getUserByUserName(userName);
		if (user != null) {
			throw new InvalidUserException("Username already exists!");
		}
	}

	private void displayUsersConsole(List<UserEntity> users) {
		users.stream().forEach(userEntity -> System.out.println(userEntity.toString()));
	}

	private void saveUserHibernateMySQL(UserEntity user) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().createUser(user);
	}

	private List<UserEntity> getAllUsersFromMySQLHibernate() {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getAllUsers();
	}
}
