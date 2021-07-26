package com.exactpro.surveillancesystem.entities;

import java.time.Instant;
import java.util.Objects;

public class Transaction {
    private String alertId;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    private long transactionID;
    private String executionEntityName;
    private String instrumentName;
    private String instrumentClassification;
    private int quantity;
    private double price;
    private  String currency;
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

    public  String getCurrency() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionID == that.transactionID && quantity == that.quantity && Double.compare(that.price, price) == 0 && Double.compare(that.netAmount, netAmount) == 0 && Objects.equals(executionEntityName, that.executionEntityName) && Objects.equals(instrumentName, that.instrumentName) && Objects.equals(instrumentClassification, that.instrumentClassification) && Objects.equals(currency, that.currency) && Objects.equals(datestamp, that.datestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, executionEntityName, instrumentName, instrumentClassification, quantity, price, currency, datestamp, netAmount);
    }
}
