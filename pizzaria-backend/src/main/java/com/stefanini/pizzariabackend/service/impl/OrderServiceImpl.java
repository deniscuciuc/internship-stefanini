package com.stefanini.pizzariabackend.service.impl;

import com.stefanini.pizzariabackend.domain.Order;
import com.stefanini.pizzariabackend.repo.OrderRepository;
import com.stefanini.pizzariabackend.service.OrderService;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import com.stefanini.pizzariabackend.service.impl.helper.ValuesChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Long deleteOrderById(Long id) {
        ValuesChecker.verifyIdAndIfInvalidThrowException(id);
        verifyIfOrderExistsById(id);
        orderRepository.deleteById(id);
        return id;
    }

    @Override
    public List<Order> getPaginatedOrders(int currentPage, int pageSize) {
        ValuesChecker.verifyPaginatingValuesAndThrowExceptionIfInvalidValues(currentPage, pageSize);

        Pageable paging = PageRequest.of(currentPage, pageSize);
        Page<Order> pagedResult = orderRepository.findAll(paging);

        return pagedResult.getContent();
    }

    private void verifyIfOrderExistsById(Long id) throws NotFoundException {
        boolean doesOrderExist = orderRepository.existsById(id);
        if (!doesOrderExist) {
            log.error("Order with such id not found");
            throw new NotFoundException("Order with such id not found");
        }
    }

}
