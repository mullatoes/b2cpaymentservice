package com.kyeiiih.b2cpaymentservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class B2CPaymentRequest {
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+254[0-9]{9}$", message = "Invalid Kenyan phone number")
    private String phoneNumber;

    @Positive(message = "Amount must be positive")
    @DecimalMin(value = "1.0", message = "Amount must be at least 1")
    private double amount;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^KES$", message = "Only KES is supported")
    private String currency;

    @NotBlank(message = "Transaction reference is required")
    private String transactionReference;
}
