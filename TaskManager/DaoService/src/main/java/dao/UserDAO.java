package dao;


/**
 * Through this interface we get access to low level operations,
 * which have their own classes that implements this class, for example UserJdbcDAO and UserHibernateDAO
 * @author dcuciuc
 *
 */

import domain.entities.UserEntity;

import java.util.List;

public interface UserDAO {
	void createUser(UserEntity user);
	List<UserEntity> getAllUsers();
}
