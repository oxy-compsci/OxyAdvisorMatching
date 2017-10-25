
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Professor> professors = getProfessors("/Users/stephanieangulo/Desktop/cs-stuff/fall-2017/advisorMatch/professors.csv");
        List<Student> students = getStudents("/Users/stephanieangulo/Desktop/cs-stuff/fall-2017/advisorMatch/students.csv");
        HillClimbing experiment = new HillClimbing();
        Map<Student, Professor> bestMap = experiment.findBestMatches(students, professors);
//        for (Map.Entry<Student, Professor> entry : bestMap.entrySet()) {
//            System.out.println( entry.getKey().last +",  " + entry.getKey().majors + " : " +
//                    entry.getValue().last + ", " + entry.getValue().department);
//        }
    }
    private static List getProfessors(String fileName) {
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();
            List<Professor> profsList = new ArrayList<Professor>();
            List<String[]> infoList = reader.readAll();
            Iterator<String[]> iterator = infoList.iterator();

            while(iterator.hasNext()) {
                String[] info = iterator.next();
                Professor prof = new Professor();
                prof.id = info[0];
                prof.last = info[1];
                prof.first = info[2];
                prof.department = info[4];
                prof.count = Integer.valueOf(info[7]);
                profsList.add(prof);
            }
            Collections.shuffle(profsList);
            return profsList;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static List getStudents(String fileName) {
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).withSkipLines(1).build();
            List<Student> studentList = new ArrayList<Student>();
            List<String[]> infoList = reader.readAll();
            Iterator<String[]> iterator = infoList.iterator();

            while(iterator.hasNext()) {
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
            Collections.shuffle(studentList);
            return studentList;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
