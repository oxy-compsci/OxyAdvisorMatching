import java.util.*;

public class HillClimbing {
    // Constants
    // # of swaps should be roughly equal to # of incoming students (~500)
    public static final int NUM_SWAPS = 50;
    public static final int NUM_RESTARTS = 3;

    // A map that connects individual student objects with an explanation of the best match
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
        Map<Student, String> nextExplanations,currentExplanations;
        Map<Student, Professor> currentMap = initMap;
        double currentScore = score(currentMap);
        currentExplanations = (HashMap<Student, String>)((HashMap<Student, String>) explanations).clone();
        Map<Student, Professor> nextMap;
        while (true) {
            nextMap = hillClimbStep(currentMap, currentScore);
            if (nextMap == null) {
                break;
            } else {
                double nextScore = score(nextMap);
                nextExplanations = (HashMap<Student, String>)((HashMap<Student, String>) explanations).clone();
//                System.out.println("    Generation #" + step + " score: " + nextScore + " > " + currentScore);
                if (nextScore > currentScore) {
                    currentMap.clear();
                    currentMap.putAll(nextMap);
                    currentScore = nextScore;
                    explanations = nextExplanations;
                } else {
                    explanations = currentExplanations;
                    break;
                }
            }
            step++;
        }
        return currentMap;
    }

    public Map<Student, Professor> randomRestartHillClimb(List<Student> students, List<Professor> professors) {
        Map<Student, Professor> bestMap = null;
        Map<Student, String> bestExplanations, currentExplanations;
        double bestScore = -1;
        for (int restart = 0; restart < NUM_RESTARTS; restart++) {
            Map<Student, Professor> randomMap = createMap(students, professors);
            bestExplanations = (HashMap<Student, String>)((HashMap<Student, String>) explanations).clone();
            Map<Student, Professor> currentMap = hillClimb(randomMap);
            double currentScore = score(currentMap);
            currentExplanations = (HashMap<Student, String>)((HashMap<Student, String>) explanations).clone();
            if (bestMap == null || currentScore > bestScore) {
                System.out.println("Restart #" + restart + " score: " + currentScore + " > " + bestScore);
                bestMap = currentMap;
                bestScore = currentScore;
                explanations = currentExplanations;
            } else {
                explanations = bestExplanations;
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
            // if the current indexed professor has too many advisees, go to the next one
            // reset counter if needed
            while(professors.get(j).exceedsTotalAdviseeLimit(studentProfMatches)) {
                j++;
                if (j == professors.size() - 1) {
                    j = 0;
                }
            }
            studentProfMatches.put(students.get(i), professors.get(j));
            j++;
            if (j == professors.size() - 1) {
                j = 0;
            }
        }
        return studentProfMatches;
    }

    public double score(Map<Student, Professor> map) {
        // Sums overall score of the current matching
        double score = 0;
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.addAll(map.keySet());
        for (int i = 0; i < studentList.size(); i++) {
            score += scoreMatch(studentList.get(i), map.get(studentList.get(i)), map);
        }
        return score;
    }

    public double scoreMatch(Student student, Professor professor, Map<Student, Professor> matches) {
        String reason;
        double score = 0;
        ArrayList<String> reasonsArr = new ArrayList<>();

        // Loop through list of intended majors
        for (int i = 0; i < student.majors.size(); i++) {
            // cross check professor's department with major
            // add 1 to score value
            if (professor.department.equals(student.majors.get(i))) {
                reason = "The student wants to major in the advisor's department ("+ student.majors.get(i) + ")";
                reasonsArr.add(reason);
                score = score + 2;
            } else if(professor.hasRelatedDepartment(student.majors.get(i))) {
                // add .75 to score value if major is related
                reason = "The student is interested in (" + student.majors.get(i) + "), which is in the same division as the advisor's department ("+ professor.department + ")";
                reasonsArr.add(reason);
                score = score + 1;
            }
        }

        boolean exceedsTotalAdviseeLimit = professor.exceedsTotalAdviseeLimit(matches);
        boolean exceedsNewAdviseeLimit = professor.exceedsNewAdviseeLimit(matches);
        int adviseeCount = professor.getTotalCount(matches);


        if(exceedsTotalAdviseeLimit) {
            // penalize if limit of 17 advisees is met
            score = score - .5;
        } else {
            // add .5 otherwise
            reason = "Professor currently only has (" + adviseeCount + ") advisees";
            reasonsArr.add(reason);
            score = score + .5;
        }
        if(exceedsNewAdviseeLimit) {
            // penalize if professor has more than 10 new advisees
            score = score - .25;
        }


        if(reasonsArr.isEmpty()) {
            explanations.put(student, "Randomly matched");
        } else {
            explanations.put(student, reasonsArr.toString().replaceAll(",", "; "));
        }
        return score;
    }

    public Map<Student, String> getExplanations() {
        return explanations;
    }
    // TODO: add multilines to explanations
}
