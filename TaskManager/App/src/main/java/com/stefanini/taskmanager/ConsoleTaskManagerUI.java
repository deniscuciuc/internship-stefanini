package com.stefanini.taskmanager;

import java.util.Scanner;

import service.AddTaskCommand;
import service.CreateUserCommand;
import service.ShowAllUsersCommand;
import service.ShowAllUsersTasksCommand;
import service.receivers.Task;
import service.receivers.User;

public class ConsoleTaskManagerUI {
	
	Invoker invoker = new Invoker();
	User user = new User();
	Task task = new Task();
	Scanner scanner = new Scanner(System.in);
	
	public void launchUI() {
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
				default:
					System.out.println("Invalid option! Try again");
			}
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
