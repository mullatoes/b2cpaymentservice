package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentRequest;
import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentResponse;
import com.kyeiiih.b2cpaymentservice.dto.PaymentRequest;
import com.kyeiiih.b2cpaymentservice.dto.PaymentResponse;
import com.kyeiiih.b2cpaymentservice.entity.Transaction;
import com.kyeiiih.b2cpaymentservice.enums.PaymentStatus;
import com.kyeiiih.b2cpaymentservice.exceptions.PaymentException;
import com.kyeiiih.b2cpaymentservice.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private MobileMoneyGateway mobileMoneyGateway;

    @Mock
    private NotificationService notificationService;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitiatePaymentSuccess() throws PaymentException {

        B2CPaymentRequest request = new B2CPaymentRequest();
        request.setPhoneNumber("+254712345678");
        request.setAmount(100.0);
        request.setCurrency("KES");
        request.setTransactionReference("TX123");

        PaymentResponse paymentResponse = new PaymentResponse("GATEWAY_TX123", PaymentStatus.SUCCESS, "Success");
        when(mobileMoneyGateway.initiateB2CPayment(any(PaymentRequest.class))).thenReturn(paymentResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());


        B2CPaymentResponse response = paymentService.initiatePayment(request);


        assertEquals("SUCCESS", response.getStatus());
        assertEquals("GATEWAY_TX123", response.getGatewayTransactionId());
        verify(notificationService, times(1)).sendNotification(anyString(), anyString());
    }

    @Test
    void testInitiatePaymentFailure() throws PaymentException {

        B2CPaymentRequest request = new B2CPaymentRequest();
        request.setPhoneNumber("+254712345678");
        request.setAmount(100.0);
        request.setCurrency("KES");
        request.setTransactionReference("TX123");

        when(mobileMoneyGateway.initiateB2CPayment(any(PaymentRequest.class)))
                .thenThrow(new PaymentException("Insufficient funds"));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> paymentService.initiatePayment(request));
        assertTrue(exception.getMessage().contains("Insufficient funds"));
        verify(notificationService, times(1)).sendNotification(anyString(), anyString());
    }

}