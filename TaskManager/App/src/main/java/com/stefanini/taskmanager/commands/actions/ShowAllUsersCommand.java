package com.stefanini.taskmanager.commands.actions;

import com.stefanini.taskmanager.commands.Command;
import service.UserService;

/**
 * Concrete command that is used to show all users
 * @author dcuciuc
 */
public class ShowAllUsersCommand implements Command {
	private UserService userService;
	
	public ShowAllUsersCommand(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Method calls concrete realizations of this command from service class through its interface - {@link UserService UserService interface}
	 * @see UserService#showAllUsers()
	 */
	@Override
	public void execute() {
		userService.showAllUsers();
	}

}
