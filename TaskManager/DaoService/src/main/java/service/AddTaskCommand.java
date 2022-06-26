package service;

import service.receivers.Task;

public class AddTaskCommand implements Command {
	Task task;

	public AddTaskCommand(Task task) {
		this.task = task;
	}

	@Override
	public void execute() {
		task.addTask();
	}

}
