import java.util.Arrays;
import java.util.List;

public class Professor {

    String id, last, first, department;
    int count;

    public Professor(String id, String last, String first, String department, int count) {
        this.id = id;
        this.last = last;
        this.first = first;
        this.department = department;
        this.count = count;
    }

    public boolean hasRelatedDepartment(String studentInterest) {
        // Checks to see if discipline is related to student's intended major(s)
        int num = getAcademicDiscipline();

        if(num == Main.disciplines.size())
            return false;
        return Arrays.asList(Main.disciplines.get(num).list).contains(studentInterest);
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