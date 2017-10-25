import java.util.*;

public class HillClimbing {
    public static final int MAX_ITERATIONS = 150;

    public Map<Student, Professor> findBestMatches(List<Student> students, List<Professor> professors) {
        int generation = 0;
        Map<Student, Professor> currentMap = createMap(students, professors);
        while(generation < MAX_ITERATIONS) {
            Map<Student, Professor> nextMap = createMap(students, professors);
            int currentScore = score(currentMap);
            int nextScore = score(nextMap);
            System.out.println("Generation #" + (generation+1));
            System.out.println("Current score: " + currentScore);
            System.out.println("Next score: " + nextScore);
            System.out.println("-----------------------");
            if(nextScore > currentScore) {
                currentMap.clear();
                currentMap.putAll(nextMap);
            }
            generation++;
        }
        double successRate = ((double)score(currentMap)/students.size())*100.0;
        System.out.println("Success rate: " + successRate);
        return currentMap;
    }

    public int score(Map<Student, Professor> map) {
        int score = 0;
        ArrayList<Student> studentList = new ArrayList<Student>();
        studentList.addAll(map.keySet());

        for(int i = 0; i < studentList.size(); i++) {
            Match currentMatch = new Match(studentList.get(i),map.get(studentList.get(i)));
            score = score + currentMatch.getMatchScore();
        }
        return score;
    }
    public Map<Student, Professor> createMap(List<Student> students, List<Professor> professors) {
        Collections.shuffle(professors);
        Collections.shuffle(students);
        Map<Student, Professor> studentProfMatches = new HashMap<Student, Professor>();
        int j = 0;
        for(int i = 0; i < students.size(); i++) {
            studentProfMatches.put(students.get(i), professors.get(j));
            j++;
            if(j == professors.size() - 1) j = 0;
        }
/*        for (Map.Entry<Student, Professor> entry : studentProfMatches.entrySet()) {
            if(entry.getValue().first == null) {
                count++;
            }
            System.out.println( entry.getKey().last +",  " + entry.getKey().majors + " : " +
                    entry.getValue().last + ", " + entry.getValue().department);
       }*/
        return studentProfMatches;
    }
}
