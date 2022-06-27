package service.receivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import dao.UserJdbcDAO;
import dao.enums.AvaibleDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;


public class User {
	public void createUser() {
		UserEntity user = getUserDataFromUser();
		if (user == null) {
			showUserAlreadyExistError();
		} else {
			saveUserList(user);
			saveUserMySQL(user);
		}
	}
	
	public void showAllUsers() {
		List<UserEntity> users = UserJdbcDAO.getUsers();
		
		if (users.isEmpty()) {
			readUserDataFromMySQL();
		}
		
		for (UserEntity user : users) {
			System.out.println("Id = " + user.getId() + " | Username = " + user.getUserName() +  " | Full name = " + user.getFullName() + 
							   " | Number of tasks = " + user.getNumberOfTasks());
		}
	}
	
	public static void readUserDataFromMySQL() {
		DAOFactory.getDAOFactory(AvaibleDAOFactories.JDBC).getUserDAO().getAllUsers();
	}

	private UserEntity getUserDataFromUser() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Username: ");
		String userName = scanner.nextLine();
		
		if (userNameAlreadyExists(userName) ) {
			scanner.close();
			return null;
		}
		
		System.out.print("First name: ");
		String firstName = scanner.nextLine();
		
		System.out.print("Last name: ");
		String lastName = scanner.nextLine();
		
		scanner.close();
		
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		
		int id = getLastUserId() + 1;
		
		return new UserEntity(id, firstName, lastName, userName, tasks);
	}
	
	private boolean userNameAlreadyExists(String userName) {
		List<UserEntity> users = UserJdbcDAO.getUsers();
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
	
	private int getLastUserId() {
		return UserJdbcDAO.getUsers().get(UserJdbcDAO.getUsers().size() - 1).getId();
	}
	
	private void showUserAlreadyExistError() {
		System.out.println("Username already exist! Try again");
	}
}
