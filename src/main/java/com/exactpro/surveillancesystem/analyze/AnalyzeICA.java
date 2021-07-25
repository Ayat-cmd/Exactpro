package com.exactpro.surveillancesystem.analyze;

import com.datastax.oss.driver.api.core.cql.*;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.AlertICA;
import com.exactpro.surveillancesystem.entities.Transaction;
import com.exactpro.surveillancesystem.factories.EntityFactory;
import com.exactpro.surveillancesystem.factories.TransactionFactory;

import static com.exactpro.surveillancesystem.factories.AlertsFactoryICA.createEntities;

import java.text.ParseException;
import java.util.*;

public class AnalyzeICA {
//    private String currencyICA;
//    private String executionEntityName;
//    private String instrumentName;
    private boolean latinLetters;
    private ArrayList<Integer> affectedTransactionsCount;
    private int idExecutionEntityNameAlerts;
    private int idInstrumentNameAlerts;
    private Map<Integer, String> entityNameAlerts;
    private Map<Integer, String> instrumentNameAlerts;
    private CassandraConnector cassandraConnector;

//    private List<Transaction> transactionAlerts;
    List<String[]> listTransactionAlerts;

    public AnalyzeICA(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
        this.idExecutionEntityNameAlerts = 0;
        this.idInstrumentNameAlerts = 0;
        this.affectedTransactionsCount = new ArrayList<>();
        this.entityNameAlerts = new HashMap<>();
        this.instrumentNameAlerts = new HashMap<>();
    }


    public List<AlertICA> ICA() throws ParseException {
        String query = "select transaction_ID, execution_entity_name,instrument_name,instrument_classification,quantity," +
                "price,currency,datestamp,net_amount from ayat.transactions";
        ResultSet resultSet = cassandraConnector.execute(query);
//        transactionAlerts = new ArrayList<>();
        listTransactionAlerts = new ArrayList<>();
        for (Row alerts : resultSet) {
            Transaction transaction = new Transaction();
            transaction.setTransactionID(alerts.getLong("transaction_ID"));
            transaction.setExecutionEntityName(alerts.getString("execution_entity_name"));
            transaction.setInstrumentName(alerts.getString("instrument_name"));
            transaction.setInstrumentClassification(alerts.getString("instrument_classification"));
            transaction.setQuantity(alerts.getInt("quantity"));
            transaction.setPrice(alerts.getDouble("price"));
            transaction.setCurrency(alerts.getString("currency"));
            transaction.setDatestamp(alerts.getInstant("datestamp"));
            transaction.setNetAmount(alerts.getDouble("net_amount"));
//            currencyICA = alerts.getString("currency");
//            executionEntityName = alerts.getString("execution_entity_name");
//            instrumentName = alerts.getString("instrument_name");
            latinLetters = transaction.getCurrency().matches("[a-zA-Z0-9]+");
            if (transaction.getCurrency().length() != 3 || !latinLetters) {
                if (!entityNameAlerts.containsValue(transaction.getExecutionEntityName()) || !instrumentNameAlerts.containsValue(transaction.getInstrumentName())) {
                    addAlerts(transaction);
//                    transactionAlerts.add(transaction);
                    listTransactionAlerts.add(new String[]{
                            String.valueOf(transaction.getTransactionID()),
                            transaction.getExecutionEntityName(),
                            transaction.getInstrumentName(),
                            transaction.getInstrumentClassification(),
                            String.valueOf(transaction.getQuantity()),
                            String.valueOf(transaction.getPrice()),
                            transaction.getCurrency(),
                            String.valueOf(transaction.getDatestamp()),
                            String.valueOf(transaction.getNetAmount())
                    });
                } else {
                    findAlerts(transaction);
                }
            }
        }
        TransactionFactory transactionFactory = new TransactionFactory();
//        new AlertTransactionFactoryICA(transactionAlerts);
        transactionFactory.createEntities(listTransactionAlerts);
        List<AlertICA> alertsICA = createEntities(entityNameAlerts, instrumentNameAlerts, affectedTransactionsCount);
        return alertsICA;
    }

    private void addAlerts(Transaction transaction) {
        entityNameAlerts.put(idExecutionEntityNameAlerts, transaction.getExecutionEntityName());
        instrumentNameAlerts.put(idInstrumentNameAlerts, transaction.getInstrumentName());
        affectedTransactionsCount.add(1);
        idExecutionEntityNameAlerts++;
        idInstrumentNameAlerts++;
    }

    private void findAlerts(Transaction transaction) {
        for (int findIdValue : entityNameAlerts.keySet()) {
            if (transaction.getExecutionEntityName().equals(entityNameAlerts.get(findIdValue)) && transaction.getInstrumentName().equals(instrumentNameAlerts.get(findIdValue))) {
                affectedTransactionsCount.set(findIdValue, affectedTransactionsCount.get(findIdValue) + 1);
            }
        }
    }
}
