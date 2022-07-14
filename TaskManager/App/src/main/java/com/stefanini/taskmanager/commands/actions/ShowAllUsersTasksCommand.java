package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import service.TaskService;

/**
 * Concrete command that is used to show all user's tasks
 * @author dcuciuc
 */
public class ShowAllUsersTasksCommand implements Command {
	private TaskService taskService;

	public ShowAllUsersTasksCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * Method calls concrete realizations of this command from service class through its interface - {@link TaskService UserService interface}
	 * @see TaskService#showAllUsersTasks()
	 */
	@Override
	public void execute() {
		taskService.showAllUsersTasks();
	}
}
