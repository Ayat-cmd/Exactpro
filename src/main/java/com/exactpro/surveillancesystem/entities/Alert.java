package com.exactpro.surveillancesystem.entities;

public class Alert {
    private String alertId;
    private String alertType;
    private String description;
    private Integer affectedTransactionsCount;

    public String getAlertId() {
        return alertId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getAffectedTransactionsCount() {
        return affectedTransactionsCount;
    }

    public void setAffectedTransactionsCount(Integer affectedTransactionsCount) {
        this.affectedTransactionsCount = affectedTransactionsCount;
    }
}
