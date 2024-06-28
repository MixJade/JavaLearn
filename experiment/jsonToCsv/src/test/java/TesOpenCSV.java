import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TesOpenCSV {
    public static void main(String[] args) throws IOException {
        String[] header = {"Name", "Age", "Address"};
        String[] record1 = {"John", "23", "New York"};
        String[] record2 = {"Tom", "25", "London"};

        Writer writer = Files.newBufferedWriter(Paths.get("test.csv"));

        try (CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeNext(header); //写入标题
            csvWriter.writeNext(record1); //写入第一行
            csvWriter.writeNext(record2); //写入第二行
        }
    }
}