package CSV;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import static com.opencsv.ICSVWriter.DEFAULT_QUOTE_CHARACTER;
import static com.opencsv.ICSVWriter.DEFAULT_SEPARATOR;

public class OpenCSV {
    private char separator;
    private char quote;
    private List<String[]> getTransactionsToDB;
    private List<String[]> getPricesToDB;

    public OpenCSV() {
        this(DEFAULT_SEPARATOR);
    }

    public OpenCSV(char separator) {
        this(separator, DEFAULT_QUOTE_CHARACTER);
    }

    public OpenCSV(char separator, char quote) {
        this.separator = separator;
        this.quote = quote;
    }

    public void write(List<String[]> data, File file) throws IOException {
        try (ICSVWriter writer = new CSVWriterBuilder(
                new FileWriter(file))
                .withSeparator(separator)
                .withQuoteChar(quote)
                .build()) {
            writer.writeAll(data);
        }
    }

    public void read(File transactionFile, File pricesFile) throws IOException, CsvException {
        List<String[]> date;
        CSVParser csvParser = new CSVParserBuilder().withSeparator(separator).withQuoteChar(quote).build();
        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader(transactionFile)).withSkipLines(1).withCSVParser(csvParser).build()){
            date = csvReader.readAll();
            getTransactionsToDB = date;
        }

        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader(pricesFile)).withSkipLines(1).withCSVParser(csvParser).build()){
            date = csvReader.readAll();
            getPricesToDB = date;
        }
    }

    public List<String[]> getTransactions() {
        return getTransactionsToDB;
    }

    public List<String[]> getPrices() {
        return getPricesToDB;
    }
}