public class Discipline {
    String name;
    String[] list;
    public Discipline(String disciplineName, String[] disciplineList) {
        this.name = disciplineName;
        this.list = clean(disciplineList);
    }
    public String[] clean(String[] list) {
        // Removes whitespace
        // Important for checking relation (see method in Professor class)
        for(int i = 0; i < list.length; i++)
            list[i] = list[i].trim();
        return list;
    }
}
