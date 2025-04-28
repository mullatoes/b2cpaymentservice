package com.kyeiiih.b2cpaymentservice.entity;

import com.kyeiiih.b2cpaymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence"
    )
    @GeneratedValue(
            generator = "transaction_sequence",
            strategy = GenerationType.SEQUENCE)
    private Long id;
    private String transactionReference;
    private String phoneNumber;
    private double amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String gatewayTransactionId;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
