package com.stefanini.librarybackend.dao;

import java.util.List;

/**
 * Generic dao class for crud entity operations.
 *
 * @param <T>
 */
public interface IGenericDao<T> {

    /**
     * Get all entities.
     *
     * @return list of all entities
     */
    List<T> getAll();

    /**
     * Get all sorted and paginated.
     *
     * @param pageNumber current page
     * @param pageSize   how many entities to get
     * @param sortBy     one of field from db
     * @param sortOrder
     * @return sorted and paginated list of T
     */
    List<T> getAllSortedAndPaginated(int pageNumber, int pageSize, String sortBy, String sortOrder);

    /**
     * Return number of entities in db.
     *
     * @return number of entities
     */
    Long getNumberOf();

    /**
     * Update entity.
     *
     * @param entity
     * @return updated entity
     */
    T update(T entity);

    /**
     * Create entity.
     *
     * @param entity
     * @return created entity
     */
    T create(T entity);

    /**
     * Get entity by id.
     *
     * @param id of entity to be found
     * @return entity
     */
    T getById(int id);

    /**
     * Remove by id.
     *
     * @param id of entity to be found
     * @return id of removed entity
     */
    int removeById(int id);


}