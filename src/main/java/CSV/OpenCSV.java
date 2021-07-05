package CSV;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import static com.opencsv.ICSVWriter.DEFAULT_QUOTE_CHARACTER;
import static com.opencsv.ICSVWriter.DEFAULT_SEPARATOR;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OpenCSV {

    private char separator;
    private char quote;

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

    public List<String[]> read(File file) throws IOException, CsvException {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(separator).withQuoteChar(quote).build();
        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader(file)).withSkipLines(1).withCSVParser(csvParser).build()){
            return csvReader.readAll();
        }
    }
}
