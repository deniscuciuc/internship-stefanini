package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import domain.TaskEntity;
import service.TaskService;
import service.impl.TaskServiceImpl;


public class AddTaskCommand implements Command {
	private TaskEntity task;
	private TaskService taskService = new TaskServiceImpl();

	public AddTaskCommand(TaskEntity task) {
		this.task = task;
	}

	@Override
	public void execute() {
		taskService.addTask(task);
	}
}
