import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class Course {
    private String institution;
    private String courseNumber;
    private Date launchDate;
    private String courseTitle;
    private String instructors;
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
        this.instructors = instructors;
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

    public String getInstructors() {
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
