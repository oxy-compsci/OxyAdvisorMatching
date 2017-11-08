import java.util.Arrays;

public class Data {
    String[] bio = new String[]{"Biochemistry", "Chemistry", "Geology","Kinesiology"};
    String[] biochem = new String[]{"Biology", "Chemistry", "Geology","Kinesiology"};
    String[] chem = new String[]{"Biology", "Chemistry", "Geology","Kinesiology", "Physics"};
    String[] compsci = new String[]{"Cognitive Science", "Mathematics"};


    public boolean isRelatedField(String intendedMajor, String department) {
        if(intendedMajor.equalsIgnoreCase("Biology")) {
            return Arrays.asList(bio).contains(department);
        } else if(intendedMajor.equalsIgnoreCase("Biochemistry")) {
            return Arrays.asList(biochem).contains(department);
        } else if(intendedMajor.equalsIgnoreCase("Chemistry")) {
            return Arrays.asList(chem).contains(department);
        } else if(intendedMajor.equalsIgnoreCase("Computer Science")) {
            return Arrays.asList(compsci).contains(department);
        }

        return false;
    }

    /*    American Studies
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
    Theater*
    Urban and Environmental Policy*/
}
