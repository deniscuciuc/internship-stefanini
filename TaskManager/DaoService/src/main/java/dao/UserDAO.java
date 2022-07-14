package dao;

import domain.UserEntity;
import java.util.List;

/**
 * Through this interface we get access to low level operations,
 * @author dcuciuc
 */
public interface UserDAO {

	/**
	 * Methods calls its implementation to get all users as a list from the database by selecting available DAO factory
	 * @return List<UserEntity>
	 */
	List<UserEntity> getAllUsers();

	/**
	 * Method calls its implementation by selecting available DAO factory to get user from database by his username
	 * @param userName
	 * @return UserEntity
	 */
	UserEntity getUserByUserName(String userName);
}
