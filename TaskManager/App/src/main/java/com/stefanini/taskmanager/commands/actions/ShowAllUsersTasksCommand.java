package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import javafx.concurrent.Task;
import service.TaskService;
import service.impl.TaskServiceImpl;


public class ShowAllUsersTasksCommand implements Command {
	private String userName;
	private TaskService taskService;

	public ShowAllUsersTasksCommand(String userName) {
		this.userName = userName;
		taskService = new TaskServiceImpl();
	}

	
	@Override
	public void execute() {
		taskService.showAllUsersTasks(userName);
	}
}
