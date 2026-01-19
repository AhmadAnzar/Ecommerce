package com.example.ecommerce.webhook;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
public class PaymentWebhookController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public String handlePaymentWebhook(@RequestBody PaymentWebhookRequest request) {
        // Delegate the processing to the service
        paymentService.processWebhook(request.getOrderId(), request.getStatus());
        return "Webhook received successfully";
    }
}