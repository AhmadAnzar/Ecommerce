package com.example.ecommerce.controller;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments") // Note the path
public class PaymentController {

    @Autowired private PaymentService service;

    @PostMapping("/create")
    public Payment createPayment(@RequestBody PaymentRequest req) {
        return service.initiatePayment(req.getOrderId(), req.getAmount());
    }

    // REMOVE the webhook method from here. It is now in the `webhook` package.
}