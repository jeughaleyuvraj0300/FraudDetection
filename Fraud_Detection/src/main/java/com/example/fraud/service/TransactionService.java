package com.example.fraud.service;

import com.example.fraud.model.FraudAlert;
import com.example.fraud.model.Transaction;
import com.example.fraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private static final double LARGE_AMOUNT_THRESHOLD = 500000.0;
    private static final int DAILY_TRANSACTION_LIMIT = 5;
    private static final int CITY_LIMIT = 2;
    private static final LocalTime UNUSUAL_TIME_START = LocalTime.of(22, 0); // 10 PM
    private static final LocalTime UNUSUAL_TIME_END = LocalTime.of(6, 0); // 6 AM

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<FraudAlert> checkTransaction(Transaction transaction) {
        List<FraudAlert> alerts = new ArrayList<>();
        transactionRepository.save(transaction);

        if (transaction.getAmount() > LARGE_AMOUNT_THRESHOLD) {
            alerts.add(new FraudAlert("Large Amount Transaction", transaction));
        }

        if (getTransactionsByUserId(transaction.getUserId()).size() > DAILY_TRANSACTION_LIMIT) {
            alerts.add(new FraudAlert("Daily Transaction Limit Exceeded", transaction));
        }

        if (getUniqueCitiesByUserId(transaction.getUserId()).size() > CITY_LIMIT) {
            alerts.add(new FraudAlert("Transactions from more than 2 different cities", transaction));
        }

        if (isTransactionAtUnusualTime(transaction)) {
            alerts.add(new FraudAlert("Transaction at Unusual Time", transaction));
        }

        return alerts;
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    private List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findAll().stream()
                .filter(tx -> tx.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    private List<String> getUniqueCitiesByUserId(Long userId) {
        return transactionRepository.findAll().stream()
                .filter(tx -> tx.getUserId().equals(userId))
                .map(Transaction::getCity)
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isTransactionAtUnusualTime(Transaction transaction) {
        LocalTime transactionTime = transaction.getTime().toLocalTime();
        return transactionTime.isAfter(UNUSUAL_TIME_START) || transactionTime.isBefore(UNUSUAL_TIME_END);
    }
}
