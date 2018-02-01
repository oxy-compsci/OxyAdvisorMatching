import java.util.List;

public class Student {

    String id, status, last, first;
    List<String> majors, minors;

    public Student(String id, String status, String last, String first, List<String> majors, List<String> minors) {
        this.id = id;
        this.status = status;
        this.last = last;
        this.first = first;
        this.majors = majors;
        this.minors = minors;
    }

    // Probably want to create methods to clean string variables and lists
}
