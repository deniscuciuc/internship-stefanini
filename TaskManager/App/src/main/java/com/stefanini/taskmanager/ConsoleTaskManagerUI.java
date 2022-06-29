package com.stefanini.taskmanager;

import java.util.Scanner;

import service.AddTaskCommand;
import service.CreateUserCommand;
import service.ShowAllUsersCommand;
import service.ShowAllUsersTasksCommand;
import service.receivers.TaskService;
import service.receivers.UserService;

/**
 * This class represents Console UI for all commands
 * @author dcuciuc
 */
public class ConsoleTaskManagerUI {
	
	private Invoker invoker = new Invoker();
	private UserService user = new UserService();
	private TaskService task = new TaskService();
	
	
	/**
	 *	Method will display console user interface and asks user for an option, then will execute selected command.
	 */
	public void launchUI() {
		Scanner scanner = new Scanner(System.in);
		
		showMenu();
		int userChoice;	
		do {
			userChoice = scanner.nextInt();
			switch (userChoice) {
				case 1:
					createUser();
					break;
				case 2:
					showAllUsers();
					break;
				case 3:
					addTask();
					break;
				case 4:
					showAllUsersTasks();
					break;
				case 5:
					System.out.println("Exiting...");
					return;
				default:
					System.out.println("Invalid option! Try again");
					break;
			}
			System.out.print("Select option: ");
		} while (userChoice != 5);
	}
	
	private void showMenu() {
		System.out.println("1. Create user");
		System.out.println("2. Show all users");
		System.out.println("3. Add task to the user");
		System.out.println("4. Show user's tasks");
		System.out.println("5. Save and exit");
		System.out.print("Select option: ");
	}
	
	private void createUser() {
		invoker.setCommand(new CreateUserCommand(user));
		invoker.executeCommand();
	}
	
	private void showAllUsers() {
		invoker.setCommand(new ShowAllUsersCommand(user));
		invoker.executeCommand();
	}
	
	private void addTask() {
		invoker.setCommand(new AddTaskCommand(task));
		invoker.executeCommand();
	}
	
	private void showAllUsersTasks() {
		invoker.setCommand(new ShowAllUsersTasksCommand(task));
		invoker.executeCommand();
	}
	
}
