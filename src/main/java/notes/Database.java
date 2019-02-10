package notes;

import file.FileGenerator;

import java.nio.file.Files;
import java.util.ArrayList;

public class Database {

    /*
        The default location of the created notes
     */
    private static String path = "data";

    /*
        All the notes created are located inside the ArrayList
     */
    private static ArrayList<Files> notes;

    public Database(){
        notes = new ArrayList<>();
        FileGenerator.createDirectory(path);
    }

    public Database(String path){
        this.path = path;
        FileGenerator.createDirectory(this.path);
        notes = new ArrayList<>();
    }

    public static String getPath() {
        return path;
    }

    public static ArrayList<Files> getExistingNotes() {
        return notes.size() == 0 ? null : notes;
    }
}
