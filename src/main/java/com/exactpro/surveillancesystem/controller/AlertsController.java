package com.exactpro.surveillancesystem.controller;

import com.exactpro.surveillancesystem.analyze.AnalyzeICA;
import com.exactpro.surveillancesystem.entities.AlertICA;
import com.exactpro.surveillancesystem.entities.Transaction;
import com.exactpro.surveillancesystem.factories.AlertsFactoryICA;
import com.exactpro.surveillancesystem.factories.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertsController {
    private final AlertsFactoryICA alertsFactoryICA;
    private final TransactionFactory transactionFactory;

    @Autowired
    AlertsController(AlertsFactoryICA alertsFactoryICA, TransactionFactory transactionFactory) {
        this.alertsFactoryICA = alertsFactoryICA;
        this.transactionFactory = transactionFactory;
    }

    @GetMapping("/api/alerts")
    public ResponseEntity<List<AlertICA>> showAlert() {
        List<AlertICA> alertICA = alertsFactoryICA.readAll();

        return alertICA != null &&  !alertICA.isEmpty()
                ? new ResponseEntity<>(alertICA, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/alertsTransactions")
    public ResponseEntity<List<Transaction>> showAlertsTransactions() {
        List<Transaction> transactions = transactionFactory.getAlertsTransactions();

        return transactions != null && !transactions.isEmpty()
                ? new ResponseEntity<>(transactions, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
