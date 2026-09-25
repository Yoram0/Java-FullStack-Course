package com.example.repo;

import com.example.business.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int accountId;
    private String transactionType;
    private BigDecimal amount;
    private Integer relatedAccountId;
    private Timestamp createdAt;

    public Transaction(int transactionId, int accountId, String transactionType, 
                       BigDecimal amount, Integer relatedAccountId, Timestamp createdAt) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.relatedAccountId = relatedAccountId;
        this.createdAt = createdAt;
    }

    public int getTransactionId() { return transactionId; }
    public int getAccountId() { return accountId; }
    public String getTransactionType() { return transactionType; }
    public BigDecimal getAmount() { return amount; }
    public Integer getRelatedAccountId() { return relatedAccountId; }
    public Timestamp getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        String related = (relatedAccountId != null && relatedAccountId != 0) 
            ? " | Related Account: " + relatedAccountId 
            : "";
        return String.format("[%s] ID: %d | Type: %-10s | Amount: $%s%s", 
            createdAt, transactionId, transactionType, amount, related);
    }
}