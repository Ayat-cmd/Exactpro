package com.exactpro.surveillancesystem.csv;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVManager {
	private final Character separator;
	private final Character quote;


	public CSVManager() {
		this(ICSVWriter.DEFAULT_SEPARATOR, ICSVWriter.DEFAULT_QUOTE_CHARACTER);
	}

	public CSVManager(Character separator, Character quote) {
		this.separator = separator;
		this.quote = quote;
	}


	public void write(List<String[]> data, File file) throws IOException {
		try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(file))
				.withSeparator(separator)
				.withQuoteChar(quote)
				.build()
		) {
			writer.writeAll(data);
		}
	}

	public List<String[]> read(File file) throws IOException, CsvException {
		return read(file, 1);
	}

	public List<String[]> read(File file, int skipLines) throws IOException, CsvException {
		CSVParser csvParser = new CSVParserBuilder()
				.withSeparator(separator)
				.withQuoteChar(quote)
				.build();
		try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
				.withSkipLines(skipLines)
				.withCSVParser(csvParser)
				.build();
		) {
			return csvReader.readAll();
		}
	}
}
