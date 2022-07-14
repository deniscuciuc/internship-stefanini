package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import service.TaskService;

/**
 * Concrete command that is used to add / create task to the user
 * @author dcuciuc
 */
public class AddTaskCommand implements Command {
	private TaskService taskService;

	public AddTaskCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * Method calls concrete realizations of this command from service class through its interface - {@link TaskService TaskService interface}
	 * @see TaskService#addTask()
	 */
	@Override
	public void execute() {
		taskService.addTask();
	}

}
