package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.Service;
import com.stefanini.pizzariabackend.repo.ServiceRepository;
import com.stefanini.pizzariabackend.service.ServiceService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import com.stefanini.pizzariabackend.service.impl.helper.ValuesChecker;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public Service saveService(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<Service> findAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public Long deleteServiceById(Long id) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(id);
        verifyIfServiceExistsById(id);
        serviceRepository.deleteById(id);
        return id;
    }

    private void verifyIfServiceExistsById(Long id) throws NotFoundException {
        boolean doesServiceExist = serviceRepository.existsById(id);
        if (!doesServiceExist) {
            log.error("Service with id {} not found", id);
            throw new NotFoundException("Service with id " + id + " not found");
        }
    }
}
