package notes.frame;

import file.FileGenerator;
import notes.Database;
import notes.area.NotesArea;
import notes.listeners.action.ListenerConstants;
import notes.menu.MenuGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NotesFrame extends JFrame {
    // Frame width and Frame height
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    // Frame panels as containers of the content and tabbed tabbedPane
    private JPanel main;
    private JTabbedPane tabbedPane;

    public NotesFrame(){
        this.setTitle("Dan's Notes");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void render(){
        // Menu bar and embedded notes.menu items initialization
        JMenuBar menuBar = constructMenuBar();
        // Main panel initialization
        main = new JPanel();
        // Tabbed tabbedPane initialization
        tabbedPane = new JTabbedPane();

        checkupForNotesInDatabase();
        // Adding the tabbed tabbedPane to the main panel
        main.add(tabbedPane, BorderLayout.CENTER);

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

//    private JTabbedPane constructTabbedPane(){
//
//    }

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
                .generate();

        menuGenerator.getMenuItem("Close")
                .addActionListener(event -> System.exit(0));
        menuGenerator.getMenuItem("Open")
                .addActionListener(event -> {
                    JFileChooser fileChooser = new JFileChooser();
                    Frame innerFrame = new JFrame();
                    innerFrame.setLocationRelativeTo(this);
                    if (fileChooser.showOpenDialog(innerFrame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
                            String line;
                            String content = "";
                            while((line = reader.readLine()) != null){
                                content += line + "\n";
                            }
                            NotesArea area = new NotesArea();
                            area.generate();
                            area.append(content);
                            area.setBackground(area.getBackground());
                            tabbedPane.add("Note", area);
                            reader.close();
                        } catch (FileNotFoundException exception) {
                            exception.printStackTrace();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
        menuGenerator.getMenuItem("Create")
                .addActionListener(event -> {
                    NotesArea area = new NotesArea();
                    area.generate();
                    tabbedPane.add(area);
                    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                });
        menuGenerator.getMenuItem("Delete")
                .addActionListener(event -> {
                    Objects.requireNonNull(Database.getExistingNotes())
                            .forEach(file -> {
                                if(file.getName().equals(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()))){
                                    tabbedPane.remove(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()));
                                    Database.getExistingNotes().remove(file);
                                }
                            });
                });
        menuGenerator.getMenuItem("Edit")
                .addActionListener(event -> {
                    ((JTextArea) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).setEditable(true);
                });
        menuGenerator.getMenuItem("Rename")
                .addActionListener(event -> {
                    Objects.requireNonNull(Database.getExistingNotes())
                            .forEach(file -> {
                                if(file.getName().equals(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()))){
                                    tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex()).requestFocus();
                                    System.out.println(tabbedPane.getTabComponentAt(tabbedPane.getSelectedIndex()).getName());
                                }
                            });
                });
        menuGenerator.getMenuItem("Save")
                .addActionListener(event -> {
                    final boolean[] isFound = { false };
                    Objects.requireNonNull(Database.getExistingNotes())
                            .forEach(file -> {
                                if(file.getName().equals(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()))){
                                    try {
                                        NotesArea area = ((NotesArea) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()));
                                        area.generate();
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
                                        writer.write(area.getText());
                                        writer.close();
                                        isFound[0] = true;
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                    if(!isFound[0]){
                        File file = new File(Database.getPath() + "/" + tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()));
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
                            writer.write(((NotesArea) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).getText());
                            writer.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        Database.getExistingNotes().add(file);
                    }
                    ((NotesArea) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex())).setEditable(false);
                });
        return menuGenerator.getMenuBar();
    }

    private void checkupForNotesInDatabase(){
        Database.notes = FileGenerator.getFilesFromDirectory(Database.getPath());
        Objects.requireNonNull(Database.getExistingNotes())
                .forEach(file -> {
                    String []parts = file.getName().split("\\.|/");
                    NotesArea area = new NotesArea(parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1).toLowerCase());
                    area.generate();
                    StringBuilder content = new StringBuilder();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
                        String line;
                        while((line = reader.readLine()) != null){
                            content.append(line).append("\n");
                        }
                        reader.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    area.append(content.toString());
                    tabbedPane.add(area);
                });
    }
}