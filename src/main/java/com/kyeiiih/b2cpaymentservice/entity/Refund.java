package com.kyeiiih.b2cpaymentservice.entity;

import com.kyeiiih.b2cpaymentservice.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
@Data
public class Refund {
    @Id
    @SequenceGenerator(
            name = "refunds_sequence",
            sequenceName = "refunds_sequence"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refunds_sequence")
    private String id;

    @Column(nullable = false)
    private String paymentId;

    @Column(nullable = false)
    private Double amount;

    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}


