package com.stefanini.librarybackend.dao.impl;


import com.stefanini.librarybackend.dao.HistoryDAO;
import com.stefanini.librarybackend.domain.History;

public class HistoryDAOImpl extends DAOAbstractImpl<History> implements HistoryDAO<History> {
    public HistoryDAOImpl() {
        setClazz(History.class);
    }
}
