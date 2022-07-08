package service.receivers.impl;

import java.util.*;

import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.beans.TaskBean;
import domain.beans.UserBean;
import domain.entities.TaskEntity;
import domain.entities.UserEntity;
import service.receivers.UserService;


public class UserServiceImpl implements UserService {
	
	@Override
	public void createUser() {
		UserEntity user = getUserDataFromConsole();
		saveUserHibernateMySQL(user);
	}

	@Override
	public void createUserWithTask() {
		UserEntity user = getUserDataFromConsole();
		TaskEntity task = getTaskDataFromConsole();

		task.setUser(user);
		user.getTasks().add(task);

		saveUserHibernateMySQL(user);
	}

	@Override
	public void showAllUsers() {
		List<UserEntity> users = getAllUsersFromMySQLHibernate();
		users.stream().forEach(userEntity -> System.out.println(userEntity.toString()));
	}

	@SuppressWarnings("resource")
	private UserEntity getUserDataFromConsole() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Username: ");
		String userName = scanner.nextLine();

		System.out.print("First name: ");
		String firstName = scanner.nextLine();

		System.out.print("Last name: ");
		String lastName = scanner.nextLine();


		Set<TaskEntity> tasks = new HashSet<TaskEntity>();

		return new UserEntity(firstName, lastName, userName, tasks);
	}

	private TaskEntity getTaskDataFromConsole() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Task title: ");
		String title = scanner.nextLine();

		System.out.print("Task description: ");
		String description = scanner.nextLine();

		return new TaskEntity(title, description);
	}

	private void saveUserHibernateMySQL(UserEntity user) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().createUser(user);
	}

	private List<UserEntity> getAllUsersFromMySQLHibernate() {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getAllUsers();
	}
}
