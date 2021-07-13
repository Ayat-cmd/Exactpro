package com.exactpro.surveillancesystem.alerts;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.*;
import com.exactpro.surveillancesystem.entities.AlertsICA;
import com.exactpro.surveillancesystem.entities.Prices;
import com.exactpro.surveillancesystem.factories.AlertsFactoryICA;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

public class IncorrectCurrencyAlert {
    private String currencyICA;
    private String executionEntityName;
    private String instrumentName;
    private InetSocketAddress address;
    private boolean latinLetters;
    private ArrayList<Integer> affectedTransactionsCount;
    private int idExecutionEntityNameAlerts;
    private int idInstrumentNameAlerts;
    private Map<Integer, String> entityNameAlerts;
    private Map<Integer, String> instrumentNameAlerts;
    private CqlSession session;

    public IncorrectCurrencyAlert(String host, Integer port){
        this.address = new InetSocketAddress(host, port);
        this.idExecutionEntityNameAlerts = 0;
        this.idInstrumentNameAlerts = 0;
        this.affectedTransactionsCount = new ArrayList<>();
        this.entityNameAlerts = new HashMap<>();
        this.instrumentNameAlerts = new HashMap<>();
        this.session = new CqlSessionBuilder().addContactPoint(this.address)
                .withLocalDatacenter("datacenter1")
                .build();
    }


    public <T> List<AlertsICA> ICA(AlertsFactoryICA alertsFactoryICA) {
        String query = "select execution_entity_name, instrument_name, currency from ayat.transactions";
        ResultSet resultSet = session.execute(query);

        for (Row alerts : resultSet) {
            currencyICA = alerts.getString("currency");
            executionEntityName  =alerts.getString("execution_entity_name");
            instrumentName = alerts.getString("instrument_name");
            latinLetters = currencyICA.matches("^[a-zA-Z0-9]+$");
            if(currencyICA.isEmpty() || currencyICA.length() != 3 || !latinLetters) {
                if(entityNameAlerts.isEmpty() || !entityNameAlerts.containsValue(executionEntityName) ||
                instrumentNameAlerts.isEmpty() || !instrumentNameAlerts.containsValue(instrumentName)) {
                    entityNameAlerts.put(idExecutionEntityNameAlerts, executionEntityName);
                    instrumentNameAlerts.put(idInstrumentNameAlerts, instrumentName);
                    affectedTransactionsCount.add(1);
                    idExecutionEntityNameAlerts++;
                    idInstrumentNameAlerts++;
                }else{
                    findAlerts();
                }
            }
        }
        return alertsFactoryICA.createEntities(entityNameAlerts, instrumentNameAlerts, affectedTransactionsCount);
    }

    public void findAlerts() {
        for (int findIdValue : entityNameAlerts.keySet()) {
            if (executionEntityName.equals(entityNameAlerts.get(findIdValue)) && instrumentName.equals(instrumentNameAlerts.get(findIdValue))) {
                    affectedTransactionsCount.set(findIdValue, affectedTransactionsCount.get(findIdValue)+1);
            }
        }
    }

    public void saveAlertsICA(Collection<AlertsICA> alertsICA) {
        String query = "INSERT INTO ayat.alerts (" +
                "alert_ID, alert_type, Description, affected_transactions_count) " +
                "VALUES (?,?,?,?);";
        sessionWrapper(session -> {
            List<ResultSet> results = new ArrayList<>();
            for (AlertsICA alertICA : alertsICA) {
                PreparedStatement preparedStatement = session.prepare(query);
                BoundStatement boundStatement = preparedStatement.bind(alertICA.getAlertID(), alertICA.getAlertType(), alertICA.getDescription(), alertICA.getAffectedTransactionsCount());
                results.add(session.execute(boundStatement));
            }
            return results;
        });
    }
    private List<ResultSet> sessionWrapper(Function<CqlSession, List<ResultSet>> action) {
        try (CqlSession session = new CqlSessionBuilder()
                .addContactPoint(address)
                .withLocalDatacenter("datacenter1")
                .build()) {
            return action.apply(session);
        }
    }

    public File createFile() throws IOException {
        File file = new File("src/main/resources/alerts.csv");
//create the file.
        if (file.createNewFile()){
            System.out.println("File is created!");
        }
        else{
            System.out.println("File already exists.");
        }
        return file;
    }

    public List<String[]> copy(List<AlertsICA> oldAlertsICA) {
        List<String[]> newAlertsICA = new ArrayList<>();
        newAlertsICA.add(new String[]{"Alert ID","Execution Entity Name","Description","Affected transactions count"});
        for (AlertsICA newFormat : oldAlertsICA) {
            newAlertsICA.add(new String[]{newFormat.getAlertID(), newFormat.getAlertType(), newFormat.getDescription(), newFormat.getAffectedTransactionsCount().toString()});
        }
        return newAlertsICA;
    }

    public void close(){
        session.close();
    }
}
