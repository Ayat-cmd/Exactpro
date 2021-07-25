package com.exactpro.surveillancesystem.analyze;

import com.datastax.oss.driver.api.core.cql.*;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.AlertICA;
import static com.exactpro.surveillancesystem.factories.AlertsFactoryICA.createEntities;

import java.text.ParseException;
import java.util.*;

public class AnalyzeICA {
    private String currencyICA;
    private String executionEntityName;
    private String instrumentName;
    private boolean latinLetters;
    private ArrayList<Integer> affectedTransactionsCount;
    private int idExecutionEntityNameAlerts;
    private int idInstrumentNameAlerts;
    private Map<Integer, String> entityNameAlerts;
    private Map<Integer, String> instrumentNameAlerts;
    private CassandraConnector cassandraConnector;

    public AnalyzeICA(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
        this.idExecutionEntityNameAlerts = 0;
        this.idInstrumentNameAlerts = 0;
        this.affectedTransactionsCount = new ArrayList<>();
        this.entityNameAlerts = new HashMap<>();
        this.instrumentNameAlerts = new HashMap<>();
    }


    public List<AlertICA> ICA() throws ParseException {
        String query = "select execution_entity_name, instrument_name, currency from ayat.transactions";
        ResultSet resultSet = cassandraConnector.execute(query);

        for (Row alerts : resultSet) {
            currencyICA = alerts.getString("currency");
            executionEntityName = alerts.getString("execution_entity_name");
            instrumentName = alerts.getString("instrument_name");
            latinLetters = currencyICA.matches("[a-zA-Z0-9]+");
            if (currencyICA.length() != 3 || !latinLetters) {
                if (!entityNameAlerts.containsValue(executionEntityName) || !instrumentNameAlerts.containsValue(instrumentName)) {
                    addAlerts();
                } else {
                    findAlerts();
                }
            }
        }
        List<AlertICA> alertsICA = createEntities(entityNameAlerts, instrumentNameAlerts, affectedTransactionsCount);
        return alertsICA;
    }

    private void addAlerts() {
        entityNameAlerts.put(idExecutionEntityNameAlerts, executionEntityName);
        instrumentNameAlerts.put(idInstrumentNameAlerts, instrumentName);
        affectedTransactionsCount.add(1);
        idExecutionEntityNameAlerts++;
        idInstrumentNameAlerts++;
    }

    private void findAlerts() {
        for (int findIdValue : entityNameAlerts.keySet()) {
            if (executionEntityName.equals(entityNameAlerts.get(findIdValue)) && instrumentName.equals(instrumentNameAlerts.get(findIdValue))) {
                affectedTransactionsCount.set(findIdValue, affectedTransactionsCount.get(findIdValue) + 1);
            }
        }
    }
}
