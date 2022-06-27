package service.receivers;

import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import dao.UserJdbcDAO;
import dao.enums.AvaibleDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;

public class Task {
	private Scanner scanner = new Scanner(System.in);
	
	public void addTask() {
		if (isUsersListEmpty()) {
			
			User.readUserDataFromMySQL();
			
			if (isUsersListEmpty()) {
				
				showNoUsersAlreadyCreatedErrorMessage();
				return;
				
			}
		}
		
		generateTask();
	}
	
	public void showAllUsersTasks() {
		
	}
	
	
	private void generateTask() {
		UserEntity user = findUserByUserName(getUserNameByUser());
		
		if (user == null) {
			showNoSuchUserErrorMessage();
		} else {
			TaskEntity task = getTaskDataFromUser();
			DAOFactory.getDAOFactory(AvaibleDAOFactories.JDBC).getTaskDAO().addTask(user, task);
		}
	}
	
	private String getUserNameByUser() {
		System.out.print("Username: ");
		String userName = scanner.nextLine();
		return userName;
	}
	
	private UserEntity findUserByUserName(String userName) {
		List<UserEntity> users = UserJdbcDAO.getUsers();
		for (UserEntity user : users) {
			if (user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
	
	private TaskEntity getTaskDataFromUser() {
		
		System.out.print("Title: ");
		String title = scanner.nextLine();
		
		System.out.print("Describtion: ");
		String describtion = scanner.nextLine();
		
		return new TaskEntity(title, describtion);
	}
	
	private boolean isUsersListEmpty() {
		return UserJdbcDAO.getUsers().isEmpty();
	}
	
	private void showNoUsersAlreadyCreatedErrorMessage() {
		System.out.println("No users already created! Select option 1.");
	}
	
	private void showNoSuchUserErrorMessage() {
		System.out.println("No such user! Try again");
	}
}
