package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.Service;

import java.util.List;

/**
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface ServiceService {

    /**
     * Saves service.
     *
     * @param service to be saved
     * @return saved service
     */
    Service saveService(Service service);

    /**
     * Finds all services.
     *
     * @return all services or empty list
     */
    List<Service> findAllServices();

    /**
     * Deletes service by its id.
     *
     * @param id of service to be deleted
     * @return id of deleted service
     */
    Long deleteServiceById(Long id);
}
