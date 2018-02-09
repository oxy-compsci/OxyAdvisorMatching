import java.util.ArrayList;
import java.util.Arrays;

public class Discipline {
    String name;
    ArrayList<String> list;
    public Discipline(String disciplineName, ArrayList<String> disciplineList) {
        this.name = disciplineName;
        this.list = clean(disciplineList);
    }
    public ArrayList<String> clean(ArrayList<String> list) {
        // Removes empty elements
        list.removeAll(Arrays.asList(null,""));
        return list;

    }
}
