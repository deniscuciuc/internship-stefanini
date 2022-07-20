package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import domain.UserEntity;
import service.UserService;
import service.impl.UserServiceImpl;

public class CreateUserCommand implements Command {
	private UserEntity user;

	private UserService userService = new UserServiceImpl();
	
	
	public CreateUserCommand(UserEntity user) {
		this.user = user;
	}



	@Override
	public void execute() {
		userService.createUser(user);
	}
}
