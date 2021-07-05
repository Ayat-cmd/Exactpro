package CSV;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvException;

public class Main {
    public static void main(String[] args){

        File file = new File("src/main/resources/ex.csv");

        OpenCSV openCSV = new OpenCSV();

        List<String[]> collectedData = null;
        try {
            collectedData = openCSV.read(file);
        } catch (IOException e) {
            System.err.println("Ошибка! (IOException)");
        } catch (CsvException e) {
            System.err.println("Ошибка! (CsvException)");
        }

        for(String[] arrStr : collectedData){
            for(String str : arrStr){
                System.out.print(str+" ");
            }
            System.out.println("");
        }
    }
}
