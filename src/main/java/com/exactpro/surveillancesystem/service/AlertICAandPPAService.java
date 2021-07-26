package com.exactpro.surveillancesystem.service;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.Alert;

import java.util.Collection;


public class AlertICAandPPAService implements Service<Alert>{
    private CassandraConnector cassandraConnector;

    public AlertICAandPPAService(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    @Override
    public void saveAll(Collection<Alert> alertsICA) {
        String query = "INSERT INTO ayat.alerts (" +
                "alert_ID, alert_type, Description, affected_transactions_count) " +
                "VALUES (?,?,?,?);";
        for (Alert alertICA : alertsICA) {
            PreparedStatement preparedStatement = cassandraConnector.preparedStatement(query);
            BoundStatement boundStatement = preparedStatement.bind(alertICA.getAlertId(),
                    alertICA.getAlertType(),
                    alertICA.getDescription(),
                    alertICA.getAffectedTransactionsCount());
            cassandraConnector.execute(boundStatement);
        }

    }

}
