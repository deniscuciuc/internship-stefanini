package com.stefanini.pizzariabackend.service;

import com.stefanini.pizzariabackend.domain.Order;

import java.util.List;

/**
 * @author dcuciuc
 * @version 0.1
 * @since 0.1
 */
public interface OrderService {

    /**
     * Saves order.
     *
     * @param order to be saved
     * @return saved order
     */
    Order saveOrder(Order order);

    /**
     * Finds all orders.
     *
     * @return all orders or empty list
     */
    List<Order> findAllOrders();

    /**
     * Deletes order by its id.
     *
     * @param id of order to be deleted
     * @return id of deleted order
     */
    Long deleteOrderById(Long id);

    List<Order> getPaginatedOrders(int currentPage, int pageSize);
}
