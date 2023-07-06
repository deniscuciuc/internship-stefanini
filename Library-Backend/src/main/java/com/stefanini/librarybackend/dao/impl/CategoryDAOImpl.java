package com.stefanini.librarybackend.dao.impl;

import com.stefanini.librarybackend.dao.CategoryDAO;
import com.stefanini.librarybackend.domain.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAOImpl extends DAOAbstractImpl<Category> implements CategoryDAO<Category> {

    public CategoryDAOImpl() {
        setClazz(Category.class);
    }
}
