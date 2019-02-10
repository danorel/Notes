package notes;

import notes.components.frame.ViewFrame;

public class Notes {
    private static Database database;
    private ViewFrame frame;

    public Notes(){
        database = new Database();
        frame = new ViewFrame();
        frame.render();
    }

    public Notes(String path){
        database = new Database(path);
        frame = new ViewFrame();
        frame.render();
    }

    public static Database getDatabase(){
        return database;
    }
}
