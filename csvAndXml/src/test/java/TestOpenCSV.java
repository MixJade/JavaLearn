import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 测试OpenCSV的读写操作
 *
 * @since 2024-6-29 10:49:11
 */
public class TestOpenCSV {
    @Test
    public void testWriteCsv() {
        String[] header = {"Name", "Age", "Address"};
        String[] record1 = {"John", "23", "New York"};
        String[] record2 = {"Tom", "25", "London"};

        try {
            Writer writer = Files.newBufferedWriter(Paths.get("output/test.csv"));

            try (CSVWriter csvWriter = new CSVWriter(writer)) {
                csvWriter.writeNext(header); //写入标题
                csvWriter.writeNext(record1); //写入第一行
                csvWriter.writeNext(record2); //写入第二行
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testReadCsv() {
        try {
            CSVReader reader = new CSVReader(new FileReader("output/test.csv"));
            reader.skip(1); // 跳过第一行
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println(Arrays.toString(nextLine));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}