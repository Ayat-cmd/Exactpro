package com.exactpro.surveillancesystem.controller;

import com.exactpro.surveillancesystem.entities.AlertICA;
import com.exactpro.surveillancesystem.factories.AlertsFactoryICA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertsController {
    private final AlertsFactoryICA alertsFactoryICA;

    @Autowired
    AlertsController(AlertsFactoryICA alertsFactoryICA) {
        this.alertsFactoryICA = alertsFactoryICA;
    }

    @GetMapping("/get/alerts")
    public ResponseEntity<List<AlertICA>> showAlert() {
        List<AlertICA> alertICA = alertsFactoryICA.readAll();

        return alertICA != null &&  !alertICA.isEmpty()
                ? new ResponseEntity<>(alertICA, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
