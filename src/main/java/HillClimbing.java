import java.util.*;

public class HillClimbing {

    public static final int NUM_SWAPS = 500;
    public static final int NUM_RESTARTS = 10;

    public Map<Student, Professor> hillClimbStep(Map<Student, Professor> initialMap, int initialScore) {
        Map<Student, Professor> nextMap = null;
        Map<Student, Professor> bestNextMap = null;
        int bestScore = initialScore;
        for (int swap = 0; swap < NUM_SWAPS; swap++) {
            nextMap = (HashMap<Student, Professor>) ((HashMap<Student, Professor>) initialMap).clone();
            Random random = new Random();
            List<Student> keys = new ArrayList<Student>(nextMap.keySet());

            Student randomKey1 = keys.get(random.nextInt(keys.size()));
            Student randomKey2 = keys.get(random.nextInt(keys.size()));
            Professor temp1 = nextMap.get(randomKey1);
            nextMap.put(randomKey1, nextMap.get(randomKey2));
            nextMap.put(randomKey2, temp1);

            int nextScore = score(nextMap);
            if (nextScore > bestScore) {
                bestNextMap = nextMap;
                bestScore = nextScore;
            }
        }
        return bestNextMap;
    }
    //whats a good score??? with this data.
    //focus on prints out for data analysis.

    public Map<Student, Professor> hillClimb(Map<Student, Professor> initMap) {
        int step = 0;
        Map<Student, Professor> currentMap = initMap;
        int currentScore = score(currentMap);
        Map<Student, Professor> nextMap;
        while (true) {
            nextMap = hillClimbStep(currentMap, currentScore);
            if (nextMap == null) {
                break;
            } else {
                int nextScore = score(nextMap);
                System.out.println("    Generation #" + step + " score: " + nextScore + " > " + currentScore);
                if (nextScore > currentScore) {
                    currentMap.clear();
                    currentMap.putAll(nextMap);
                    currentScore = nextScore;
                } else {
                    break;
                }
            }
            step++;
        }
        return currentMap;
    }

    public Map<Student, Professor> randomRestartHillClimb(List<Student> students, List<Professor> professors) {
        Map<Student, Professor> bestMap = null;
        int bestScore = -1;
        for (int restart = 0; restart < NUM_RESTARTS; restart++) {
            Map<Student, Professor> randomMap = createMap(students, professors);
            Map<Student, Professor> currentMap = hillClimb(randomMap);
            int currentScore = score(currentMap);
            if (bestMap == null || currentScore > bestScore) {
                System.out.println("Restart #" + restart + " score: " + currentScore + " > " + bestScore);
                bestMap = currentMap;
                bestScore = currentScore;
            } else {
                System.out.println("Restart #" + restart + " score: " + currentScore + " <= " + bestScore);
            }
        }
        return bestMap;
    }

    public Map<Student, Professor> findBestMatches(List<Student> students, List<Professor> professors) {
        return randomRestartHillClimb(students, professors);
    }

    public Map<Student, Professor> createMap(List<Student> students, List<Professor> professors) {
        Collections.shuffle(professors);
        Collections.shuffle(students);
        Map<Student, Professor> studentProfMatches = new HashMap<Student, Professor>();
        int j = 0;
        for (int i = 0; i < students.size(); i++) {
            studentProfMatches.put(students.get(i), professors.get(j));
            j++;
            if (j == professors.size() - 1) {
                j = 0;
            }
        }
        return studentProfMatches;
    }

    public int score(Map<Student, Professor> map) {
        int score = 0;
        ArrayList<Student> studentList = new ArrayList<Student>();
        studentList.addAll(map.keySet());

        for (int i = 0; i < studentList.size(); i++) {
            score += scoreMatch(studentList.get(i), map.get(studentList.get(i)));
        }
        return score;
    }

    public int scoreMatch(Student student, Professor professor) {
        int score = 0;
        for (int i = 0; i < student.majors.size(); i++) {
            if (professor.department.equalsIgnoreCase(student.majors.get(i))) {
                score++;
            }
        }
        return score;
    }
}