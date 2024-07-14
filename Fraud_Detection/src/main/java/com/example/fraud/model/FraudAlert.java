package com.example.fraud.model;

public class FraudAlert {
    private String message;
    private Transaction transaction;

    public FraudAlert(String message, Transaction transaction) {
        this.message = message;
        this.transaction = transaction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
