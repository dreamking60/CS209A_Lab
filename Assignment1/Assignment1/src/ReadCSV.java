import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
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

        List<Course> couseList = courseStream.toList();
//        couseList.stream().forEach(Course -> {
//            System.out.println("Course Subject: " + Course.getCourseSubjects());
//        });
//        couseList.stream().forEach(Course -> {
//            System.out.println("Instructor: " + Course.getInstructors());
//        });
//
//        Map<String, Integer> res1 = couseList.stream().collect(Collectors.groupingBy(Course::getInstitution, Collectors.summingInt(s->1)));
//        res1.forEach((s, integer) -> {
//            System.out.printf("%s: %d\n", s, integer);
//        });
//        res1.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new))
//                .forEach((s,integer) -> {
//                    System.out.printf("%s: %d\n", s, integer);
//                });
//
//        List<String> inst_subject = couseList.stream().map(s -> s.getInstitution() + "-" + s.getCourseSubjects()).toList();
//        Map<String, Integer> res2 = inst_subject.stream().collect(Collectors.groupingBy(String::toString, Collectors.summingInt(s->1)));
//        res2.forEach((s, integer) -> {
//            System.out.printf("%s: %d\n", s, integer);
//        });

        OnlineCoursesAnalyzer oa = new OnlineCoursesAnalyzer(csvFile);

        // Q1 Test
//        oa.getPtcpCountByInst().forEach((s,i)->{
//            System.out.printf("%s: %d\n", s, i);
//        });

        // Q2 Test
//        oa.getPtcpCountByInstAndSubject().forEach((s,i)->{
//            System.out.printf("%s: %d\n", s, i);
//        });

        //Q3
//        oa.getCourseListOfInstructor().forEach((s,ss) -> {
//            System.out.println(s+": "+ss);
//        });
//        LinkedList<String> testString = new LinkedList<>();
//        String a = "Hello";
//        String b = "Hello";
//        String c = "World";
//        testString.add(a);
//        testString.add(b);
//        testString.add(c);
//        System.out.println(testString);

        //Q4
//        couseList.stream()
//                .sorted(Comparator.comparing(Course::getPerThousandTotalCourseHours).reversed().thenComparing(Course::getCourseTitle))
//                .collect(Collectors.toList());
        //Q6
//        couseList.stream()
//                .collect(Collectors.groupingBy(Course::getCourseNumber))
//                .entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        entry -> entry.getValue().stream()
//                                        .mapToDouble(c -> c.getParticipants() * c.getPerThousandTotalCourseHours())
//                                .sum() / entry.getValue().stream().mapToDouble(c -> c.getParticipants()).sum()))
//                .forEach((s, aDouble) -> System.out.println(s+": "+aDouble));
        //int age = , int gender = , int isBachelorOrHigher = ;
//        Map<String, List<Course>> courses = couseList.stream().collect(Collectors.groupingBy(Course::getCourseNumber));
//        Map<String, Double> courseValue = new HashMap<>();
//        for(Map.Entry<String, List<Course>> e: courses.entrySet()) {
//            double avgAge = 0, avgGender = 0, avgIsBOrH = 0;
//            double partipant = 0, value = 0;
//            List<Course> cList = e.getValue();
//            if(e.getValue().size() == 1) {
//                avgAge += cList.get(0).getMedianAge();
//                avgGender += cList.get(0).getPercentMale();
//                avgIsBOrH += cList.get(0).getPercentBachelorDegreeOrHigher();
//            } else {
//                for(Course course: cList) {
//                    avgAge += course.getMedianAge()*course.getParticipants();
//                    avgGender += course.getPercentMale()*course.getParticipants();
//                    avgIsBOrH += course.getPercentBachelorDegreeOrHigher()*course.getParticipants();
//                    partipant += course.getParticipants();
////                    avgAge += course.getMedianAge();
////                    avgGender += course.getPercentMale();
////                    avgIsBOrH += course.getPercentBachelorDegreeOrHigher();
//                }
//                avgAge = avgAge/partipant;
//                avgGender = avgGender/partipant;
//                avgIsBOrH = avgIsBOrH/partipant;
////                avgAge = avgAge/cList.size();
////                avgGender = avgGender/cList.size();
////                avgIsBOrH = avgIsBOrH/cList.size();
//            }
//            value = Math.pow(age-avgAge,2) + Math.pow(gender*100-avgGender,2) + Math.pow(isBachelorOrHigher*100-avgIsBOrH,2);
//            courseValue.put(e.getKey(), value);
//        }
//
//        List<String> cChose = courseValue.entrySet().stream()
//                .sorted(Map.Entry.comparingByValue())
//                .limit(10)
//                .map(Map.Entry::getKey)
//                .toList();
//        couseList.stream().sorted(Comparator.comparing(Course::getLaunchDate).reversed()).map(Course::getLaunchDate)
//                .forEach(System.out::println);
//        //couseList.sort(Comparator.comparing())
//        couseList.stream()
//                .sorted()
    }
}
