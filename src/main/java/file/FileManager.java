package file;

import java.io.File;
import java.util.ArrayList;

public interface FileManager {
    void createFile(String src);
    void createDirectory(String name);
    String readFile(String src);
    void writeFile(String src, String content);
    ArrayList<File> getFilesFromDirectory(String src);
    ArrayList<String> getFilenamesFromDirectory(String src);
}
