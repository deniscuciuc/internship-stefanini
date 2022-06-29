package service;

import service.receivers.UserService;

public class ShowAllUsersCommand implements Command {
	private UserService userService;
	
	public ShowAllUsersCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void execute() {
		userService.showAllUsers();
	}

}
