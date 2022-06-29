package service;


import service.receivers.UserService;

public class CreateUserCommand implements Command {
	private UserService userService;
	
	
	public CreateUserCommand(UserService userService) {
		this.userService = userService;
	}
	

	@Override
	public void execute() {
		userService.createUser();
	}

}
