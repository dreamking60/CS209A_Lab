import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.CollationElementIterator;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OnlineCoursesAnalyzer {
    private List<Course> couseList;
    public OnlineCoursesAnalyzer(String datasetPath) throws IOException {
        // TODO: Read file and generate stream of course
        couseList = Files.lines(Paths.get(datasetPath))
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
                                        Double.parseDouble(data[22]))).toList();
    }

    public Map<String, Integer> getPtcpCountByInst() {
        // TODO: Participants count by Institution
        Map<String, Integer> res = couseList.stream().collect(Collectors.groupingBy(Course::getInstitution, Collectors.summingInt(Course::getParticipants)));
        return res.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));
    }

    public Map<String, Integer> getPtCountByInstAndSubject() {
        // TODO: Participants count by Institution and Course Subject
        Map<String, Integer> res = couseList.stream().collect(Collectors.groupingBy(c -> c.getInstitution()+"-"+c.getCourseSubjects(), Collectors.summingInt(Course::getParticipants)));
        return res.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldV, newV)->newV, LinkedHashMap::new));
    }

    public Map<String, List<List<String>>> getCourseListOfInstructor() {
        // TODO: Course list by Instructor

        return null;
    }

    public List<String> getCourses(int topK, String by) {
        // TODO: Top courses

        return null;
    }

    public List<String> searchCourses(String courseSubject, double percentAudited, double totalCourseHours) {
        // TODO: Search courses

        return null;
    }

    public List<String> recommendCourses(int age, int gender, int isBachelorOrHigher) {
        // TODO: Recommend courses

        return null;
    }


}
