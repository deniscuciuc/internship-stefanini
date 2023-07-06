package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.AuthorDAO;
import com.stefanini.librarybackend.domain.Author;
import com.stefanini.librarybackend.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorDAOImpl extends DAOAbstractImpl<Author> implements AuthorDAO<Author> {
    public AuthorDAOImpl() {
        setClazz(Author.class);
    }
}
