package com.stefanini.pizzariabackend.controller;

import com.stefanini.pizzariabackend.domain.Service;
import com.stefanini.pizzariabackend.service.ServiceService;
import com.stefanini.pizzariabackend.service.impl.ServiceServiceImpl;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidIdException;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceServiceImpl serviceServiceImpl) {
        this.serviceService = serviceServiceImpl;
    }

    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceService.saveService(service);
    }

    @GetMapping
    public List<Service> findAllServices() {
        return serviceService.findAllServices();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteServiceById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(serviceService.deleteServiceById(id));
        } catch (InvalidIdException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }
}
