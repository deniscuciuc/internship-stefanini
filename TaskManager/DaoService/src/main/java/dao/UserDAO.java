package dao;

import domain.UserEntity;
import java.util.List;

/**
 * Through this interface we get access to low level operations,
 * which have their own classes that implements this class, for example UserJdbcDAO and UserHibernateDAO
 * @author dcuciuc
 */
public interface UserDAO {

	/**
	 * Method calls its implementation to create/save the user in the database
	 * @param user
	 */
	void createUser(UserEntity user);

	/**
	 * Methods calls its implementation to get all users as a list from the database
	 * @return List<UserEntity>
	 */
	List<UserEntity> getAllUsers();

	/**
	 * Method calls its implementation to get user from database by his username
	 * @param userName
	 * @return UserEntity
	 */
	UserEntity getUserByUserName(String userName);
}
