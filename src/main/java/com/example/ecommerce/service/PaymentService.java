package com.example.ecommerce.service;

import com.example.ecommerce.client.PaymentServiceClient; // Import the new client
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentService {
    @Autowired private PaymentRepository paymentRepo;
    @Autowired private OrderService orderService;
    @Autowired private PaymentServiceClient paymentServiceClient; // Inject Client

    public Payment initiatePayment(String orderId, Double amount) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setStatus("PENDING");

        // Use the Client to get the ID
        String externalId = paymentServiceClient.initiatePayment(orderId, amount);
        payment.setPaymentId(externalId);

        return paymentRepo.save(payment);
    }

    public void processWebhook(String orderId, String status) {
        if ("captured".equalsIgnoreCase(status) || "success".equalsIgnoreCase(status)) {
            orderService.updateStatus(orderId, "PAID");

            // Optional: Update Payment record status too
            // Payment p = paymentRepo.findByOrderId(orderId);
            // p.setStatus("SUCCESS");
            // paymentRepo.save(p);
        } else {
            orderService.updateStatus(orderId, "FAILED");
        }
    }
}