package notes.area;

import javax.swing.*;
import java.awt.*;

public class NotesArea extends JTextArea {
    private final static int AREA_COLUMNS = 37;
    private final static int AREA_ROWS = 23;
    private final static Color BACKGROUND_COLOR = new Color(0xB2C0BE);
    private final static Color FONT_COLOR = new Color(0x8B00DC);

    public NotesArea(){
        super();
        this.setName("Note");
        this.setColumns(AREA_COLUMNS);
        this.setRows(AREA_ROWS);
    }

    public NotesArea(String title){
        super();
        this.setName(title);
        this.setColumns(AREA_COLUMNS);
        this.setRows(AREA_ROWS);
    }


    public void generate(){
        this.setBackground(BACKGROUND_COLOR);
//        this.setFont(new Font());
    }






}
