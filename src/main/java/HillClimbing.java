import java.util.*;

public class HillClimbing {

    public static final int NUM_SWAPS = 250;
    public static final int NUM_RESTARTS = 10;

    Map<Student, String> explanations = new HashMap<>();

    public Map<Student, Professor> hillClimbStep(Map<Student, Professor> initialMap, double initialScore) {
        Map<Student, Professor> nextMap;
        Map<Student, Professor> bestNextMap = null;
        double bestScore = initialScore;
        for (int swap = 0; swap < NUM_SWAPS; swap++) {
            nextMap = (HashMap<Student, Professor>)((HashMap<Student, Professor>) initialMap).clone();
            Random random = new Random();
            List<Student> keys = new ArrayList<>(nextMap.keySet());

            Student randomKey1 = keys.get(random.nextInt(keys.size()));
            Student randomKey2 = keys.get(random.nextInt(keys.size()));
            Professor temp1 = nextMap.get(randomKey1);
            nextMap.replace(randomKey1, nextMap.get(randomKey2));
            nextMap.replace(randomKey2, temp1);

            double nextScore = score(nextMap);
            if (nextScore > bestScore) {
                bestNextMap = nextMap;
                bestScore = nextScore;
            }
        }
        return bestNextMap;
    }

    public Map<Student, Professor> hillClimb(Map<Student, Professor> initMap) {
        int step = 0;
        Map<Student, Professor> currentMap = initMap;
        double currentScore = score(currentMap);
        Map<Student, Professor> nextMap;
        while (true) {
            nextMap = hillClimbStep(currentMap, currentScore);
            if (nextMap == null) {
                break;
            } else {
                double nextScore = score(nextMap);
//                System.out.println("    Generation #" + step + " score: " + nextScore + " > " + currentScore);
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
        double bestScore = -1;
        for (int restart = 0; restart < NUM_RESTARTS; restart++) {
            Map<Student, Professor> randomMap = createMap(students, professors);
            Map<Student, String> previousExplanations = explanations;
            Map<Student, Professor> currentMap = hillClimb(randomMap);
            double currentScore = score(currentMap);
            Map<Student, String> currentExplanations = explanations;
            if (bestMap == null || currentScore > bestScore) {
                System.out.println("Restart #" + restart + " score: " + currentScore + " > " + bestScore);
                bestMap = currentMap;
                bestScore = currentScore;
                explanations = currentExplanations;
            } else {
                explanations = previousExplanations;
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
        Map<Student, Professor> studentProfMatches = new HashMap<>();
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

    public double score(Map<Student, Professor> map) {
        double score = 0;
        ArrayList<Student> studentList = new ArrayList<Student>();
        studentList.addAll(map.keySet());
        for (int i = 0; i < studentList.size(); i++) {
            score += scoreMatch(studentList.get(i), map.get(studentList.get(i)));
        }
        return score;
    }

    public double scoreMatch(Student student, Professor professor) {
        String reason;
        double score = 0;
        ArrayList<String> reasonsArr = new ArrayList<>();
        for (int i = 0; i < student.majors.size(); i++) {
            Data data = new Data(professor.department, student.majors.get(i));
            if (professor.department.equals(student.majors.get(i))) {
                reason = "The student wants to major in the advisor's department ("+ student.majors.get(i) + ")";
                reasonsArr.add(reason);
                score = score + 1;
            } else if(data.isRelatedField()) {
                reason = "The student wants to major in " + student.majors.get(i) + ", which is in the same division as the advisor's department ("+ professor.department + ")";
                reasonsArr.add(reason);
                score = score + .75;
            }
        }
        if(professor.count < 10) {
            reason = "Professor currently only has " + professor.count + " advisees";
            reasonsArr.add(reason);
            score = score + .5;
        }

        if(reasonsArr.isEmpty()) {
            explanations.put(student, "Randomly matched");
        } else {
            explanations.put(student, reasonsArr.toString().replaceAll(",", " | "));
        }

        return score;
    }

    public Map<Student, String> getExplanations() {
        return explanations;
    }
}
