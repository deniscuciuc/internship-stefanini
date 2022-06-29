package service;

import service.receivers.TaskService;

public class ShowAllUsersTasksCommand implements Command {
	private TaskService taskService;

	public ShowAllUsersTasksCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public void execute() {
		taskService.showAllUsersTasks();
	}
}
