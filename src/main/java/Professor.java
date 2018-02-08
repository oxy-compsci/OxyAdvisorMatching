import java.util.*;

public class Professor {
    private static final int TOTAL_ADVISEE_LIMIT = 18;
    private static final int NEW_ADVISEE_LIMIT = 10;

    String id, last, first, department;
    int previousCount;
    int discipline_index;

    public Professor(String id, String last, String first, String department, int previousCount) {
        this.id = id;
        this.last = last;
        this.first = first;
        this.department = department;
        this.previousCount = previousCount;
        this.discipline_index = getAcademicDiscipline();
    }

    public boolean hasRelatedDepartment(String studentInterest) {
        // Checks to see if discipline is related to student's intended major(s)
        if(discipline_index == Main.disciplines.size())
            return false;
        return Arrays.asList(Main.disciplines.get(discipline_index).list).contains(studentInterest);
    }
    public boolean exceedsTotalAdviseeLimit(Map<Student, Professor> matches) {
        return (getTotalCount(matches) > TOTAL_ADVISEE_LIMIT);
    }
    public boolean exceedsNewAdviseeLimit(Map<Student, Professor> matches) {
        return ((getTotalCount(matches) - previousCount) > NEW_ADVISEE_LIMIT);
    }
    public int getTotalCount(Map<Student, Professor> matches) {
        // counts number of times that professor appears in matches map
        ArrayList<Student> students = new ArrayList<>(matches.keySet());
        int count = 0;
        for(int i = 0; i < students.size(); i++) {
            if(matches.get(students.get(i)) == this) {
             count++;
            }
        }
        // add the new number of advisees with the old
        return count + previousCount;
    }
    private int getAcademicDiscipline() {
        // Searches through the list of disciplines objects' lists
        // Finds what discipline their department falls under
        int num = 0;
        while(!Arrays.asList(Main.disciplines.get(num).list).contains(department)) {
            num++;
            if(num == Main.disciplines.size())
                break;
        }
        return num;
    }

}