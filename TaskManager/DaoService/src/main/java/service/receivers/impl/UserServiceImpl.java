package service.receivers.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;
import service.receivers.UserService;
import service.receivers.exceptions.InvalidUserException;


public class UserServiceImpl implements UserService {
	
	@Override
	public void createUser() {
		UserEntity user = getUserDataFromUser();
		saveUserMySQL(user);
	}
	
	@Override
	public void showAllUsers() {
		try {
			checkIfUsersWasAlreadyCreated();
			List<UserEntity> users = getAllUsersFromMySQL();
			for (UserEntity user : users) {
				System.out.println("Id = " + user.getId() + " | Username = " + user.getUserName() +  " | Full name = " + user.getFullName() + 
								   " | Number of tasks = " + user.getNumberOfTasks());
			}
		} catch (InvalidUserException e) {
			System.out.println(e);
		}
	}
	
	public static List<UserEntity> getAllUsersFromMySQL() {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.JDBC).getUserDAO().getAllUsers();
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
	
	private void saveUserMySQL(UserEntity user) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.JDBC).getUserDAO().createUser(user);
	}
	
	private boolean userNameAlreadyExists(String userName) {
		List<UserEntity> users = getAllUsersFromMySQL();
		for (UserEntity user : users) {
			if (user.getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	private void validateUserName(String userName) throws InvalidUserException {
			if (userNameAlreadyExists(userName) ) {
				throw new InvalidUserException("Username already exists!");
			}
	}
	
	private void checkIfUsersWasAlreadyCreated() throws InvalidUserException {
		List<UserEntity> users = getAllUsersFromMySQL();
		if (users.isEmpty()) {
			throw new InvalidUserException("Users were not created yet!");
		}
	}
	
//	private void saveUserList(UserEntity user) {
//		UserJdbcDAO.getUsers().add(user);
//	}
}
