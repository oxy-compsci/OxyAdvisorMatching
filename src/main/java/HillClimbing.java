import java.util.*;

public class HillClimbing {
    public static final int MAX_ITERATIONS = 750;

    public Map<Student, Professor> hillClimb(Map<Student, Professor> initialMap, int numSwaps) {
        Map<Student, Professor> nextMap = null;
        Map<Student, Professor> bestNextMap = null;
        int bestScore = -1;
        int nextScore = -1;
        for (int swap = 0; swap < numSwaps; swap++) {
            nextMap =  (HashMap<Student,Professor>)((HashMap<Student,Professor>)initialMap).clone();
            Random random= new Random();
            List<Student>keys = new ArrayList<Student>(nextMap.keySet());

            Student randomKey1 = keys.get(random.nextInt(keys.size()));
            Student randomKey2 = keys.get(random.nextInt(keys.size()));
            Professor temp1 = nextMap.get(randomKey1);
            nextMap.put(randomKey1, nextMap.get(randomKey2));
            nextMap.put(randomKey2, temp1);

            nextScore = score(nextMap);
            if (bestNextMap == null || nextScore > bestScore) {
                bestNextMap = nextMap;
                bestScore = score(nextMap);
            }
        }
        return bestNextMap;
    }
    //whats a good score??? with this data.
    //focus on prints out for data analysis.

    public Map<Student, Professor> findBestMatches(List<Student> students, List<Professor> professors) {
        int generation = 0;
        Map<Student, Professor> currentMap = createMap(students, professors);
        Map<Student, Professor> nextMap;
        while(generation < MAX_ITERATIONS) {
            nextMap = hillClimb(currentMap, 500);
            int currentScore = score(currentMap);
            int nextScore = score(nextMap);
//            System.out.println("Generation #" + (generation+1));
//            System.out.println("Current score: " + currentScore);
//            System.out.println("Next score: " + nextScore);
//            System.out.println("-----------------------");
            if(nextScore > currentScore) {
                currentMap.clear();
                currentMap.putAll(nextMap);
            }
            generation++;
        }
        return currentMap;
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
        return studentProfMatches;
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
}
