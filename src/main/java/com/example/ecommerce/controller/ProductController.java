package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return service.createProduct(product);
    }

    @GetMapping
    public Iterable<Product> getProducts() {
        return service.getAllProducts();
    }
}
