package com.stefanini.pizzariabackend.controller;

import com.stefanini.pizzariabackend.domain.Order;
import com.stefanini.pizzariabackend.service.OrderService;
import com.stefanini.pizzariabackend.service.impl.OrderServiceImpl;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidIdException;
import com.stefanini.pizzariabackend.service.impl.exception.InvalidPageValueException;
import com.stefanini.pizzariabackend.service.impl.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderService = orderServiceImpl;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(orderService.deleteOrderById(id));
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

    @GetMapping("/pagination/{current-page}/{page-size}")
    public ResponseEntity<?> getPaginatedOrders(
            @PathVariable("current-page") int currentPage,
            @PathVariable("page-size") int pageSize
    ) {
        try {
            return ResponseEntity
                    .status(ACCEPTED)
                    .body(orderService.getPaginatedOrders(currentPage, pageSize));
        } catch (InvalidPageValueException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }
}
