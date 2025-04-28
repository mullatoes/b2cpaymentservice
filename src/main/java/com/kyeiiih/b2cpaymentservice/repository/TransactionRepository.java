package com.kyeiiih.b2cpaymentservice.repository;

import com.kyeiiih.b2cpaymentservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionReference(String transactionReference);
}
