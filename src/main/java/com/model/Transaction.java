package com.model;

import java.time.LocalDate;

public class Transaction {

    private Integer id;
    private Integer transactionCategoryId;
    private String transactionName;
    private double transactionAmount;
    private LocalDate transactionDate;
    private String transactionType;

    public Transaction(){

    }

    public Transaction(Integer id, Integer transactionCategoryId, String transactionName, double transactionAmount, LocalDate transactionDate, String transactionType) {
        this.id = id;
        this.transactionCategoryId = transactionCategoryId;
        this.transactionName = transactionName;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransactionCategoryId() {
        return transactionCategoryId;
    }

    public void setTransactionCategoryId(Integer transactionCategoryId) {
        this.transactionCategoryId = transactionCategoryId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
