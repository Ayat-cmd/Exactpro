package com.exactpro.surveillancesystem.csv;

import com.exactpro.surveillancesystem.entities.Alert;
import com.exactpro.surveillancesystem.factories.EntityFactory;
import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.text.ParseException;
import java.util.List;

public class CSVManager{
	private final Character separator;
	private final Character quote;


	public CSVManager() {
		this(ICSVWriter.DEFAULT_SEPARATOR, ICSVWriter.DEFAULT_QUOTE_CHARACTER);
	}

	public CSVManager(Character separator, Character quote) {
		this.separator = separator;
		this.quote = quote;
	}

	public void write(List<Alert> alerts, File file) throws IOException {
		PrintWriter pw = new PrintWriter(file);
		pw.println("Alert ID, Alert type, Description, Affected transactions count");
		for (Alert alert: alerts) {
			pw.println(alert.getAlertId()+","+alert.getAlertType()+","+alert.getDescription()+","+alert.getAffectedTransactionsCount());
		}
		pw.close();
	}

	public <T> List<T> read(File file, EntityFactory<T> entityFactory) throws IOException, CsvException, ParseException {
		return entityFactory.createEntities(read(file, 1));
	}

	public List<String[]> read(File file, int skipLines) throws IOException, CsvException {
		CSVParser csvParser = new CSVParserBuilder()
				.withSeparator(separator)
				.withQuoteChar(quote)
				.build();
		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
				.withSkipLines(skipLines)
				.withCSVParser(csvParser)
				.build();) {
			return csvReader.readAll();
		}
	}

}
