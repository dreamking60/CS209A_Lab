import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class ReadCSV {
    public static void main(String[] args) {
        String csvFile = "local.csv";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                // Do something with the data
                Stream.of(data).forEach(key -> System.out.print(key+" "));
                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
