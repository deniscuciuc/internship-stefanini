package service.receivers.impl;


import java.util.List;
import java.util.Scanner;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.DAOFactory;
import dao.enums.AvailableDAOFactories;
import domain.entities.TaskEntity;
import service.receivers.TaskService;

public class TaskServiceImpl implements TaskService {
	
	private static final Logger logger = LogManager.getLogger(TaskServiceImpl.class);  
	
	@Override
	public void addTask() {
		TaskEntity task = getTaskDataFromConsole();
		saveTaskHibernateMySQLByUserId(task);
	}
	
	@Override
	public void showAllUsersTasks() {
		String userName = getUserNameFromConsole();
		List<TaskEntity> tasks = getUsersTasksByUserNameHibernateMySQL(userName);
		tasks.stream().forEach(taskEntity -> System.out.println(taskEntity.toString()));
 	}


	private TaskEntity getTaskDataFromConsole() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Task title: ");
		String title = scanner.nextLine();

		System.out.print("Task description: ");
		String description = scanner.nextLine();

		return new TaskEntity(title, description);
	}

	private String getUserNameFromConsole() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Username: ");
		String userName = scanner.nextLine();
		return userName;
	}

	private List<TaskEntity> getUsersTasksByUserNameHibernateMySQL(String userName) {
		return DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getTaskDAO().getAllUsersTasks(userName);
	}

	private void saveTaskHibernateMySQLByUserId(TaskEntity task) {
		DAOFactory.getDAOFactory(AvailableDAOFactories.HIBERNATE).getTaskDAO().createTask(task);
	}
}
