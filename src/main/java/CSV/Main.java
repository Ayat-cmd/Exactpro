package CSV;

import com.opencsv.exceptions.CsvException;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Logger logger = org.slf4j.LoggerFactory.getLogger(LoggingHandler.class);
        OpenCSV openCSV = new OpenCSV();
        List<String[]> loadDataTransactionsToDB;
        List<String[]> loadDataPricesToDB;
        ArgumentsReader argumentsReader = new ArgumentsReader();
        File transactionFile = argumentsReader.getInputDataTransaction();
        File priceFile = argumentsReader.getInputDataPrice();
        try {
            openCSV.read(transactionFile, priceFile);
            loadDataTransactionsToDB = openCSV.getTransactions();
            loadDataPricesToDB = openCSV.getPrices();
            LoadFileToDB loadFileToDB = new LoadFileToDB(loadDataTransactionsToDB, loadDataPricesToDB);
            loadFileToDB.checkingDb();
            loadFileToDB.csvToDB();
        } catch (IOException e) {
            logger.error("An error occurred while reading file! IOException", e);
        } catch (CsvException e) {
            logger.error("An error occurred while reading file! CsvException", e);
        } catch (ParseException e) {
            logger.error("An error occurred while reading file! CsvException", e);
        }
    }
}
