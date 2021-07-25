package com.exactpro.surveillancesystem;

import com.exactpro.surveillancesystem.analyze.AnalyzeICA;
import com.exactpro.surveillancesystem.analyze.AnalyzePPA;
import com.exactpro.surveillancesystem.csv.CSVManager;
import com.exactpro.surveillancesystem.db.CassandraConnector;
import com.exactpro.surveillancesystem.entities.AlertICA;
import com.exactpro.surveillancesystem.entities.AlertPPA;
import com.exactpro.surveillancesystem.entities.Price;
import com.exactpro.surveillancesystem.entities.Transaction;
import com.exactpro.surveillancesystem.factories.PricesFactory;
import com.exactpro.surveillancesystem.factories.TransactionFactory;
import com.exactpro.surveillancesystem.service.AlertICAService;
import com.exactpro.surveillancesystem.service.AlertPPAService;
import com.exactpro.surveillancesystem.service.PriceService;
import com.exactpro.surveillancesystem.service.TransactionService;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static com.exactpro.surveillancesystem.utils.IOUtils.createFile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main(String[] args) throws IOException, CsvException, ParseException {

		ArgumentsReader argumentsReader = new ArgumentsReader(args);
		CSVManager csvManager = new CSVManager();
		TransactionFactory transactionFactory = new TransactionFactory();
		PricesFactory pricesFactory = new PricesFactory();

		File transactionFile = argumentsReader.getTransactionsFile();
		File priceFile = argumentsReader.getPricesFile();

		List<Transaction> transactions = csvManager.read(transactionFile, transactionFactory);
		List<Price> prices = csvManager.read(priceFile, pricesFactory);
		CassandraConnector connector = new CassandraConnector("localhost", 9042);
		TransactionService transactionService = new TransactionService(connector);
		transactionService.saveAll(transactions);
		PriceService priceService = new PriceService(connector);
		priceService.saveAll(prices);
		AnalyzeICA incorrectCurrencyAlert = new AnalyzeICA(connector);
		List<AlertICA> alertICA = incorrectCurrencyAlert.ICA();
		AlertICAService alertService = new AlertICAService(connector);
		alertService.saveAll(alertICA);
		File alertsICAFile = createFile("ICA");
		csvManager.writeICA(alertICA, alertsICAFile);

		AnalyzePPA ppa = new AnalyzePPA(connector);
		List<AlertPPA> alertPPA = ppa.PPA();
		AlertPPAService alertPPAService = new AlertPPAService(connector);
		alertPPAService.saveAll(alertPPA);
		File alertsPPAFile = createFile("PPA");
		csvManager.writePPA(alertPPA, alertsPPAFile);

		SpringApplication.run(Main.class, args);

		connector.close();
	}
}
