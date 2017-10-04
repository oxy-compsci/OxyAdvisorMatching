
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader("/Users/stephanieangulo/Desktop/cs-stuff/fall-2017/advisorMatch/professors.csv")).withSkipLines(1).build();
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
                profsList.add(prof);
            }

            System.out.println(profsList);

        } catch(IOException e) {
            e.printStackTrace();
        }



    }
}
