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
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        for(String[] s : collectedData){
            for(String fs : s){
                System.out.print(fs+" ");
            }
            System.out.println("");
        }
    }
}
