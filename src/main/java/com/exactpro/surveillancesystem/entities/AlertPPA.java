package com.exactpro.surveillancesystem.entities;

public class AlertPPA {
    private String alertId;
    private String alertType;
    private String descriptionExecutionEntityName;
    private String descriptionInstrumentName;
    private String descriptionCurrency;
    private double descriptionCurrentPercent;
    private Integer affectedTransactionsCount;

    public String getAlertId() {
        return alertId;
    }

    public double getDescriptionCurrentPercent() {
        return descriptionCurrentPercent;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getDescription() {
        return "Potential pumping price activity has been noticed for the following combination of "+descriptionExecutionEntityName +", "+descriptionInstrumentName+" and "+descriptionCurrency+" where an average price is greater than previous more than 50% and is "+descriptionCurrentPercent;
    }

    public void setDescriptionExecutionEntityName(String descriptionExecutionEntityName) {
        this.descriptionExecutionEntityName = descriptionExecutionEntityName;
    }

    public void setDescriptionInstrumentName(String descriptionInstrumentName) {
        this.descriptionInstrumentName = descriptionInstrumentName;
    }

    public void setDescriptionCurrency(String descriptionCurrency) {
        this.descriptionCurrency = descriptionCurrency;
    }

    public void setDescriptionCurrentPercent(double descriptionCurrentPercent) {
        this.descriptionCurrentPercent = descriptionCurrentPercent;
    }

    public Integer getAffectedTransactionsCount() {
        return affectedTransactionsCount;
    }

    public void setAffectedTransactionsCount(Integer affectedTransactionsCount) {
        this.affectedTransactionsCount = affectedTransactionsCount;
    }
}
