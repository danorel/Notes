import file.FileGenerator;
import notes.Notes;

public class App {
    public static void main(String[] args) {
//        Notes note = new Notes();
        FileGenerator fileGenerator = new FileGenerator();
        fileGenerator.createFile("data/task.txt");
        fileGenerator.writeFile("data/task.txt", "Hello, world!");
        System.out.println(fileGenerator.readFile("data/task.txt"));

        fileGenerator.getFilenamesFromDirectory("data").stream().forEach(name -> System.out.println(name));
    }
}
