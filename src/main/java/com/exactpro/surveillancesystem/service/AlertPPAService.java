package com.exactpro.surveillancesystem.service;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.AlertICA;
import com.exactpro.surveillancesystem.entities.AlertPPA;

import java.util.Collection;

public class AlertPPAService implements Service<AlertPPA>{
    private CassandraConnector connector;
    public AlertPPAService(CassandraConnector connector) {
        this.connector = connector;
    }

    @Override
    public void saveAll(Collection<AlertPPA> alertPPAS) {
        String query = "INSERT INTO ayat.alertsPPA (" +
                "alert_ID, alert_type, Description, affected_transactions_count) " +
                "VALUES (?,?,?,?);";
        for (AlertPPA alertPPA : alertPPAS) {
            PreparedStatement preparedStatement = connector.preparedStatement(query);
            BoundStatement boundStatement = preparedStatement.bind(alertPPA.getAlertId(),
                    alertPPA.getAlertType(),
                    alertPPA.getDescription(),
                    alertPPA.getAffectedTransactionsCount());
            connector.execute(boundStatement);
        }
    }
}
