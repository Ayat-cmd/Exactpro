package com.exactpro.surveillancesystem;

import com.exactpro.surveillancesystem.csv.CSVManager;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.Prices;
import com.exactpro.surveillancesystem.entities.Transaction;
import com.exactpro.surveillancesystem.factories.PricesFactory;
import com.exactpro.surveillancesystem.factories.TransactionFactory;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Main {
	private static Logger logger = getLogger(Main.class);

	public static void main(String[] args) throws IOException, CsvException, ParseException {
		ArgumentsReader argumentsReader = new ArgumentsReader(args);
		CSVManager csvManager = new CSVManager();
		TransactionFactory transactionFactory = new TransactionFactory();
		PricesFactory pricesFactory = new PricesFactory();

		File transactionFile = argumentsReader.getTransactionsData();
		File priceFile = argumentsReader.getPriceData();

		List<Transaction> transactions = csvManager.read(transactionFile, transactionFactory);
		List<Prices> prices = csvManager.read(priceFile, pricesFactory);
		CassandraConnector connector = new CassandraConnector("localhost", 9042);
		connector.initializationDB();
		connector.saveTransaction(transactions);
		connector.savePrices(prices);
	}
}
