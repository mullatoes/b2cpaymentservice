package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentStatus;
import com.kyeiiih.b2cpaymentservice.dto.PaymentRequest;
import com.kyeiiih.b2cpaymentservice.dto.PaymentResponse;
import com.kyeiiih.b2cpaymentservice.exceptions.PaymentException;

public interface MobileMoneyGateway {
    PaymentResponse initiateB2CPayment(PaymentRequest request) throws PaymentException;
    B2CPaymentStatus checkTransactionStatus(String transactionId) throws PaymentException;
}
