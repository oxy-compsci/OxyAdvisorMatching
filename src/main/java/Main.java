
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;
import com.sun.scenario.effect.impl.prism.PrFilterContext;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Professor> profsList = getProfessors("/Users/stephanieangulo/Desktop/cs-stuff/fall-2017/advisorMatch/professors.csv");
        List<Student> studentList = getStudents("/Users/stephanieangulo/Desktop/cs-stuff/fall-2017/advisorMatch/students.csv");

//        for(int i = 0; i < studentList.size(); i++)
//            System.out.println(studentList.get(i).majors);
//        System.out.println("-----------------");
//        for(int i = 0; i < profsList.size(); i++)
//            System.out.println(profsList.get(i).department);

//        List<Student> studentList = getStudents("/Users/stephanieangulo/Desktop/cs-stuff/fall-2017/advisorMatch/teststudent.csv");
//        List<Professor> profsList = getProfessors("/Users/stephanieangulo/Desktop/cs-stuff/fall-2017/advisorMatch/testprofessor.csv");
        hillClimbing(profsList, studentList);
    }
    public static List getProfessors(String fileName) {
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
    public static List getStudents(String fileName) {
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


    public static void hillClimbing(List<Professor> professors, List<Student> students) {
        int generation = 0;
        Map<Student, Professor> studentProfMatches = new HashMap<Student, Professor>();
        Professor nextProf;

        while(generation < 100) {
            for(int i = 0; i < students.size(); i++) {
                int maxMatch = 0;
                Professor bestProf;




//                Student unmatchedStudent = new Student();
//                for(int j = 0; j < professors.size(); j++) {
//                    int currentMatch = getScore(professors.get(j), students.get(i));
//                    if(currentMatch > maxMatch) {
//                        maxMatch = currentMatch;
//                        bestProf = professors.get(j);
//                    }
//                }
//                if(bestProf == null) {
//                    if(studentProfMatches.containsValue())
//                }
                //studentProfMatches.put(students.get(i), bestProf);
            }
            generation++;
        }
        int count = 0;

//        for (Map.Entry<Student, Professor> entry : studentProfMatches.entrySet()) {
//            if(entry.getValue().first == null) {
//                count++;
//            }
//            System.out.println( entry.getKey().last +",  " + entry.getKey().majors + " : " +
//                    entry.getValue().last + ", " + entry.getValue().department);
//        }
//        System.out.println("Number of entries null: " + count);

    }


}
