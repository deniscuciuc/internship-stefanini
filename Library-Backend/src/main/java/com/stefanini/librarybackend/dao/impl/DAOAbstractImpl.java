package com.stefanini.librarybackend.dao.impl;

import com.google.common.base.Preconditions;
import com.stefanini.librarybackend.dao.IGenericDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Repository
@Slf4j
public abstract class DAOAbstractImpl<T extends Serializable> implements IGenericDao<T> {
    private Class<T> clazz;
    @PersistenceContext
    EntityManager entityManager;

    public void setClazz(final Class<T> clazzToSet) {
        clazz = Preconditions.checkNotNull(clazzToSet);
    }

    // API
    @Override
    public List<T> getAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    public List<T> getAllSortedAndPaginated(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        TypedQuery<T> query = (TypedQuery<T>) entityManager.createQuery("select a from " + clazz.getName() + " a order by a." + sortBy + " " + sortOrder);

        int from = 0;

        if (pageNumber > 1) {
            from = pageNumber * pageSize - pageSize;
        }

        return query.setFirstResult(from)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public Long getNumberOf() {
        return (Long) entityManager.createQuery("select count(*) from " + clazz.getName()).getSingleResult();
    }

    @Override
    @Transactional
    public T update(T entity) {
        entityManager.merge(entity);
        log.info("Entity {} was updated", entity.getClass().getName());
        return entity;
    }

    @Override
    @Transactional
    public T create(T entity) {
        entityManager.persist(entity);
        log.info("Entity {} was created", entity.getClass().getName());
        return entity;
    }

    @Override
    public T getById(int id) {
        return entityManager.find(clazz, id);
    }

    @Override
    @Transactional
    public int removeById(int id) {
        entityManager.remove(getById(id));
        log.info("Entity with id {} was removed", id);
        return id;
    }
}