import java.util.Arrays;

public class Data {
    static String[] bio = new String[]{"Biochemistry", "Chemistry", "Geology","Kinesiology"};
    static String[] biochem = new String[]{"Biology", "Chemistry", "Geology","Kinesiology"};
    static String[] chem = new String[]{"Biology", "Chemistry", "Geology","Kinesiology", "Physics"};
    static String[] cogsci = new String[]{"Computer Science", "Philosophy", "Psychology"};
    static String[] compsci = new String[]{"Cognitive Science", "Mathematics"};

    String department, intendedMajor;

    public Data(String department, String intendedMajor) {
        this.department = department;
        this.intendedMajor = intendedMajor;
    }
    public boolean isRelatedField() {
        if(intendedMajor.equalsIgnoreCase("Biology")) {
            return Arrays.asList(bio).contains(department);
        } else if(intendedMajor.equalsIgnoreCase("Biochemistry")) {
            return Arrays.asList(biochem).contains(department);
        } else if(intendedMajor.equalsIgnoreCase("Chemistry")) {
            return Arrays.asList(chem).contains(department);
        } else if(intendedMajor.equalsIgnoreCase("Cognitive Science")) {
            return Arrays.asList(cogsci).contains(department);
        } else if(intendedMajor.equalsIgnoreCase("Computer Science")) {
            return Arrays.asList(compsci).contains(department);
        }
        return false;
    }

    /*  List of departments:
    American Studies
    Art and Art History
    Biochemistry
    Biology
    Chemistry
    Chinese
    Classical Studies (minor)
    Cognitive Science
    Comparative Studies in Literature and Culture
    Computer Science
    Critical Theory and Social Justice
    Diplomacy and World Affairs
    East Asian Languages and Cultures
    Education (minor)
    English
    French
    Gender, Women, and Sexuality Studies (minor)
    Geology
    German
    Group Language
    History
    Interdisciplinary Writing
    Japanese
    Kinesiology
    Latino/a and Latin American Studies
    Linguistics (minor)
    Mathematics
    Media Arts and Culture
    Music
    Neuroscience (minor)
    Philosophy
    Physics
    Politics
    Psychology
    Public Health (minor)
    Religious Studies
    Russian (minor)
    Sociology
    Spanish
    Theater
    Urban and Environmental Policy*/
}
