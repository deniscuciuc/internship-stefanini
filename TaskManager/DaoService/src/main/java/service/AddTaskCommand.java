package service;

import service.receivers.TaskService;

public class AddTaskCommand implements Command {
	TaskService taskService;

	public AddTaskCommand(TaskService taskService) {
		this.taskService = taskService;
	}

	@Override
	public void execute() {
		taskService.addTask();
	}

}
