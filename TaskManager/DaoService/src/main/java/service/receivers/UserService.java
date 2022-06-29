package service.receivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import dao.enums.AvaibleDAOFactories;
import dao.impl.UserJdbcDAO;
import domain.TaskEntity;
import domain.UserEntity;


public class UserService {
	public void createUser() {
		UserEntity user = getUserDataFromUser();
		if (user == null) {
			showUserAlreadyExistErrorMessage();
		} else {
			saveUserList(user);
			saveUserMySQL(user);
		}
	}
	
	public void showAllUsers() {
	
		if (UserJdbcDAO.getUsers().isEmpty()) {
			readUserDataFromMySQL();
			
			if (UserJdbcDAO.getUsers().isEmpty()) {
				showNoUsersAlreadyCreatedErrorMessage();
				return;
			}
		}
		
		List<UserEntity> users = UserJdbcDAO.getUsers();
		for (UserEntity user : users) {
			System.out.println("Id = " + user.getId() + " | Username = " + user.getUserName() +  " | Full name = " + user.getFullName() + 
							   " | Number of tasks = " + user.getNumberOfTasks());
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
		
		if (userNameAlreadyExists(userName) ) {
			scanner.close();
			return null;
		}
		
		System.out.print("First name: ");
		String firstName = scanner.nextLine();
		
		System.out.print("Last name: ");
		String lastName = scanner.nextLine();
		
		
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		
		return new UserEntity(firstName, lastName, userName, tasks);
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
	
	private void showUserAlreadyExistErrorMessage() {
		System.out.println("Username already exist! Try again");
	}
	
	private void showNoUsersAlreadyCreatedErrorMessage() {
		System.out.println("No users already created!");
	}
}
