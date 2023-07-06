package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.UserDAO;
import com.stefanini.librarybackend.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository

public class UserDAOImpl extends DAOAbstractImpl<User> implements UserDAO<User> {

    public UserDAOImpl() {
        setClazz(User.class);
    }

    @Override
    public User findUserByEmail(String email) {
        TypedQuery query = entityManager.createQuery("select a from User a where a.email = ?1", User.class);
        query.setParameter(1, email);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (User) query.getResultList().get(0);
    }


    @Override
    public List<User> getUsersByCriteria(String criteria) {
        return entityManager.createQuery("from User" + " WHERE  LCASE(email) LIKE '%" + criteria.toLowerCase() +
                "" + "%'").getResultList();
    }


}
