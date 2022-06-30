package dao;



import java.util.List;

import domain.UserEntity;

public interface UserDAO {
	void createUser(UserEntity user);
	List<UserEntity> getAllUsers();
}
