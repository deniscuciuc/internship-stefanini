package com.stefanini.taskmanager;

import service.Command;


public class Invoker {
	private Command action;
	
	
	
	public void setCommand(Command command) {
		action = command;
	}
	
	
	
	public void executeCommand() {
		action.execute();
	}
}
