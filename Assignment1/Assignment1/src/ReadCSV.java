import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

public class ReadCSV {
    public static void main(String[] args) throws IOException {
        String csvFile = "local.csv";
        String line = "";

//        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//
//            while ((line = br.readLine()) != null) {
//
//                String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//
//                // Do something with the data
//                Stream.of(data).forEach(key -> System.out.print(key+" "));
//                System.out.println();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Stream<Course> courseStream = Files.lines(Paths.get(csvFile))
                .skip(1)
                .map(l -> l.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))
                .map(data -> new Course(data[0], data[1], new Date(data[2]), data[3],
                        data[4], data[5], Integer.parseInt(data[6]),
                        Integer.parseInt(data[7]), Integer.parseInt(data[8]),
                        Integer.parseInt(data[9]), Integer.parseInt(data[10]),
                        Double.parseDouble(data[11]),
                        Double.parseDouble(data[12]), Double.parseDouble(data[13]),
                        Double.parseDouble(data[14]), Double.parseDouble(data[15]),
                        Double.parseDouble(data[16]), Double.parseDouble(data[17]),
                        Double.parseDouble(data[18]), Double.parseDouble(data[19]),
                        Double.parseDouble(data[20]), Double.parseDouble(data[21]),
                        Double.parseDouble(data[22])));

        courseStream.forEach(System.out::println);



    }
}
