import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

public class Main {
    private static final String ANSWER_FILE_HEADER = "Student Name,Majors,Professor Name,Departments,Count";
    private static final String EXPLANATION_FILE_HEADER = "Student's Majors,Professor's Department, Reason of Matching";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) {
        List<Professor> professors = getProfessors("professors.csv");
        List<Student> students = getStudents("students.csv");
        HillClimbing experiment = new HillClimbing();
        Map<Student, Professor> bestMap = experiment.findBestMatches(students, professors);

        writeMatchFile("answer.csv", bestMap);
        writeExplanationFile("explanation.csv", bestMap, experiment.getExplanations());

    }
    private static void writeMatchFile(String filename, Map<Student, Professor> map) {
        List<Student> students = new ArrayList<>(map.keySet());
        Iterator<Student> iterator = students.iterator();
        try {
            FileWriter writer = new FileWriter(filename);
            writer.append(ANSWER_FILE_HEADER);
            writer.append(NEW_LINE_SEPARATOR);
            while(iterator.hasNext()) {
                Student student = iterator.next();
                Professor professor = map.get(student);
                String match = "";
                match = match + student.last + " " + student.first + COMMA_DELIMITER;
                match = match + student.majors.toString().replaceAll(",", " AND ") + COMMA_DELIMITER;
                match = match + professor.last + " " + professor.first + COMMA_DELIMITER;
                match = match + professor.department + COMMA_DELIMITER;
                match = match + professor.count + COMMA_DELIMITER;
                match = match + NEW_LINE_SEPARATOR;
                writer.append(match);
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeExplanationFile(String filename, Map<Student, Professor> map, Map<Student, String> reasons) {
        List<Student> students = new ArrayList<Student>(map.keySet());
        Iterator<Student> iterator = students.iterator();
        try {
            FileWriter writer = new FileWriter(filename);
            writer.append(EXPLANATION_FILE_HEADER);
            writer.append(NEW_LINE_SEPARATOR);
            while(iterator.hasNext()) {
                String explanation = "";
                Student student = iterator.next();
                Professor professor = map.get(student);
                String reason = reasons.get(student);
                explanation = explanation + student.majors.toString().replaceAll("," , " AND ") + COMMA_DELIMITER;
                explanation = explanation + professor.department + COMMA_DELIMITER;
                explanation = explanation + reason + COMMA_DELIMITER;
                explanation = explanation + NEW_LINE_SEPARATOR;
                writer.append(explanation);
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                student.majors = Arrays.asList((info[7].replaceAll(", ", ",")).split(","));
                student.minors = Arrays.asList((info[8].replaceAll(", ", ",")).split(","));
                studentList.add(student);
            }
            return studentList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
