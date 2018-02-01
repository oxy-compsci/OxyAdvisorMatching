import java.util.Arrays;

public class Data {
    static String[] sciences = new String[]{"Biochemistry", "Biology", "Chemistry", " Cognitive Science",
        "Computer Science", "Geology", "Kinesiology","Mathematics", "Physics"};

    static String[] socialSciences = new String[]{"Psychology", "Sociology", "Urban and Environmental Policy",
            "Urban & Environmental Policy", "Urban/Environmental Policy", "Economics","History", "Philosophy",
            "Politics", "American Studies", "Diplomacy & World Affairs", "Diplomacy and World Affairs",
            "Latino/a and Latin American Studies", "Latino/a & Latin American Studies"};

    static String[] artsAndHumanities = new String[] {"Art and Art History","Art & Art History","Media Arts and Culture",
            "Media Arts & Culture", "Critical Theory and Social Justice", "Critical Theory & Social Justice",
            "Critical Theory/Social Justice", "English", "Chinese", "French", "German", "Japanese", "Music",
            "Religious Studies", "Spanish", "Theater", "East Asian Languages and Cultures",
            "Comparative Studies in Literature and Culture", "Comparative Studies in Literature & Culture",
            "Comparative Studies/Lit & Cult", "East Asian Languages/Cultures", "East Asian Languages & Cultures","Group Language"};

    String department, intendedMajor;

    public Data(String department, String intendedMajor) {
        this.department = department;
        this.intendedMajor = intendedMajor;
    }
    public boolean isRelatedField() {
        if(Arrays.asList(sciences).contains(intendedMajor)) {
            return Arrays.asList(sciences).contains(department);
        } else if(Arrays.asList(socialSciences).contains(intendedMajor)) {
            return Arrays.asList(socialSciences).contains(department);
        } else if(Arrays.asList(artsAndHumanities).contains(intendedMajor)) {
            return Arrays.asList(artsAndHumanities).contains(department);
        }
        return false;
    }
}
