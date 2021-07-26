package com.exactpro.surveillancesystem.service;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.Alert;

import java.util.Collection;

public class AlertPPAService implements Service<Alert>{
    private CassandraConnector connector;
    public AlertPPAService(CassandraConnector connector) {
        this.connector = connector;
    }

    @Override
    public void saveAll(Collection<Alert> alertPPAS) {
        String query = "INSERT INTO ayat.alerts (" +
                "alert_ID, alert_type, Description, affected_transactions_count) " +
                "VALUES (?,?,?,?);";
        for (Alert alertPPA : alertPPAS) {
            PreparedStatement preparedStatement = connector.preparedStatement(query);
            BoundStatement boundStatement = preparedStatement.bind(alertPPA.getAlertId(),
                    alertPPA.getAlertType(),
                    alertPPA.getDescription(),
                    alertPPA.getAffectedTransactionsCount());
            connector.execute(boundStatement);
        }
    }
}
