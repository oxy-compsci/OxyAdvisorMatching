import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;

public class Main {
    // CONSTANTS
    // needed for writing the output csv files
    private static final String ANSWER_FILE_HEADER = "Student's Name,Majors,Professor's Name,Departments,Count,Reason of Matching";
    private static final String EXPLANATION_FILE_HEADER = "Student's Majors,Professor's Department, Reason of Matching";

    // needed for formatting csv files
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String COMMA_DELIMITER = ",";

    private static final String PROFESSOR_CSV_FILE_NAME = "updated_professors.csv";
    private static final String STUDENT_CSV_FILE_NAME = "students.csv";
    private static final String DISCIPLINE_CSV_FILE_NAME = "disciplines.csv";
    private static final String MATCHES_CSV_FILE_NAME = "matches.csv";
    private static final String EXPLANATIONS_CSV_FILE_NAME = "explanations.csv";

    // Lists for csv files
    public static List<Professor> professors;
    public static List<Student> students;
    public static List<Discipline> disciplines;

    public static void main(String[] args) {
        // Convert CSV files to objects and lists
        disciplines = getDisciplines(DISCIPLINE_CSV_FILE_NAME);
        professors = getProfessors(PROFESSOR_CSV_FILE_NAME);
        students = getStudents(STUDENT_CSV_FILE_NAME);

        // Create experiment and find best matches
        HillClimbing experiment = new HillClimbing();
        Map<Student, Professor> bestMap = experiment.findBestMatches(students, professors);

        // Write CSV file with matches
        writeMatchFile(MATCHES_CSV_FILE_NAME, bestMap, experiment.getExplanations(), true);

        // Write CSV file with explanations
//       writeExplanationFile(EXPLANATIONS_CSV_FILE_NAME, bestMap, experiment.getExplanations());

    }

    private static void writeMatchFile(String filename, Map<Student, Professor> map, Map<Student, String> reasons, boolean includeExplanation) {
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
                if(includeExplanation) {
                    String reason = reasons.get(student);
                    match = match + reason + COMMA_DELIMITER;
                }
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

        List<Student> students = new ArrayList<>(map.keySet());
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
            // Create CSV builder, skip the first line (header)
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();
            List<Professor> profsList = new ArrayList<>();
            List<String[]> infoList = reader.readAll();
            Iterator<String[]> iterator = infoList.iterator();

            while (iterator.hasNext()) {
                String[] info = iterator.next();
                String id, last, first, department;
                int count;

                id = info[0].trim();
                last = info[1].trim();
                first = info[2].trim();
                department = info[4].trim();
                count = Integer.valueOf(info[7].trim());

                Professor prof = new Professor(id, last, first, department, count);
                profsList.add(prof);
            }
            reader.close();
            return profsList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Student> getStudents(String fileName) {
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();
            List<Student> studentList = new ArrayList<>();
            List<String[]> infoList = reader.readAll();
            Iterator<String[]> iterator = infoList.iterator();

            while (iterator.hasNext()) {
                String[] info = iterator.next();
                String id,  status,  last,  first;
                List<String> majors, minors;
                id = info[1].trim();
                status = info[2].trim();
                last = info[4].trim();
                first = info[3].trim();
                majors = Arrays.asList((info[7].replaceAll(", ", ",")).split(","));
                minors = Arrays.asList((info[8].replaceAll(", ", ",")).split(","));
                Student student = new Student(id,status,last,first,majors,minors);
                studentList.add(student);
            }
            reader.close();
            return studentList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Discipline> getDisciplines(String fileName) {
        // Note that formatting strongly matters when reading this CSV file
        // Function currently reads from left to right, aka one row = discipline and list of departments
        // Currently trying to read in file more naturally (aka columns instead of rows)

        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).build();
            List<Discipline> disciplineList = new ArrayList<>();
            List<String[]> infoList = reader.readAll();
            Iterator<String[]> iterator = infoList.iterator();

           while (iterator.hasNext()) {
               String[] info = iterator.next();
               Discipline discipline = new Discipline(info[0], Arrays.copyOfRange(info, 1, info.length));
               System.out.println(Arrays.toString(discipline.list));
               disciplineList.add(discipline);
            }
            reader.close();
            return disciplineList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Edge cases that still need work be caught:
    //

}
