import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OnlineCoursesAnalyzer {
    private Stream<Course> courseStream;
    public OnlineCoursesAnalyzer(String datasetPath) throws IOException {
        // TODO: Read file and generate stream of course
        courseStream = Files.lines(Paths.get(datasetPath))
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
    }

    public Map<String, Integer> getPtcpCountByInst() {
        // TODO: Participants count by Institution

        return null;
    }

    public Map<String, Integer> getPtCountByInstAndSubject() {
        // TODO: Participants count by Institution and Course Subject

        return null;
    }

    public Map<String, Integer> getCourseListOfInstructor() {
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

    public static void main(String[] args) {

    }

}
