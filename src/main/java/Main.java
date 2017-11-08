import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

public class Main {

    private static final String FILE_HEADER = "Student ID,Student Name,Majors,Professor ID,Professor Name,Departments";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) {
        List<Professor> professors = getProfessors("professors.csv");
        List<Student> students = getStudents("students.csv");
        HillClimbing experiment = new HillClimbing();
        Map<Student, Professor> bestMap = experiment.findBestMatches(students, professors);
        String[] files = getMatchesCSVFile(bestMap, experiment.reasons);
        try {
            FileWriter writer = new FileWriter("answer.csv");
            writer.append(FILE_HEADER);
            writer.append(NEW_LINE_SEPARATOR);
            writer.append(files[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter("explanation.csv");
            writer.append(FILE_HEADER);
            writer.append(NEW_LINE_SEPARATOR);
            writer.append(files[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String[] getMatchesCSVFile(Map<Student, Professor> map, Map<Student, String> reasons) {
        String[] files = new String[2];
        String match = "";
        String explanation = "";
        List<Student> students = new ArrayList<Student>(map.keySet());
        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {
            Student student = iterator.next();
            Professor professor = map.get(student);
            String reason = reasons.get(student);
            match = match + student.id + COMMA_DELIMITER;
            match = match + student.last + " " + student.first + COMMA_DELIMITER;
            match = match + student.majors.toString().replaceAll(",", " ") + COMMA_DELIMITER;
            match = match + student.minors.toString().replaceAll(",", " ") + COMMA_DELIMITER;
            match = match + professor.id + COMMA_DELIMITER;
            match = match + professor.last + " " + professor.first + COMMA_DELIMITER;
            match = match + professor.department + COMMA_DELIMITER;
            match = match + String.valueOf(professor.count) + COMMA_DELIMITER;
            match = match + NEW_LINE_SEPARATOR;

            explanation = explanation + student.majors.toString().replaceAll(",", " ") + COMMA_DELIMITER;
            explanation = explanation + professor.department + COMMA_DELIMITER;
            explanation = explanation + reason + COMMA_DELIMITER;
            explanation = explanation + NEW_LINE_SEPARATOR;
        }
        files[0] = match;
        files[1] = explanation;
        return files;
    }

    private static List<Professor> getProfessors(String fileName) {
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();
            List<Professor> profsList = new ArrayList<Professor>();
            List<String[]> infoList = reader.readAll();
            Iterator<String[]> iterator = infoList.iterator();

            while (iterator.hasNext()) {
                String[] info = iterator.next();
                Professor prof = new Professor();
                prof.id = info[0];
                prof.last = info[1];
                prof.first = info[2];
                prof.department = info[4];
                prof.count = Integer.valueOf(info[7]);
                profsList.add(prof);
            }
            return profsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Student> getStudents(String fileName) {
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();
            List<Student> studentList = new ArrayList<Student>();
            List<String[]> infoList = reader.readAll();
            Iterator<String[]> iterator = infoList.iterator();

            while (iterator.hasNext()) {
                String[] info = iterator.next();
                Student student = new Student();

                student.id = info[1];
                student.status = info[2];
                student.last = info[4];
                student.first = info[3];
                student.majors = Arrays.asList(info[7].split(","));
                student.minors = Arrays.asList(info[8].split(","));
                studentList.add(student);
            }
            return studentList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
