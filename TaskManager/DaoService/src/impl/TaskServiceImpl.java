package service.receivers.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import dao.DAOFactory;
import dao.enums.AvaibleDAOFactories;
import dao.impl.UserJdbcDAO;
import domain.TaskEntity;
import domain.UserEntity;
import service.receivers.TaskService;

public class TaskServiceImpl implements TaskService {
	
	@Override
	public void addTask() {
		if (!areUsersCreated()) {
			return;
		}
		generateTask();
	}
	
	@Override
	public void showAllUsersTasks() {
		if (!areUsersCreated()) {
			return;
		}
		UserEntity user = findUserByUserName(getUserNameFromKeyboard());
		
		if (user.getNumberOfTasks() == 0) {
			showUserHaveNoTasksErrorMessage();
			return;
		}
		
		displayTasks(user);
 	}
	
	
	private void generateTask() {
		UserEntity user = findUserByUserName(getUserNameFromKeyboard());
		
		if (user == null) {
			showNoSuchUserErrorMessage();
		} else {
			TaskEntity task = getTaskDataFromUser();
			
			saveUsersTask(user, task);
			saveTaskMySQL(user, task);
		}
	}
	
	private void displayTasks(UserEntity user) {
		List<TaskEntity> tasks = user.getTasks();
		int taskCounter = 1;
		
		System.out.println("\n" + user.getUserName());
		for (TaskEntity task : tasks) {
			System.out.println("\n\nTask " + taskCounter);
			System.out.println("Title: " + task.getTitle());
			System.out.print("Describtion: " + task.getDescribtion());
			taskCounter++;
		}
	}
	
	public boolean areUsersCreated() {
		if (isUsersListEmpty()) {
			
			UserServiceImpl.readUserDataFromMySQL();
			
			if (isUsersListEmpty()) {
				
				showNoUsersAlreadyCreatedErrorMessage();
				return false;
				
			}
		}
		return true;
	}
	
	private String getUserNameFromKeyboard() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Username: ");
		String userName = scanner.nextLine();
		
		// scanner.nextLine() can fix exceptions such as scanner.nextInt source not found
		scanner.nextLine();
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
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Title: ");
		String title = scanner.nextLine();
		
		System.out.print("Describtion: ");
		String describtion = scanner.nextLine();
		
		return new TaskEntity(title, describtion);
	}
	
	private void saveUsersTask(UserEntity user, TaskEntity task) {
		user.getTasks().add(task);
	}
	
	private void saveTaskMySQL(UserEntity user, TaskEntity task) {
		DAOFactory.getDAOFactory(AvaibleDAOFactories.JDBC).getTaskDAO().addTask(user, task);
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
	
	private void showUserHaveNoTasksErrorMessage() {
		System.out.println("User have no tasks!");
	}
}
