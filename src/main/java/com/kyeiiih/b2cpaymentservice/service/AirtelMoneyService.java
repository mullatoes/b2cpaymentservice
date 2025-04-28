package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentStatus;
import com.kyeiiih.b2cpaymentservice.dto.PaymentRequest;
import com.kyeiiih.b2cpaymentservice.dto.PaymentResponse;
import com.kyeiiih.b2cpaymentservice.enums.PaymentStatus;
import com.kyeiiih.b2cpaymentservice.exceptions.PaymentException;
import org.springframework.stereotype.Service;

@Service
public class AirtelMoneyService {
    public PaymentResponse initiateB2CPayment(PaymentRequest request) throws PaymentException {
        return new PaymentResponse(
                "AIRTEL_" + request.getTransactionReference(),
                PaymentStatus.SUCCESS,
                "Airtel Money payment processed successfully"
        );
    }

    public B2CPaymentStatus checkTransactionStatus(String transactionId) throws PaymentException {
        return new B2CPaymentStatus(
                transactionId,
                PaymentStatus.SUCCESS,
                "Airtel Money transaction completed"
        );
    }
}
