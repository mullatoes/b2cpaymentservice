package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.*;
import com.kyeiiih.b2cpaymentservice.entity.*;
import com.kyeiiih.b2cpaymentservice.enums.PaymentStatus;
import com.kyeiiih.b2cpaymentservice.exceptions.PaymentException;
import com.kyeiiih.b2cpaymentservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final MobileMoneyGateway mobileMoneyGateway;
    private final NotificationService notificationService;
    private final TransactionRepository transactionRepository;

    public PaymentService(MobileMoneyGateway mobileMoneyGateway,
                          NotificationService notificationService,
                          TransactionRepository transactionRepository) {
        this.mobileMoneyGateway = mobileMoneyGateway;
        this.notificationService = notificationService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public B2CPaymentResponse initiatePayment(B2CPaymentRequest request) {
        logger.info("Initiating B2C payment for phone: {}, amount: {}", request.getPhoneNumber(), request.getAmount());

        Transaction transaction = new Transaction();
        transaction.setPhoneNumber(request.getPhoneNumber());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setTransactionReference(request.getTransactionReference());
        transaction.setStatus(PaymentStatus.PENDING);
        transactionRepository.save(transaction);

        try {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setPhoneNumber(request.getPhoneNumber());
            paymentRequest.setAmount(request.getAmount());
            paymentRequest.setCurrency(request.getCurrency());
            paymentRequest.setTransactionReference(request.getTransactionReference());

            PaymentResponse paymentResponse = mobileMoneyGateway.initiateB2CPayment(paymentRequest);

            transaction.setGatewayTransactionId(paymentResponse.getTransactionId());
            transaction.setStatus(paymentResponse.getStatus());
            transactionRepository.save(transaction);

            String smsMessage = String.format("B2C Payment of %s %s to %s is %s. Ref: %s",
                    request.getCurrency(), request.getAmount(), request.getPhoneNumber(),
                    paymentResponse.getStatus(), paymentResponse.getTransactionId());
            notificationService.sendNotification(request.getPhoneNumber(), smsMessage);

            return new B2CPaymentResponse(
                    transaction.getTransactionReference(),
                    paymentResponse.getStatus().toString(),
                    paymentResponse.getMessage(),
                    paymentResponse.getTransactionId()
            );
        } catch (PaymentException e) {
            logger.error("Payment failed for ref: {}", request.getTransactionReference(), e);
            transaction.setStatus(PaymentStatus.FAILED);
            transaction.setFailureReason(e.getMessage());
            transactionRepository.save(transaction);
            
            String smsMessage = String.format("B2C Payment of %s %s to %s failed. Reason: %s",
                    request.getCurrency(), request.getAmount(), request.getPhoneNumber(), e.getMessage());
            notificationService.sendNotification(request.getPhoneNumber(), smsMessage);

            throw new RuntimeException("Payment processing failed: " + e.getMessage());
        }
    }

    public TransactionStatusResponse getTransactionStatus(String transactionId) {
        logger.info("Checking status for transaction: {}", transactionId);

        Transaction transaction = transactionRepository.findByTransactionReference(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));

        try {
            B2CPaymentStatus status = mobileMoneyGateway.checkTransactionStatus(transaction.getGatewayTransactionId());
            transaction.setStatus(status.getStatus());
            transactionRepository.save(transaction);

            return new TransactionStatusResponse(
                    transaction.getTransactionReference(),
                    status.getStatus(),
                    status.getDetails(),
                    transaction.getPhoneNumber(),
                    transaction.getAmount(),
                    transaction.getCurrency()
            );
        } catch (PaymentException e) {
            logger.error("Failed to check status for transaction: {}", transactionId, e);
            throw new RuntimeException("Status check failed: " + e.getMessage());
        }
    }

    public RefundResponse initiateRefund(RefundRequest request) {
        //Custom refund implementation here
        return null;
    }

    private String getCurrentUserId() {
        return "0001";
    }
}
