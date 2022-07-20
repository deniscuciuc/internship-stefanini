package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import service.UserService;
import service.impl.UserServiceImpl;


public class ShowAllUsersCommand implements Command {
	private UserService userService = new UserServiceImpl();


	@Override
	public void execute() {
		userService.showAllUsers();
	}

}
