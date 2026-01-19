package com.example.ecommerce.controller;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody AddToCartRequest req) {
        return service.addToCart(
                req.getUserId(),
                req.getProductId(),
                req.getQuantity()
        );
    }

    @GetMapping("/{userId}")
    public Iterable<CartItem> getCart(@PathVariable String userId) {
        return service.getCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable String userId) {
        service.clearCart(userId);
        return "Cart cleared";
    }
}
