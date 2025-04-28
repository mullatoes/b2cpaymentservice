package com.kyeiiih.b2cpaymentservice.controller;

import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentRequest;
import com.kyeiiih.b2cpaymentservice.dto.RefundRequest;
import com.kyeiiih.b2cpaymentservice.dto.RefundResponse;
import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentResponse;
import com.kyeiiih.b2cpaymentservice.dto.TransactionStatusResponse;
import com.kyeiiih.b2cpaymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        "/api/v1/payments"
)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/b2c")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<B2CPaymentResponse> initiateB2CPayment(@Valid @RequestBody B2CPaymentRequest request) {
        B2CPaymentResponse response = paymentService.initiatePayment(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/status/{transactionId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TransactionStatusResponse> getTransactionStatus(@PathVariable String transactionId) {
        TransactionStatusResponse response = paymentService.getTransactionStatus(transactionId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refund")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RefundResponse> initiateRefund(@Valid @RequestBody RefundRequest request) {
        RefundResponse response = paymentService.initiateRefund(request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
