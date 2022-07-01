package service.receivers.impl;


import java.util.List;
import java.util.Scanner;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DAOFactory;
import dao.enums.AvaibleDAOFactories;
import domain.TaskEntity;
import domain.UserEntity;
import service.receivers.TaskService;
import service.receivers.exceptions.InvalidUserException;

public class TaskServiceImpl implements TaskService {
	
	private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);  
	
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
		try {
			UserEntity user = findUserByUserName(getUserNameFromKeyboard());
			checkIfSuchUserExist(user);
			displayTasks(user);
		} catch (InvalidUserException e) {
			logger.error("Such user dont exist!", e);
		}
 	}
	
	
	private void generateTask() {
		UserEntity user = findUserByUserName(getUserNameFromKeyboard());
		try {
			checkIfSuchUserExist(user);
			TaskEntity task = getTaskDataFromUser();
			task.setId(user.getId());
			saveUsersTask(user, task);
			saveTaskMySQL(task);
		} catch(InvalidUserException e) {
			System.out.println(e);
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
		List<UserEntity> users = UserServiceImpl.getAllUsersFromMySQL();
		if (users.isEmpty()) {
			return false;
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
		List<UserEntity> users = UserServiceImpl.getAllUsersFromMySQL();
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
	
	private void saveTaskMySQL(TaskEntity task) {
		DAOFactory.getDAOFactory(AvaibleDAOFactories.JDBC).getTaskDAO().createTask(task);
	}
	
//	private boolean isUsersListEmpty() {
//		return UserJdbcDAO.getUsers().isEmpty();
//	}
	
	private void checkIfSuchUserExist(UserEntity user) throws InvalidUserException {
		if (user == null) {
			throw new InvalidUserException("No such user");
		}
	}
}
