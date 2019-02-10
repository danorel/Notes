package notes.tabbedpane;

import notes.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PaneGenerator {
    public void generate(){
        Objects.requireNonNull(Database.getExistingNotes())
                .forEach(file -> {
                    ArrayList<String> parts = (ArrayList<String>) Arrays.asList(file.getName().split("."));
                    parts.get(0); // the name of the file
                });
    }
}
