package com.kyeiiih.b2cpaymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefundRequest {
    @NotBlank
    private String transactionId;

    @NotNull
    @Positive
    private Double amount;

    private String reason;
}
