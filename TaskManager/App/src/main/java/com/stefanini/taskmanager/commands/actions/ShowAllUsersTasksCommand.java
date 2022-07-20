package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import javafx.concurrent.Task;
import service.TaskService;
import service.impl.TaskServiceImpl;

/**
 * Concrete command that is used to show all user's tasks
 * @author dcuciuc
 */
public class ShowAllUsersTasksCommand implements Command {
	private String userName;
	private TaskService taskService = new TaskServiceImpl();

	public ShowAllUsersTasksCommand(String userName) {
		this.userName = userName;
	}

	/**
	 * Method calls concrete realizations of this command from service class through its interface - {@link TaskService UserService interface}
	 * @see TaskService#showAllUsersTasks(String)
	 */
	@Override
	public void execute() {
		taskService.showAllUsersTasks(userName);
	}
}
