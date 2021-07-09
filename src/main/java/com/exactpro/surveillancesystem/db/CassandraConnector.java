package com.exactpro.surveillancesystem.db;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.exactpro.surveillancesystem.entities.Instrument;
import com.exactpro.surveillancesystem.entities.Transaction;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.*;
import static java.lang.Long.*;

public class CassandraConnector {
	private final InetSocketAddress address;

	public CassandraConnector(String host, Integer port) {
		this.address = new InetSocketAddress(host, port);
	}

	public void saveTransaction(Transaction transaction) {

	}

	public double toDouble(String str){
		double d;
		d = parseDouble(str);
		return d;
	}

	public Instant convertDateFormatTransactions(String oldDateString) throws ParseException {
		final String OLD_FORMAT = "dd-MM-yyyy hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(oldDateString);
		Timestamp timestamp = new java.sql.Timestamp(d.getTime());
		return timestamp.toInstant();
	}

	public LocalDate convertDateFormatPrices(String dateConvert) throws ParseException {
		final String OLD_FORMAT = "dd-MM-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date d = sdf.parse(dateConvert);
		Timestamp timestamp = new java.sql.Timestamp(d.getTime());
		return timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void saveInstrument(Instrument instrument) throws ParseException {
		CqlSession session = new CqlSessionBuilder().addContactPoint(new InetSocketAddress("localhost", 9042))
				.withLocalDatacenter("datacenter1")
				.build();
		for (String[] TransactionsToDB: instrument.getTransactions()) {
			PreparedStatement preparedStatement = session.prepare("INSERT INTO ayat.transactions (transaction_ID, " +
					"execution_entity_name, instrument_name,instrument_classification,quantity," +
					"price,currency,datestamp,net_amount) VALUES (?,?,?,?,?,?,?,?,?);");

			BoundStatement boundStatement = preparedStatement.bind(Long.parseLong(TransactionsToDB[0]),
					TransactionsToDB[1],
					TransactionsToDB[2],
					TransactionsToDB[3],
					Integer.parseInt(TransactionsToDB[4]),
					toDouble(TransactionsToDB[5]),
					TransactionsToDB[6],
					convertDateFormatTransactions(TransactionsToDB[7]),
					toDouble(TransactionsToDB[8]));
			session.execute(boundStatement);
		}
		for(String[] PricesToDB : instrument.getPrices()) {
			PreparedStatement preparedStatement = session.prepare("INSERT INTO ayat.prices (instrument_name, " +
					"date, currency, avg_price,net_amount_per_day) VALUES (?,?,?,?,?);");

			BoundStatement boundStatement = preparedStatement.bind(PricesToDB[0],
					convertDateFormatPrices(PricesToDB[1]),
					PricesToDB[2],
					toDouble(PricesToDB[3]),
					toDouble(PricesToDB[4]));
			session.execute(boundStatement);
		}
		session.close();
	}

	private ResultSet sessionWrapper(Function<CqlSession, ResultSet> action) {
		try (CqlSession session = new CqlSessionBuilder()
				.addContactPoint(address)
				.withLocalDatacenter("datacenter1")
				.build()) {
			return action.apply(session);
		}
	}

	public void checkingDb() {
		Logger logger = org.slf4j.LoggerFactory.getLogger(LoggingHandler.class);
		try{
			CqlSession session = new CqlSessionBuilder().addContactPoint(new InetSocketAddress("localhost", 9042))
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
			logger.error ("An error occurred when creating the database", e);
		}
	}
}
