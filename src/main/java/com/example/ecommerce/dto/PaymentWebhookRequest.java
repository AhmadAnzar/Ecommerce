package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class PaymentWebhookRequest {
    // This matches the structure Razorpay (or your mock) sends back
    private String orderId;
    private String paymentId;
    private String status; // e.g., "captured", "failed"
}