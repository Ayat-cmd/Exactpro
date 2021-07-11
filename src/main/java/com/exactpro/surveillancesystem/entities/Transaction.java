package com.exactpro.surveillancesystem.entities;

import java.time.Instant;

public class Transaction {
    private long transactionID;
    private String executionEntityName;
    private String instrumentName;
    private String instrumentClassification;
    private int quantity;
    private double price;
    private String currency;
    private Instant datestamp;
    private double netAmount;

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public String getExecutionEntityName() {
        return executionEntityName;
    }

    public void setExecutionEntityName(String executionEntityName) {
        this.executionEntityName = executionEntityName;
    }

    public String getInstrumentName() { return instrumentName; }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentClassification() {
        return instrumentClassification;
    }

    public void setInstrumentClassification(String instrumentClassification) {
        this.instrumentClassification = instrumentClassification; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Instant getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(Instant datestamp) {
        this.datestamp = datestamp;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }
}
