package service.receivers;

/**
 * This class represents an interface for users methods realization.
 * By calling this class we can access to all commands realization and execute all operations with users
 * @author dcuciuc
 */
public interface UserService {

	/**
	 * Method creates new user and save it
	 */
	void createUser();

	/**
	 * Method creates a new user and instantly generates a task for him
	 */
	void createUserWithTask();

	/**
	 * Method shows / prints all information about users
	 */
	void showAllUsers();
}
