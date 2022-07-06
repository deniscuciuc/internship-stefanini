package com.stefanini.taskmanager;

import service.Command;

/**
 * Simple invoker class that will be used only in client class to define and call a specific command
 * @author dcuciuc
 */
public class Invoker {
	private Command action;


	/**
	 * Using this method and using the command interface as a parameter, the client class can set a specific command
	 * @param command
	 */
	public void setCommand(Command command) {
		action = command;
	}


	/**
	 * The method will execute a specific command after it has been set
	 */
	public void executeCommand() {
		action.execute();
	}
}
