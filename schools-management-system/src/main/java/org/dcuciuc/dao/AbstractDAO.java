package org.dcuciuc.dao;

import org.dcuciuc.dao.exceptions.EntityNotFoundException;

/**
 * AbstractDAO is responsible for all CRUD methods.
 * Each implementation from an interface that inherits it implements all of these methods
 *
 * @param <T>
 * @author dcuciuc
 */
public interface AbstractDAO<T> {

    /**
     * Saves entity in database
     *
     * @param entity to be saved in database
     * @return entity with id
     * @throws EntityNotFoundException if something went wrong and after creating entity was not found by generated key
     */
    T create(T entity) throws EntityNotFoundException;

    /**
     * Updates entity in database
     *
     * @param entity new data of entity to be saved
     * @param id     of entity to be updated
     * @return updated entity from database
     * @throws EntityNotFoundException if id of entity to be updated was not found
     */
    T update(T entity, Long id) throws EntityNotFoundException;

    /**
     * Removes entity from database
     *
     * @param id of entity to be removed
     * @return id of removed entity
     * @throws EntityNotFoundException if entity not found with such id
     */
    Long removeById(Long id) throws EntityNotFoundException;

    /**
     * Finds entity in database from database
     *
     * @param id of entity to be found
     * @return found entity
     * @throws EntityNotFoundException if entity with such id was not found
     */
    T getById(Long id) throws EntityNotFoundException;

}
