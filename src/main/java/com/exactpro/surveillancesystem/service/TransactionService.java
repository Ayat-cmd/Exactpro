package com.exactpro.surveillancesystem.service;

import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.Transaction;

import java.util.Collection;

public class TransactionService  implements Service<Transaction>{
    private final CassandraConnector cassandraConnector;


    public TransactionService(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    @Override
    public void saveAll(Collection<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            PreparedStatement preparedStatement = cassandraConnector.preparedStatement("INSERT INTO ayat.transactions (transaction_ID, " +
                    "execution_entity_name, instrument_name,instrument_classification,quantity," +
                    "price,currency,datestamp,net_amount) VALUES (?,?,?,?,?,?,?,?,?);");

            BoundStatement boundStatement = preparedStatement.bind(transaction.getTransactionID(),
                    transaction.getExecutionEntityName(),
                    transaction.getInstrumentName(),
                    transaction.getInstrumentClassification(),
                    transaction.getQuantity(),
                    transaction.getPrice(),
                    transaction.getCurrency(),
                    transaction.getDatestamp(),
                    transaction.getNetAmount());
            cassandraConnector.execute(boundStatement);
        }
    }
}
