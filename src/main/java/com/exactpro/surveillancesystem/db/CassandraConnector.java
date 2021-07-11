package com.exactpro.surveillancesystem.db;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.exactpro.surveillancesystem.entities.Prices;
import com.exactpro.surveillancesystem.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class CassandraConnector {
	private final InetSocketAddress address;
	private final static Logger loggerCassandraConnection = LoggerFactory.getLogger(CassandraConnector.class);

	public CassandraConnector(String host, Integer port) {
		this.address = new InetSocketAddress(host, port);
	}

	public void saveTransaction(Collection<Transaction> transactions) {
			sessionWrapper(session -> {
			List<ResultSet> results = new ArrayList<>();
			for (Transaction transaction : transactions) {
				PreparedStatement preparedStatement = session.prepare("INSERT INTO ayat.transactions (transaction_ID, " +
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
						session.execute(boundStatement);
				results.add(session.execute(boundStatement));
			}
			return results;
		});
	}

	public void savePrices(Collection<Prices> prices) {
		sessionWrapper(session -> {
			List<ResultSet> results = new ArrayList<>();
			for (Prices price : prices) {
				PreparedStatement preparedStatement = session.prepare("INSERT INTO ayat.prices (instrument_name, " +
						"date, currency, avg_price,net_amount_per_day) VALUES (?,?,?,?,?);");

				BoundStatement boundStatement = preparedStatement.bind(price.getInstrumentName(),
						price.getDate().atZone(ZoneId.systemDefault()).toLocalDate(),
						price.getCurrency(),
						price.getAvgPrice(),
						price.getNetAmountPerDay());
				session.execute(boundStatement);
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

	public void initializationDB() {
		try{
			CqlSession session = new CqlSessionBuilder().addContactPoint(address)
					.withLocalDatacenter("datacenter1")
					.build();
			session.execute("CREATE KEYSPACE IF NOT EXISTS ayat WITH REPLICATION = {" +
					"'class' : 'SimpleStrategy', 'replication_factor' : 1 }; ");
			session.execute("CREATE TABLE IF NOT EXISTS ayat.transactions (" +
					"transaction_ID bigint PRIMARY KEY ," +
					"execution_entity_name text," +
					"instrument_name text," +
					"instrument_classification text, " +
					"quantity int," +
					"price double," +
					"currency text," +
					"datestamp timestamp," +
					"net_amount double);");
			session.execute("CREATE TABLE IF NOT EXISTS ayat.prices (" +
					"instrument_name text PRIMARY KEY," +
					"date date," +
					"currency text," +
					"avg_price double," +
					"net_amount_per_day double);");
			session.close();
		}catch (Exception e){
			loggerCassandraConnection.error ("An error occurred when creating the database", e);
		}
	}
}
