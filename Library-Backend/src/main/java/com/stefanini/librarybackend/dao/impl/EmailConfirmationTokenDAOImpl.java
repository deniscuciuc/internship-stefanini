package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.EmailConfirmationTokenDAO;
import com.stefanini.librarybackend.domain.ConfirmationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class EmailConfirmationTokenDAOImpl extends DAOAbstractImpl<ConfirmationToken> implements EmailConfirmationTokenDAO<ConfirmationToken> {

    public EmailConfirmationTokenDAOImpl() {
        setClazz(ConfirmationToken.class);
    }

    @Override
    public ConfirmationToken findByToken(String token) {
        TypedQuery query = entityManager.createQuery(
                "select a from ConfirmationToken a where a.token = ?1", ConfirmationToken.class
        );
        query.setParameter(1, token);
        if (query.getResultList().isEmpty()) {
            return null;
        }
        return (ConfirmationToken) query.getResultList().get(0);
    }
}
