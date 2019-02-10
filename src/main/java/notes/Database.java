package notes;

import file.FileGenerator;

import java.io.File;
import java.util.ArrayList;

public class Database {

    /*
        The default location of the created notes
     */
    private static String path = "data";

    /*
        All the notes created are located inside the ArrayList
     */
    public static ArrayList<File> notes = new ArrayList<>();

    public Database(){
        FileGenerator.createDirectory(path);
    }

    public Database(String path){
        this.path = path;
        FileGenerator.createDirectory(this.path);
    }

    public static String getPath() {
        return path;
    }

    public static ArrayList<File> getExistingNotes() {
        return notes.size() == 0 ? null : notes;
    }
}
