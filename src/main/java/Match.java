public class Match {

    Student student;
    Professor professor;

    public Match(Student student, Professor professor) {
        this.student = student;
        this.professor = professor;
    }

    public int getMatchScore() {
        int score = 0;
        for (int i = 0; i < student.majors.size(); i++) {
            if (professor.department.equalsIgnoreCase(student.majors.get(i)))
                score++;
        }
        return score;
    }
}