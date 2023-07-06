package service;

import domain.UserEntity;

/**
 * This class represents an interface for users methods realization.
 * By calling this class we can access to all commands realization and execute all operations with users
 * @author dcuciuc
 */
public interface UserService {

	/**
	 * Method creates new user by getting user details from console and save it in database
     * @param user
     */
	void createUser(UserEntity user);

	/**
	 * Method shows / prints all information about users by getting them from database
	 */
	void showAllUsers();
}
