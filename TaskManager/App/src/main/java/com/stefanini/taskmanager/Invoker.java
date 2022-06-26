package com.stefanini.taskmanager;

import service.Command;

public class Invoker {
	Command action;
	
	public Invoker() {
		
	}
	
	public void setCommand(Command command) {
		action = command;
	}
	
	public void executeCommand() {
		action.execute();
	}
}
