package com.kyeiiih.b2cpaymentservice.service;

import com.kyeiiih.b2cpaymentservice.dto.B2CPaymentStatus;
import com.kyeiiih.b2cpaymentservice.dto.PaymentRequest;
import com.kyeiiih.b2cpaymentservice.dto.PaymentResponse;
import com.kyeiiih.b2cpaymentservice.exceptions.PaymentException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MobileMoneyGatewayImpl implements MobileMoneyGateway {

    private final MpesaService mpesaService;
    private final AirtelMoneyService airtelMoneyService;

    @Override
    public PaymentResponse initiateB2CPayment(PaymentRequest request) throws PaymentException {

        if (request.getTransactionReference().contains("AIRTEL")) {
            return airtelMoneyService.initiateB2CPayment(request);
        }
        if (request.getTransactionReference().contains("MPESA")) {
            return mpesaService.initiateB2CPayment(request);
        }
        throw new PaymentException("Unsupported phone number format");
    }

    @Override
    public B2CPaymentStatus checkTransactionStatus(String transactionId) throws PaymentException {
        //Assuming MPESA transactions start with MPESA keyword
        if (transactionId.startsWith("MPESA")) {
            return mpesaService.checkTransactionStatus(transactionId);
        } else if (transactionId.startsWith("AIRTEL")) {
            return airtelMoneyService.checkTransactionStatus(transactionId);
        }

        throw new PaymentException("Unknown transaction ID format");
    }
}
