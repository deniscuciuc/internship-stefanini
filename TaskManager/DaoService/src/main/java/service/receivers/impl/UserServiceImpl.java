package service.receivers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import dao.enums.AvaibleDAOFactories;
import dao.impl.UserJdbcDAO;
import domain.TaskEntity;
import domain.UserEntity;
import service.receivers.UserService;
import service.receivers.exceptions.InvalidUserException;


public class UserServiceImpl implements UserService {
	
	@Override
	public void createUser() {
		UserEntity user = getUserDataFromUser();
		saveUserList(user);
		saveUserMySQL(user);
	}
	
	@Override
	public void showAllUsers() {
		try {
			checkIfUsersWasAlreadyCreated();
			List<UserEntity> users = UserJdbcDAO.getUsers();
			for (UserEntity user : users) {
				System.out.println("Id = " + user.getId() + " | Username = " + user.getUserName() +  " | Full name = " + user.getFullName() + 
								   " | Number of tasks = " + user.getNumberOfTasks());
			}
		} catch (InvalidUserException e) {
			System.out.println(e);
		}
	}
	
	public static void readUserDataFromMySQL() {
		DAOFactory.getDAOFactory(AvaibleDAOFactories.JDBC).getUserDAO().getAllUsers();
	}

	@SuppressWarnings("resource")
	private UserEntity getUserDataFromUser() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Username: ");
		String userName = scanner.nextLine();
		
		try {
			validateUserName(userName);
			
			System.out.print("First name: ");
			String firstName = scanner.nextLine();
			
			System.out.print("Last name: ");
			String lastName = scanner.nextLine();
			
			
			List<TaskEntity> tasks = new ArrayList<TaskEntity>();
			
			return new UserEntity(firstName, lastName, userName, tasks);
		} catch (InvalidUserException e) {
			System.out.println(e);
		}
		return null;
	}
	
	private boolean userNameAlreadyExists(String userName) {
		List<UserEntity> users = UserJdbcDAO.getUsers();
		if (users.isEmpty()) {
			readUserDataFromMySQL();
		}
		for (UserEntity user : users) {
			if (user.getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	private void saveUserList(UserEntity user) {
		UserJdbcDAO.getUsers().add(user);
	}
	
	private void saveUserMySQL(UserEntity user) {
		DAOFactory.getDAOFactory(AvaibleDAOFactories.JDBC).getUserDAO().createUser(user);
	}
	
	private void validateUserName(String userName) throws InvalidUserException {
			if (userNameAlreadyExists(userName) ) {
				throw new InvalidUserException("Username already exists!");
			}
	}
	
	private void checkIfUsersWasAlreadyCreated() throws InvalidUserException {
		if (UserJdbcDAO.getUsers().isEmpty()) {
			readUserDataFromMySQL();
			if (UserJdbcDAO.getUsers().isEmpty()) {
				throw new InvalidUserException("No users already created!");
			}
		}
	}
}
