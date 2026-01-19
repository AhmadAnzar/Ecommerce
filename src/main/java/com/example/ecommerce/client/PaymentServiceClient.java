package com.example.ecommerce.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.provider.url:http://localhost:8080/mock-bank/pay}")
    private String paymentProviderUrl;

    public String initiatePayment(String orderId, Double amount) {
        // In a real app, this makes a POST request to Razorpay/Stripe.
        // For this assignment's "Mock" phase, we can either:
        // 1. Actually call a mock endpoint if you have one.
        // 2. Just return a generated ID immediately (Simulated).

        // Simulating the "Call" to the provider:
        System.out.println("Calling Payment Provider for Order: " + orderId);

        // If you were actually calling an external mock service:
        // return restTemplate.postForObject(paymentProviderUrl, request, String.class);

        // For now, return a fake Transaction ID
        return "pay_ext_" + java.util.UUID.randomUUID().toString().substring(0, 8);
    }
}