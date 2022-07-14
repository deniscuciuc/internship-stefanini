package service;

/**
 * This class represents an interface for users methods realization.
 * By calling this class we can access to all commands realization and execute all operations with users
 * @author dcuciuc
 */
public interface UserService {

	/**
	 * Method creates new user by getting user details from console and save it in database
	 */
	void createUser();

	/**
	 * Method creates a new user and instantly generates a task for him.
	 * User's and task's details are getting from console, then method save user and task in database
	 */
	void createUserWithTask();

	/**
	 * Method shows / prints all information about users by getting them from database
	 */
	void showAllUsers();
}
