package service;

import service.receivers.Task;

public class ShowAllUsersTasksCommand implements Command {
	Task task;

	public ShowAllUsersTasksCommand(Task task) {
		this.task = task;
	}

	@Override
	public void execute() {
		task.showAllUsersTasks();
	}
}
