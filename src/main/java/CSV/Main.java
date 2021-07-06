package CSV;

import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File transactionFile = new File("src/main/resources/transactions_current_datetime.csv");
        File priceFile = new File("src/main/resources/price_file_datestamp.csv");
        OpenCSV openCSV = new OpenCSV();
        try {
            openCSV.read(transactionFile, priceFile);
            openCSV.checkingDb();
            openCSV.csvToDB();
        } catch (IOException e) {
            System.err.println("Ошибка! IOException");
        } catch (CsvException e) {
            System.err.println("Ошибка! CsvException");
        }
    }
}
