package notes.frame;

import notes.area.NotesArea;
import notes.listeners.action.ListenerConstants;
import notes.menu.MenuGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class NotesFrame extends JFrame {
    // Frame width and Frame height
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    // Pointer to the frame object
    private JFrame frame = this;

    // Frame panels as containers of the content and tabbed pane
    private JPanel main;

    private JTabbedPane pane;

    public NotesFrame(){
        this.setTitle("Dan's Notes");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void render(){

        // Menu bar and embedded notes.menu items initialization
        JMenuBar menuBar = constructMenuBar();
        NotesArea area = new NotesArea();
        area.generate();
        // Main panel initialization
        main = new JPanel();
        // Tabbed pane initialization
        pane = new JTabbedPane();
        pane.add(area);
//        checkForExistingNotes();
        // Adding the tabbed pane to the main panel
        main.add(pane, BorderLayout.CENTER);
        // Adding the panels and notes.menu to the frame
        this.add(main, BorderLayout.CENTER);
        this.add(menuBar, BorderLayout.NORTH);
        this.setVisible(true);
    }

    private JTextArea constructTextArea(){
        NotesArea area = new NotesArea();
        area.generate();
        return area;
    }

    private JMenuBar constructMenuBar(){
        MenuGenerator menuGenerator = new MenuGenerator();
        menuGenerator
                .defineMenus(Arrays.asList("Notes", "Tools"))
                .defineMenuItemTo("Notes", "Preferences")
                .defineMenuItemTo("Notes", "Close")
                .defineMenuItemTo("Tools", "Open")
                .defineMenuItemTo("Tools", "Create")
                .defineMenuItemTo("Tools", "Edit")
                .defineMenuItemTo("Tools", "Save")
                .defineMenuItemTo("Tools", "Rename")
                .defineMenuItemTo("Tools", "Delete")
                .addActionListener("Save", ListenerConstants.SAVE)
                .addActionListener("Close", ListenerConstants.CLOSE)
                .addActionListener("Open", ListenerConstants.OPEN)
                .addActionListener("Create", ListenerConstants.CREATE)
                .generate();
        return menuGenerator.getMenuBar();
    }
}