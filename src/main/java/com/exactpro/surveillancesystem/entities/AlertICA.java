package com.exactpro.surveillancesystem.entities;

import org.springframework.stereotype.Component;

@Component
public class AlertICA {
    private String alertID;
    private String alertType;
    private String descriptionEntityName;
    private String descriptionInstrumentName;
    private Integer affectedTransactionsCount;

    public String getAlertID() {
        return alertID;
    }

    public void setAlertID(String alertID) {
        this.alertID = alertID;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getDescription() {
        return "Currency field is incorrect for the combination of " + descriptionEntityName + " and " + descriptionInstrumentName;
    }

    public void setDescription(String descriptionEntityName, String descriptionInstrumentName) {
        this.descriptionEntityName = descriptionEntityName;
        this.descriptionInstrumentName = descriptionInstrumentName;
    }

    public Integer getAffectedTransactionsCount() {
        return affectedTransactionsCount;
    }

    public void setAffectedTransactionsCount(Integer affectedTransactionsCount) {
        this.affectedTransactionsCount = affectedTransactionsCount;
    }
}
