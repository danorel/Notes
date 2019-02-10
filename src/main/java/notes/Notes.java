package notes;

import notes.frame.NotesFrame;

public class Notes {
    private static Database database;
    private NotesFrame frame;

    public Notes(){
        database = new Database();
        frame = new NotesFrame();
        frame.render();
    }

    public Notes(String path){
        database = new Database(path);
        frame = new NotesFrame();
        frame.render();
    }

    public static Database getDatabase(){
        return database;
    }
}
