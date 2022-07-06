package com.stefanini.taskmanager;

import java.util.Scanner;

import service.*;
import service.receivers.impl.TaskServiceImpl;
import service.receivers.impl.UserServiceImpl;

/**
 * This class represents the user interface of the console.
 * Its role is to select a command of the user's choice and call the invoker class for further execution and invocation of commands
 * @author dcuciuc
 */
public class ConsoleTaskManagerUI {
	
	private Invoker invoker = new Invoker();
	private UserServiceImpl user = new UserServiceImpl();
	private TaskServiceImpl task = new TaskServiceImpl();
	
	
	/**
	 * Display console user interface and asks user for an option.
	 * Then will execute selected command by calling private methods for each option
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
					createUserWithTask();
					break;
				case 3:
					showAllUsers();
					break;
				case 4:
					addTask();
					break;
				case 5:
					showAllUsersTasks();
					break;
				case 6:
					System.out.println("Exiting...");
					return;
				default:
					System.out.println("Invalid option! Try again");
					break;
			}
			System.out.print("Select option: ");
		} while (userChoice != 6);
	}
	
	private void showMenu() {
		System.out.println("1. Create user");
		System.out.println("2. Create user and give him a task");
		System.out.println("3. Show all users");
		System.out.println("4. Add task to the user");
		System.out.println("5. Show user's tasks");
		System.out.println("6. Save and exit");
		System.out.print("Select option: ");
	}
	
	private void createUser() {
		invoker.setCommand(new CreateUserCommand(user));
		invoker.executeCommand();
	}

	private void createUserWithTask() {
		invoker.setCommand(new CreateUserWithTaskCommand(user));
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
