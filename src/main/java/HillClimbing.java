import java.util.*;

public class HillClimbing {

    public static final int NUM_SWAPS = 50;
    public static final int NUM_RESTARTS = 5;

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
            Map<Student, Professor> currentMap = hillClimb(randomMap);
            Map<Student, String> previousExplanations = (HashMap<Student, String>)((HashMap<Student, String>) explanations).clone();
            double currentScore = score(currentMap);
            if (bestMap == null || currentScore > bestScore) {
//                System.out.println("Restart #" + restart + " score: " + currentScore + " > " + bestScore);
                bestMap = currentMap;
                bestScore = currentScore;
            } else {
                explanations.clear();
                explanations.putAll(previousExplanations);
//                System.out.println("Restart #" + restart + " score: " + currentScore + " <= " + bestScore);
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
        ArrayList<String> reasonsArr = new ArrayList<>();
        String reason;
        double score = 0;

        for (int i = 0; i < student.majors.size(); i++) {
            String major = student.majors.get(i);
            String department = professor.department;
            Data data = new Data(department, major);
            boolean isEqual = department.equals(major);
            boolean isRelated = data.isRelatedField();
            if (isEqual) {
                reason = "Student's major: ["+ student.majors.get(i) + "] and professor's department: "+ professor.department +" match directly";
                reasonsArr.add(reason);
                explanations.replace(student, reason);
                score++;
            } else if(isRelated) {
                reason = "Student's major: ["+ student.majors.get(i) + "] and professor's department: "+ professor.department +" are related";
                reasonsArr.add(reason);
                explanations.replace(student, reason);
                score = score + .5;
            }
        }
        if(reasonsArr.isEmpty()) {
            explanations.put(student, "Randomly matched");
        } else {
            explanations.put(student, reasonsArr.toString().replaceAll(",", " | "));
        }

        return score;
    }
}