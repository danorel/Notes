package file;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileGenerator implements FileManager {
    private String position = "";

    public void createFile(String src) {
        List<String> path = Arrays.asList(src.split("/"));
        path.stream()
                .forEach(part -> {
                    try {
                        if(part.equals(path.get(path.size() - 1))) {
                            File element = new File(position + "/" +part);
                            element.createNewFile();
                        } else {
                            position += part + "/";
                            File element = new File(position.substring(0, position.length() - 1));
                            element.mkdir();
                        }
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });
    }

    @Override
    public void createDirectory(String name) {
        File directory = new File(name);
        directory.mkdir();
    }

    public String readFile(String src) {
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

    public void writeFile(String src, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(src));
            writer.write(content);
            writer.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public ArrayList<File> getFilesFromDirectory(String src) {
        return new ArrayList<> (Arrays.asList(Objects.requireNonNull(new File(src).listFiles())));
    }

    @Override
    public ArrayList<String> getFilenamesFromDirectory(String src) {
        ArrayList<String> filenames = new ArrayList<>();
        getFilesFromDirectory(src)
                .forEach(file -> {
                    filenames.add(file.getName());
                    System.out.println(file.getName());
                });
        return filenames;
    }
}
