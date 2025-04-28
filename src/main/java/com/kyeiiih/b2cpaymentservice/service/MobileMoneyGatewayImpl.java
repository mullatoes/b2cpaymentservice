package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentStatus;
import com.kyeiiih.b2cpaymentservice.dto.PaymentRequest;
import com.kyeiiih.b2cpaymentservice.dto.PaymentResponse;
import com.kyeiiih.b2cpaymentservice.enums.PaymentStatus;
import com.kyeiiih.b2cpaymentservice.exceptions.PaymentException;
import org.springframework.stereotype.Service;

@Service
public class MobileMoneyGatewayImpl implements MobileMoneyGateway {
    @Override
    public PaymentResponse initiateB2CPayment(PaymentRequest request) throws PaymentException {
        // Simulate payment processing
        if (request.getPhoneNumber().startsWith("+254")) {
            return new PaymentResponse(
                    "GATEWAY_" + request.getTransactionReference(),
                    PaymentStatus.SUCCESS,
                    "Payment processed successfully"
            );
        }
        throw new PaymentException("Invalid phone number");
    }

    @Override
    public B2CPaymentStatus checkTransactionStatus(String transactionId) throws PaymentException {
        return new B2CPaymentStatus(
                transactionId,
                PaymentStatus.SUCCESS,
                "Transaction completed"
        );
    }
}
