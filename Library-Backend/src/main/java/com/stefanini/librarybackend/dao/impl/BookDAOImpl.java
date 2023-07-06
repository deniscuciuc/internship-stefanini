package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.BookDAO;
import com.stefanini.librarybackend.domain.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class BookDAOImpl extends DAOAbstractImpl<Book> implements BookDAO<Book> {
    public BookDAOImpl() {
        setClazz(Book.class);
    }


    @Override
    public List<Book> getBooksByAnyCriteria(String criteria) {
        return entityManager.createQuery("from Book" +  " WHERE  LCASE(title) LIKE '%" + criteria.toLowerCase() +
                "%' OR LCASE(description) LIKE '%" + criteria.toLowerCase() + "%'" ).getResultList();
    }


}
