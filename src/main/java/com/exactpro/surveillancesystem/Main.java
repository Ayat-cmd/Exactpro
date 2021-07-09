package com.exactpro.surveillancesystem;

import com.exactpro.surveillancesystem.csv.CSVManager;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.Instrument;
import com.opencsv.exceptions.CsvException;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException, CsvException {
		Logger logger = getLogger(LoggingHandler.class);
		ArgumentsReader argumentsReader = new ArgumentsReader(args);
		File transactionFile = argumentsReader.getTransactionsData();
		File priceFile = argumentsReader.getPriceData();
		CSVManager csvManager = new CSVManager();

		// TODO: convert raw data to entities
		List<String[]> dataFromCSVTransactions = csvManager.read(transactionFile);
		List<String[]> dataFromCSVPrices = csvManager.read(priceFile);
		CassandraConnector connector = new CassandraConnector("localhost", 9042);
		// TODO: save your entities
		Instrument instrument = new Instrument();
		instrument.setTransactions(dataFromCSVTransactions);
		instrument.setPrices(dataFromCSVPrices);
		connector.checkingDb();
		try {
			connector.saveInstrument(instrument);
		} catch (ParseException e) {
			logger.error("date conversion error");
		}
	}
}
