package com.exactpro.surveillancesystem.entities;

import java.time.Instant;

public class Prices {
    private String instrument_name;
    private Instant date;
    private String currency;
    private double avg_price;
    private double net_amount_per_day;

    public String getInstrument_name() {
        return instrument_name;
    }

    public void setInstrument_name(String instrument_name) {
        this.instrument_name = instrument_name;
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

    public double getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(double avg_price) {
        this.avg_price = avg_price;
    }

    public double getNet_amount_per_day() {
        return net_amount_per_day;
    }

    public void setNet_amount_per_day(double net_amount_per_day) {
        this.net_amount_per_day = net_amount_per_day;
    }

}
