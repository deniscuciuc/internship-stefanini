package service.receivers.impl;

import java.util.*;

import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.beans.TaskBean;
import domain.beans.UserBean;
import service.receivers.UserService;


public class UserServiceImpl implements UserService {
	
	@Override
	public void createUser() {
		UserBean user = getUserDataFromConsole();
		saveUserHibernateMySQL(user);
	}

	@Override
	public void createUserWithTask() {
		UserBean user = getUserDataFromConsole();
		TaskBean task = getTaskDataFromConsole();

		task.setUser(user);
		user.getTasks().add(task);

		saveUserHibernateMySQL(user);
	}

	@Override
	public void showAllUsers() {
		Set<UserBean> users = getAllUsersFromMySQLHibernate();
		users.stream().forEach(userBean -> userBean.toString());
	}

	@SuppressWarnings("resource")
	private UserBean getUserDataFromConsole() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Username: ");
		String userName = scanner.nextLine();

		System.out.print("First name: ");
		String firstName = scanner.nextLine();

		System.out.print("Last name: ");
		String lastName = scanner.nextLine();


		Set<TaskBean> tasks = new HashSet<TaskBean>();

		return new UserBean(firstName, lastName, userName, tasks);
	}

	private TaskBean getTaskDataFromConsole() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Task title: ");
		String title = scanner.nextLine();

		System.out.print("Task description: ");
		String description = scanner.nextLine();

		return new TaskBean(title, description);
	}

	private void saveUserHibernateMySQL(UserBean user) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().createUser(user);
	}

	private Set<UserBean> getAllUsersFromMySQLHibernate() {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getUserDAO().getAllUsers();
	}
}
