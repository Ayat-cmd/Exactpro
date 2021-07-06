package CSV;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
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

        date=null;
        try(CSVReader csvReader = new CSVReaderBuilder(new FileReader(pricesFile)).withSkipLines(1).withCSVParser(csvParser).build()){
            date = csvReader.readAll();
            getPricesToDB = date;
        }
    }

    public double toDouble(String a){
        BigDecimal bigDecimal = new BigDecimal(a);
        bigDecimal = bigDecimal.setScale(2,RoundingMode.HALF_UP );
        return bigDecimal.doubleValue();
    }

    public void csvToDB() {
        CqlSession session = new CqlSessionBuilder().addContactPoint(new InetSocketAddress("localhost", 9042))
                .withLocalDatacenter("datacenter1")
                .build();
        for (String[] TransactionsToDB: getTransactionsToDB) {
            PreparedStatement preparedStatement = session.prepare("INSERT INTO ayat.transactions (transaction_ID, " +
                    "execution_entity_name, instrument_name,instrument_classification,quantity," +
                    "price,currency,datestamp,net_amount) VALUES (?,?,?,?,?,?,?,?,?);");

            BoundStatement boundStatement = preparedStatement.bind(Long.parseLong(TransactionsToDB[0]),TransactionsToDB[1],TransactionsToDB[2],TransactionsToDB[3],
                    Integer.parseInt(TransactionsToDB[4]),toDouble(TransactionsToDB[5]),TransactionsToDB[6],TransactionsToDB[7],toDouble(TransactionsToDB[8]));
            session.execute(boundStatement);
        }
        for(String[] PricesToDB : getPricesToDB) {
            PreparedStatement preparedStatement = session.prepare("INSERT INTO ayat.prices (instrument_name, " +
                    "date, currency, avg_price,net_amount_per_day) VALUES (?,?,?,?,?);");

            BoundStatement boundStatement = preparedStatement.bind(PricesToDB[0], PricesToDB[1], PricesToDB[2], toDouble(PricesToDB[3]),toDouble(PricesToDB[4]));
            session.execute(boundStatement);
        }
        session.close();
    }

    public void checkingDb() {
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
                    "datestamp text," +
                    "net_amount double);");
            session.execute("CREATE TABLE IF NOT EXISTS ayat.prices (" +
                    "instrument_name text PRIMARY KEY," +
                    "date text," +
                    "currency text," +
                    "avg_price double," +
                    "net_amount_per_day double);");
            session.close();
        }catch (Exception e){
            System.err.println("Ошибка! Cheking data base");
        }
    }

}