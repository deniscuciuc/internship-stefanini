package service.impl;

import java.util.*;

import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;
import email.SendMail;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.UserService;
import service.exceptions.InvalidUserException;


public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);


	@Override
	public void createUser() {
		UserEntity user = getUserDataFromConsole();
		saveUserHibernateMySQL(user);
		sendMail(user.getFirstName(), user.getLastName(), user.getUserName());
	}



	@Override
	public void createUserWithTask() {
		UserEntity user = getUserDataFromConsole();

		if (user == null) return;

		TaskEntity task = getTaskDataFromConsole();

		task.setUser(user);
		user.getTasks().add(task);

		saveUserHibernateMySQL(user);
	}


	@Override
	public void showAllUsers() {
		List<UserEntity> users = getAllUsersFromMySQLHibernate();
		displayUsersConsole(users);
	}

	@SendMail
	private void sendMail(String firstName, String lastName, String userName) {
		logger.info("Sending email...");
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


			Set<TaskEntity> tasks = new HashSet<>();

			return new UserEntity(firstName, lastName, userName, tasks);
		} catch (InvalidUserException e) {
			logger.error("Exception while getting user details from console. " + e.getMessage());
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
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getGenericDAO().create(user);
	}

	private List<UserEntity> getAllUsersFromMySQLHibernate() {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getAllUsers();
	}
}
