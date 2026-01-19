package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo,
                        OrderItemRepository orderItemRepo,
                        CartRepository cartRepo,
                        ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public Order createOrderFromCart(String userId) {

        List<CartItem> cartItems = cartRepo.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("CREATED");
        order = orderRepo.save(order);

        for (CartItem cartItem : cartItems) {

            Product product = productRepo.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // --- STOCK CHECK & UPDATE (NEW) ---
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Out of stock: " + product.getName());
            }

            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepo.save(product);
            // ----------------------------------

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            total += product.getPrice() * cartItem.getQuantity();
            orderItemRepo.save(orderItem);
        }

        order.setTotalAmount(total);
        cartRepo.deleteByUserId(userId);

        return orderRepo.save(order);
    }

    public Order getOrder(String orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // --- BONUS: ORDER HISTORY ---
    public List<Order> getUserOrders(String userId) {
        return orderRepo.findByUserId(userId);
    }
    // ----------------------------

    public void updateStatus(String orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        orderRepo.save(order);
    }
}
