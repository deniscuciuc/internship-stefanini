package com.stefanini.taskmanager.commands;

/**
 * Simple invoker class that will be used only in client class to define and call a specific command
 * @author dcuciuc
 */
public class Invoker implements Runnable {
	private Command action;


	/**
	 * Using this method and using the command interface as a parameter, the client class can set a specific command
	 * @param command
	 */
	public void setCommand(Command command) {
		action = command;
	}


	@Override
	public void run() {
		action.execute();
	}
}
