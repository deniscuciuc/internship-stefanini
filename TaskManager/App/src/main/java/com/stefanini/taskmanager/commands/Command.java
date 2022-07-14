package com.stefanini.taskmanager.commands;

import com.stefanini.taskmanager.commands.actions.AddTaskCommand;

/**
 * Command interface is the way to execute concrete commands such as {@link AddTaskCommand AddTaskCommand}
 * @author dcuciuc
 */
public interface Command {

	/**
	 * Method executes command
	 */
	void execute();
}