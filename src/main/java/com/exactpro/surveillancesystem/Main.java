package com.exactpro.surveillancesystem;

import com.exactpro.surveillancesystem.csv.CSVManager;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.Instrument;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException, CsvException {
		ArgumentsReader argumentsReader = new ArgumentsReader(args);
		File transactionFile = argumentsReader.getInputData(0);
		File priceFile = argumentsReader.getInputData(1);
//		File transactionFile = new File("src/main/resources/transactions_current_datetime.csv");
//		File priceFile = new File("src/main/resources/price_file_datestamp.csv");
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
			e.printStackTrace();
		}
	}
}
