package com.exactpro.surveillancesystem.service;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.AlertICA;

import java.util.Collection;


public class AlertService implements Service<AlertICA>{
    private CassandraConnector cassandraConnector;

    public AlertService(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    @Override
    public void saveAll(Collection<AlertICA> alertsICA) {
        String query = "INSERT INTO ayat.alerts (" +
                "alert_ID, alert_type, Description, affected_transactions_count) " +
                "VALUES (?,?,?,?);";
        for (AlertICA alertICA : alertsICA) {
            PreparedStatement preparedStatement = cassandraConnector.preparedStatement(query);
            BoundStatement boundStatement = preparedStatement.bind(alertICA.getAlertID(), alertICA.getAlertType(), alertICA.getDescription(), alertICA.getAffectedTransactionsCount());
            cassandraConnector.execute(boundStatement);
        }

    }

}
