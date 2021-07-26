package com.exactpro.surveillancesystem.controller;

import com.exactpro.surveillancesystem.entities.Alert;
import com.exactpro.surveillancesystem.entities.Transaction;
import com.exactpro.surveillancesystem.factories.AlertsFactory;
import com.exactpro.surveillancesystem.factories.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AlertsController {
    private final AlertsFactory alertsFactory;
    private final TransactionFactory transactionFactory;
    private List<Transaction> transactions;
    private List<Alert> alerts;

    @Autowired
    AlertsController(AlertsFactory alertsFactory, TransactionFactory transactionFactory) {
        this.alertsFactory = alertsFactory;
        this.transactionFactory = transactionFactory;
        alertIdToTransactions();
    }

    private void alertIdToTransactions() {
        transactions = transactionFactory.getAlertsTransactions();
        alerts = alertsFactory.getAlerts();
        int i = 0;
        for(Transaction transaction : transactions) {
            transaction.setAlertId(alerts.get(i).getAlertId());
            i++;
        }
    }

    @GetMapping("/api/alerts")
    public ResponseEntity<List<Alert>> showAlert() {
        return alerts != null &&  !alerts.isEmpty()
                ? new ResponseEntity<>(alerts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/alertsTransactions")
    public ResponseEntity<List<Transaction>> showAlertsTransactions() {

        return transactions != null && !transactions.isEmpty()
                ? new ResponseEntity<>(transactions, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/{transactionID}")
    public List<Transaction> getAlertId(@PathVariable String transactionID) {
        List<Transaction> transactions = transactionFactory.getAlertsTransactions();
        String id;
        List<Transaction> idTransactions = new ArrayList<>();
        for(Transaction transaction : transactions) {
            id = String.valueOf(transaction.getTransactionID());
            if(id.equals(transactionID)) {
                idTransactions.add(transaction);
            }
        }

        return idTransactions;
    }
}
