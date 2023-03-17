import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.List;

public class OnlineCoursesAnalyzer {
    private List<Course> courseList;
    public OnlineCoursesAnalyzer(String datasetPath) throws IOException {
        // TODO: Read file and generate stream of course
        courseList = Files.lines(Paths.get(datasetPath))
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
        Map<String, Integer> res = courseList.stream().collect(Collectors.groupingBy(Course::getInstitution, Collectors.summingInt(Course::getParticipants)));
        return res.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldV, newV) -> oldV, LinkedHashMap::new));
    }

    public Map<String, Integer> getPtcpCountByInstAndSubject() {
        // TODO: Participants count by Institution and Course Subject
        Map<String, Integer> res = courseList.stream().collect(Collectors.groupingBy(c -> c.getInstitution()+"-"+c.getCourseSubjects(), Collectors.summingInt(Course::getParticipants)));
        return res.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldV, newV)->newV, LinkedHashMap::new));
    }

    public Map<String, List<List<String>>> getCourseListOfInstructor() {
        // TODO: Course list by Instructor
        Map<String, List<List<String>>> res = new HashMap<>();
        courseList.stream().sorted(Comparator.comparing(Course::getCourseTitle)).forEach(course -> {
            List<String> instructors = course.getInstructors().stream().toList();
            checkInstructor(res, instructors);
            if(instructors.size() == 1) {
                if(!res.get(instructors.get(0)).get(0).contains(course.getCourseTitle())) {
                    res.get(instructors.get(0)).get(0).add(course.getCourseTitle());
                }
            } else {
                instructors.stream().forEach(instructor -> {
                    if(!res.get(instructor).get(1).contains(course.getCourseTitle())) {
                        res.get(instructor).get(1).add(course.getCourseTitle());
                    }
                });
            }
        });
        return res;
    }

    public void checkInstructor(Map<String, List<List<String>>> res, List<String> instructors) {
        for(String instructor: instructors) {
            if(!res.containsKey(instructor)) {
                List<String> inCourse = new ArrayList<>();
                List<String> coCourse = new ArrayList<>();
                List<List<String>> instructorCourseList = new ArrayList<>();
                instructorCourseList.add(inCourse);
                instructorCourseList.add(coCourse);
                res.put(instructor, instructorCourseList);
            }
        }

    }

    public List<String> getCourses(int topK, String by) {
        // TODO: Top courses
        if(by.equals("hours")) {
            return courseList.stream()
                    .sorted(Comparator.comparing(Course::getPerThousandTotalCourseHours).reversed().thenComparing(Course::getCourseTitle))
                    .map(Course::getCourseTitle).distinct().limit(topK).toList();
        } else if(by.equals("participants")) {
            return courseList.stream()
                    .sorted(Comparator.comparing(Course::getParticipants).reversed().thenComparing(Course::getCourseTitle))
                    .map(Course::getCourseTitle).distinct().limit(topK).toList();
        } else {
            System.out.println("Not a valid by parameter.");
            return new ArrayList<>();
        }
    }

    public List<String> searchCourses(String courseSubject, double percentAudited, double totalCourseHours) {
        // TODO: Search courses
        return courseList.stream()
                .filter(course -> course.getCourseSubjects().toUpperCase().contains(courseSubject.toUpperCase()))
                .filter(course -> course.getPercentAudited() >= percentAudited)
                .filter(course -> course.getPerThousandTotalCourseHours() <= totalCourseHours)
                .map(Course::getCourseTitle)
                .distinct()
                .sorted()
                .toList();
    }

    public List<String> recommendCourses(int age, int gender, int isBachelorOrHigher) {
        // TODO: Recommend courses
        Map<String, Double> courseValue = courseList.stream()
                .collect(Collectors.groupingBy(Course::getCourseNumber,
                        Collectors.collectingAndThen(Collectors.toList(), courses -> {
                            double avgAge = courses.stream().collect(Collectors.averagingDouble(Course::getMedianAge));
                            double avgGender = courses.stream().collect(Collectors.averagingDouble(Course::getPercentMale));
                            double avgBoH = courses.stream().collect(Collectors.averagingDouble(Course::getPercentBachelorDegreeOrHigher));
                            return Math.pow(age-avgAge,2) + Math.pow(gender*100-avgGender,2) + Math.pow(isBachelorOrHigher*100-avgBoH,2);
                        })));

//        Map<String, List<Course>> courses = courseList.stream().collect(Collectors.groupingBy(Course::getCourseNumber));

//        Map<String, Double> courseValue = new HashMap<>();
//        for(Map.Entry<String, List<Course>> e: courses.entrySet()) {
//            double avgAge = 0, avgGender = 0, avgIsBOrH = 0, value;
//            List<Course> cList = e.getValue();
//            for(Course course: cList) {
//                avgAge += course.getMedianAge();
//                avgGender += course.getPercentMale();
//                avgIsBOrH += course.getPercentBachelorDegreeOrHigher();
//            }
//            avgAge = avgAge/cList.size();
//            avgGender = avgGender/cList.size();
//            avgIsBOrH = avgIsBOrH/cList.size();
//            value = Math.pow(age-avgAge,2) + Math.pow(gender*100-avgGender,2) + Math.pow(isBachelorOrHigher*100-avgIsBOrH,2);
//            courseValue.put(e.getKey(), value);
//        }
        //courseValue.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getValue).forEach(System.out::println);
//        courseValue.entrySet().stream()
//                .map(Map.Entry::getValue)
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
//                .entrySet().stream()
//                .filter(e -> e.getValue() > 1)
//                .map(Map.Entry::getKey)
//                .forEach(System.out::println);

        List<String> cOrder = courseValue.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();

//        Comparator<Course> valueCmp = Comparator.comparingInt(c -> cOrder.indexOf(c.getCourseNumber()));
        Set<String> cnSet = new HashSet<>();

//        courseList.stream()
//                .sorted(Comparator.comparing(Course::getLaunchDate).reversed())
//                .filter(course -> cnSet.add(course.getCourseNumber()))
//                .forEach(course -> System.out.println(course.getCourseNumber()+" "+course.getCourseTitle()+" "+course.getLaunchDate()));

        return courseList.stream()
                .sorted(Comparator.comparing(Course::getLaunchDate).reversed())
                .filter(course -> cnSet.add(course.getCourseNumber()))
                .sorted(Comparator.comparing((Course c) -> cOrder.indexOf(c.getCourseNumber())).thenComparing(Course::getCourseTitle))
                .map(Course::getCourseTitle)
                .distinct()
                .limit(10)
                .toList();

    }
    class Course {
        private String institution;
        private String courseNumber;
        private Date launchDate;
        private String courseTitle;
        private List<String> instructors;
        private String courseSubjects;
        private int year;
        private int honorCodeCertificates;
        private int participants;
        private int audited;
        private int certified;
        private double percentAudited;
        private double percentCertified;
        private double percentCertifiedOfAudited;
        private double percentPlayedVideo;
        private double percentPostedInForum;
        private double percentGradeHigherThanZero;
        private double perThousandTotalCourseHours;
        private double medianHoursForCertification;
        private double medianAge;
        private double percentMale;
        private double percentFemale;
        private double percentBachelorDegreeOrHigher;

        public Course(String institution, String courseNumber, Date launchDate, String courseTitle, String instructors, String courseSubjects, int year, int honorCodeCertificates, int participants, int audited, int certified, double percentAudited, double percentCertified, double percentCertifiedOfAudited, double percentPlayedVideo, double percentPostedInForum, double percentGradeHigherThanZero, double perThousandTotalCourseHours, double medianHoursForCertification, double medianAge, double percentMale, double percentFemale, double percentBachelorDegreeOrHigher) {
            this.institution = institution;
            this.courseNumber = courseNumber;
            this.launchDate = launchDate;
            if(courseTitle.startsWith("\"")) courseTitle = courseTitle.substring(1);
            if(courseTitle.endsWith("\"")) courseTitle = courseTitle.substring(0, courseTitle.length()-1);
            this.courseTitle = courseTitle;
            if (instructors.startsWith("\"")) instructors = instructors.substring(1);
            if (instructors.endsWith("\"")) instructors = instructors.substring(0, instructors.length() - 1);
            this.instructors = Arrays.stream(instructors.split(",")).map(String::trim).toList();
            if (courseSubjects.startsWith("\"")) courseSubjects = courseSubjects.substring(1);
            if (courseSubjects.endsWith("\"")) courseSubjects = courseSubjects.substring(0, courseSubjects.length() - 1);
            this.courseSubjects = courseSubjects;
            this.year = year;
            this.honorCodeCertificates = honorCodeCertificates;
            this.participants = participants;
            this.audited = audited;
            this.certified = certified;
            this.percentAudited = percentAudited;
            this.percentCertified = percentCertified;
            this.percentCertifiedOfAudited = percentCertifiedOfAudited;
            this.percentPlayedVideo = percentPlayedVideo;
            this.percentPostedInForum = percentPostedInForum;
            this.percentGradeHigherThanZero = percentGradeHigherThanZero;
            this.perThousandTotalCourseHours = perThousandTotalCourseHours;
            this.medianHoursForCertification = medianHoursForCertification;
            this.medianAge = medianAge;
            this.percentMale = percentMale;
            this.percentFemale = percentFemale;
            this.percentBachelorDegreeOrHigher = percentBachelorDegreeOrHigher;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "{" +
                    Arrays.stream(getClass().getDeclaredFields())
                            .map(field -> {
                                try{
                                    field.setAccessible(true);
                                    return field.getName() + "=" + field.get(this);
                                } catch (IllegalAccessException e) {
                                    return "";
                                }
                            })
                            .filter(s -> !s.isEmpty())
                            .reduce((s1,s2) -> s1+", "+s2)
                            .orElse("") +
                    "}";
        }

        public String getInstitution() {
            return institution;
        }

        public String getCourseNumber() {
            return courseNumber;
        }

        public Date getLaunchDate() {
            return launchDate;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public List<String> getInstructors() {
            return instructors;
        }

        public String getCourseSubjects() {
            return courseSubjects;
        }

        public int getYear() {
            return year;
        }

        public int getHonorCodeCertificates() {
            return honorCodeCertificates;
        }

        public int getParticipants() {
            return participants;
        }

        public int getAudited() {
            return audited;
        }

        public int getCertified() {
            return certified;
        }

        public double getPercentAudited() {
            return percentAudited;
        }

        public double getPercentCertified() {
            return percentCertified;
        }

        public double getPercentCertifiedOfAudited() {
            return percentCertifiedOfAudited;
        }

        public double getPercentPlayedVideo() {
            return percentPlayedVideo;
        }

        public double getPercentPostedInForum() {
            return percentPostedInForum;
        }

        public double getPercentGradeHigherThanZero() {
            return percentGradeHigherThanZero;
        }

        public double getPerThousandTotalCourseHours() {
            return perThousandTotalCourseHours;
        }

        public double getMedianHoursForCertification() {
            return medianHoursForCertification;
        }

        public double getMedianAge() {
            return medianAge;
        }

        public double getPercentMale() {
            return percentMale;
        }

        public double getPercentFemale() {
            return percentFemale;
        }

        public double getPercentBachelorDegreeOrHigher() {
            return percentBachelorDegreeOrHigher;
        }
    }

}
