package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import service.UserService;

/**
 * Concrete command that is used to create new user
 * @author dcuciuc
 */
public class CreateUserCommand implements Command {
	private UserService userService;
	
	
	public CreateUserCommand(UserService userService) {
		this.userService = userService;
	}


	/**
	 * Method calls concrete realizations of this command from service class through its interface - {@link UserService UserService interface}
	 * @see UserService#createUser()
	 */
	@Override
	public void execute() {
		userService.createUser();
	}

}
