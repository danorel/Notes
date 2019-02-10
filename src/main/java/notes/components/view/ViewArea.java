package notes.components.view;

import javax.swing.*;
import java.awt.*;

public class ViewArea extends JTextArea {
    private final static int AREA_COLUMNS = 60;
    private final static int AREA_ROWS = 25;
    private final static Color BACKGROUND_COLOR = new Color( 178,192, 190);
    private final static Color FONT_COLOR = new Color(255-178, 255-192, 255-190);
    private final static Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    public ViewArea(){
        super();
        this.setName("Note");
        this.setColumns(AREA_COLUMNS);
        this.setRows(AREA_ROWS);
    }

    public ViewArea(String title){
        super();
        this.setName(title);
        this.setColumns(AREA_COLUMNS);
        this.setRows(AREA_ROWS);
    }


    public void generate(){
        this.setBackground(BACKGROUND_COLOR);
        this.setFont(FONT);
        this.setForeground(FONT_COLOR);
    }






}
