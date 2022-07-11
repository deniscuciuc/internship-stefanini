package service;

import service.receivers.UserService;

/**
 * Concrete command that is used to create new user with task instantly
 * @author dcuciuc
 */
public class CreateUserWithTaskCommand implements Command {
    private UserService userService;


    public CreateUserWithTaskCommand(UserService userService) {
        this.userService = userService;
    }


    /**
     * Method calls concrete realizations of this command from service class through its interface - {@link UserService UserService interface}
     * @see UserService#createUserWithTask()
     */
    @Override
    public void execute() {
        userService.createUserWithTask();
    }
}
