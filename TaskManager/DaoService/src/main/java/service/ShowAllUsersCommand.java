package service;

import service.receivers.User;

public class ShowAllUsersCommand implements Command {
	User user;
	
	public ShowAllUsersCommand(User user) {
		this.user = user;
	}

	@Override
	public void execute() {
		user.showAllUsers();
	}

}
