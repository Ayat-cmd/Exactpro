package com.exactpro.surveillancesystem.db;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.String;
import java.net.InetSocketAddress;

public class CassandraConnector implements AutoCloseable{
	private final static Logger logger = LoggerFactory.getLogger(CassandraConnector.class);
	private CqlSession session;

	public CassandraConnector(String host, Integer port) {
		this.session = new CqlSessionBuilder().addContactPoint(new InetSocketAddress(host, port))
				.withLocalDatacenter("datacenter1")
				.build();
		initializationDB();
	}

	public PreparedStatement preparedStatement(String query) {
		return session.prepare(query);
	}

	public void execute(BoundStatement statement) {
		session.execute(statement);
	}
	
	public ResultSet execute(String query) {
		return session.execute(query);
	}

	private void initializationDB() {
		try{
			session.execute("DROP KEYSPACE IF EXISTS ayat;");
			session.execute("CREATE KEYSPACE IF NOT EXISTS ayat WITH REPLICATION = {" +
					"'class' : 'SimpleStrategy', 'replication_factor' : 1 }; ");
			session.execute("CREATE TABLE IF NOT EXISTS ayat.transactions (" +
					"transaction_ID bigint," +
					"execution_entity_name text," +
					"instrument_name text," +
					"instrument_classification text, " +
					"quantity int," +
					"price double," +
					"currency text," +
					"datestamp timestamp," +
					"net_amount double," +
					"PRIMARY KEY ((execution_entity_name,instrument_name), transaction_ID));");
			session.execute("CREATE TABLE IF NOT EXISTS ayat.prices (" +
					"instrument_name text," +
					"date date," +
					"currency text," +
					"avg_price double," +
					"net_amount_per_day double," +
					"PRIMARY KEY ((date, currency),instrument_name));");
			session.execute("CREATE TABLE IF NOT EXISTS ayat.alertsICA (" +
					"alert_ID text PRIMARY KEY," +
					"alert_type text," +
					"Description text," +
					"affected_transactions_count int);");
			session.execute("CREATE TABLE IF NOT EXISTS ayat.alertsPPA (" +
					"alert_ID text PRIMARY KEY," +
					"alert_type text," +
					"Description text," +
					"affected_transactions_count int);");
		}catch (Exception e){
			logger.error("not create keyspace Cassandra");
		}
	}

	public void close(){
		if (session != null) {
			session.close();
		}
	}
}
