package com.exactpro.surveillancesystem.dao;

import com.exactpro.surveillancesystem.entities.AlertICA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlertsDAO {
    @Autowired
    private List<AlertICA> alertICA;

    @Autowired
    public AlertsDAO(List<AlertICA> alertICA) {
        this.alertICA = alertICA;
    }

    public List<AlertICA> getDAO(){
        return alertICA;
    }
}
