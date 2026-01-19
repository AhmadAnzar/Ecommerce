package com.example.ecommerce.service;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository repository;

    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public CartItem addToCart(String userId, String productId, Integer quantity) {
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setProductId(productId);
        item.setQuantity(quantity);
        return repository.save(item);
    }

    public List<CartItem> getCart(String userId) {
        return repository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        repository.deleteByUserId(userId);
    }
}
