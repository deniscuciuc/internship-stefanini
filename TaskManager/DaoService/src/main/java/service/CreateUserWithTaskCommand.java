package service;

import service.receivers.UserService;

public class CreateUserWithTaskCommand implements Command {
    private UserService userService;


    public CreateUserWithTaskCommand(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void execute() {
        userService.createUserWithTask();
    }
}
