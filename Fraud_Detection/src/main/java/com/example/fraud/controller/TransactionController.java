package com.example.fraud.controller;

import com.example.fraud.model.FraudAlert;
import com.example.fraud.model.Transaction;
import com.example.fraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        try {
            List<FraudAlert> alerts = transactionService.checkTransaction(transaction);
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing transaction: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getTransactions() {
        try {
            List<Transaction> transactions = transactionService.getTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching transactions: " + e.getMessage());
        }
    }
}
