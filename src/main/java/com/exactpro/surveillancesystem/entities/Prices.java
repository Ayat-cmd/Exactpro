package com.exactpro.surveillancesystem.entities;

import java.time.Instant;

public class Prices {
    private String instrumentName;
    private Instant date;
    private String currency;
    private double avgPrice;
    private double netAmountPerDay;

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getNetAmountPerDay() {
        return netAmountPerDay;
    }

    public void setNetAmountPerDay(double netAmountPerDay) {
        this.netAmountPerDay = netAmountPerDay;
    }

}
