import java.util.*;

public class HillClimbing {
    public static final int MAX_ITERATIONS = 500;

    public Map<Student, Professor> findBestMatches(List<Student> students, List<Professor> professors) {
        int generation = 0;
        Map<Student, Professor> currentMap = createMap(students, professors);
        Map<Student, Professor> bestNextMap = null;
        while(generation < MAX_ITERATIONS) {
            Map<Student, Professor> nextMap = null;
            int bestScore = -1;
            int nextScore = -1;
            for (int swap = 0; swap < 100; swap++) {
                nextMap =  (HashMap<Student,Professor>)((HashMap<Student,Professor>)currentMap).clone();
                Random random= new Random();
                List<Student>keys = new ArrayList<Student>(currentMap.keySet());

                Student randomKey1 = keys.get(random.nextInt(keys.size()));
                Student randomKey2 = keys.get(random.nextInt(keys.size()));
                Professor temp1 = currentMap.get(randomKey1);
                nextMap.put(randomKey1, currentMap.get(randomKey2));
                nextMap.put(randomKey2, temp1);

                nextScore = score(nextMap);
                if (bestNextMap == null || nextScore > bestScore) {
                    bestNextMap = nextMap;
                    bestScore = score(nextMap);
                }
            }
            int currentScore = score(currentMap);
            System.out.println("Generation #" + (generation+1));
            System.out.println("Current best score: " + bestScore);
            System.out.println("Next score: " + nextScore);
            System.out.println("-----------------------");
            if(nextScore > currentScore) {
                currentMap.clear();
                currentMap.putAll(nextMap);
            }
            generation++;
        }

        return bestNextMap;
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
