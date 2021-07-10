package com.exactpro.surveillancesystem.entities;

import java.time.Instant;

public class Transaction {
    private long transaction_ID;
    private String execution_entity_name;
    private String instrument_name;
    private String instrument_classification;
    private int quantity;
    private double price;
    private String currency;
    private Instant datestamp;
    private double net_amount;

    public long getTransaction_ID() {
        return transaction_ID;
    }

    public void setTransaction_ID(long transaction_ID) {
        this.transaction_ID = transaction_ID;
    }

    public String getExecution_entity_name() {
        return execution_entity_name;
    }

    public void setExecution_entity_name(String execution_entity_name) {
        this.execution_entity_name = execution_entity_name;
    }

    public String getInstrument_name() {
        return instrument_name;
    }

    public void setInstrument_name(String instrument_name) {
        this.instrument_name = instrument_name;
    }

    public String getInstrument_classification() {
        return instrument_classification;
    }

    public void setInstrument_classification(String instrument_classification) {
        this.instrument_classification = instrument_classification;
    }

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

    public double getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(double net_amount) {
        this.net_amount = net_amount;
    }
}
