package com.example.ecommerce.controller;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest req) {
        return service.createOrderFromCart(req.getUserId());
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return service.getOrder(orderId);
    }

    // --- BONUS: ORDER HISTORY ---
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable String userId) {
        return service.getUserOrders(userId);
    }
}
