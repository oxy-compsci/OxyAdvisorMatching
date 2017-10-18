import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HillClimbing {

    public static final int MAX_ITERATIONS = 100;
    public Map<Student, Professor> findBestMatches(List<Student> students, List<Professor> professors, Professor currentProfessor) {
        int generation = 0;
        Professor nextProfessor;
        Set<Student> studentKeys = new HashSet<Student>(students);
        Map<Student, Professor> studentProfMatches = new HashMap<Student, Professor>();
        while(generation < MAX_ITERATIONS) {
            for(int i = 0; i < students.size(); i++) {
                nextProfessor = findNextProfessor(professors, currentProfessor);
                Match nextMatch = new Match(students.get(i), nextProfessor);
                Match currentMatch = new Match(students.get(i), currentProfessor);
                if(currentMatch.getMatchScore() > nextMatch.getMatchScore()) {
                    // continue add proceeding matches to the map
                } else {
                    //
                }
            }
        }
        return studentProfMatches;

    }

    public Professor findNextProfessor(List<Professor> professors, Professor currentProfessor) {
        int i = professors.indexOf(currentProfessor);
        if(i == professors.size() - 1)
            return professors.get(0);
        return professors.get(i+1);
    }

}
