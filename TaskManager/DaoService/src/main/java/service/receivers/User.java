package service.receivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.UserJdbcDAO;
import domain.TaskEntity;
import domain.UserEntity;


public class User {
	public void createUser() {
		UserEntity user = getUserDataFromUser();
		UserJdbcDAO.getUsers().add(user);
		UserJdbcDAO userSQLServer = new UserJdbcDAO();
		userSQLServer.createUser(user);
	}
	
	public void showAllUsers() {
		
	}
	
	private UserEntity getUserDataFromUser() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Username: ");
		String userName = scanner.nextLine();
		
		System.out.print("First name: ");
		String firstName = scanner.nextLine();
		
		System.out.print("Last name: ");
		String lastName = scanner.nextLine();
		
		scanner.close();
		
		List<TaskEntity> tasks = new ArrayList<TaskEntity>();
		
		return new UserEntity(firstName, lastName, userName, tasks);
	}
}
