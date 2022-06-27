package com.stefanini.taskmanager;

import service.Command;

/**
 * This is invoker class that will set and execute commands
 * @author dcuciuc
 *
 */
public class Invoker {
	Command action;
	
	
	/**
	 * Set the command class for Invoker
	 * @param command
	 */
	public void setCommand(Command command) {
		action = command;
	}
	
	
	/**
	 * Execute current command
	 */
	public void executeCommand() {
		action.execute();
	}
}
