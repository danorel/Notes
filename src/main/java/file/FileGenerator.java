package file;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface FileGenerator {
    static void createDirectory(String name){
        File directory = new File(name);
        directory.mkdir();
    }

    static void createFile(String src){
        final String[] position = {""};
        List<String> path = Arrays.asList(src.split("/"));
        path.stream()
                .forEach(part -> {
                    try {
                        if(part.equals(path.get(path.size() - 1))) {
                            File element = new File(position[0] + "/" +part);
                            element.createNewFile();
                        } else {
                            position[0] += part + "/";
                            File element = new File(position[0].substring(0, position[0].length() - 1));
                            element.mkdir();
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });
    }

    static String readFile(String src){
        String content = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(src));
            String line;
            while((line = reader.readLine()) != null){
                content += line + "\n";
            }
            reader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return content;
    }

    static void writeFile(String src, String content){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(src));
            writer.write(content);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static ArrayList<File> getFilesFromDirectory(String src){
        return new ArrayList<> (Arrays.asList(Objects.requireNonNull(new File(src).listFiles())));
    }

    static ArrayList<String> getFilenamesFromDirectory(String src){
        ArrayList<String> filenames = new ArrayList<>();
        getFilesFromDirectory(src)
                .forEach(file -> {
                    filenames.add(file.getName());
                });
        return filenames;
    }
}
