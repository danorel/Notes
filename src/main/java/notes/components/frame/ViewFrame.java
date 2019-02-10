package notes.components.frame;

import file.FileGenerator;
import notes.Database;
import notes.components.pane.PaneGenerator;
import notes.components.view.ViewArea;
import notes.components.menu.MenuGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class ViewFrame extends JFrame {
    // Frame width and Frame height
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    // Frame panels as containers of the content and tabbed jTabbedPane
    private JPanel jPanel;
    private JTabbedPane jTabbedPane;

    public ViewFrame(){
        this.setTitle("Dan's Notes");
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void render(){
        jPanel = new JPanel();
        jTabbedPane = new JTabbedPane();
        JMenuBar jMenuBar = constructMenuBar();
        checkupForNotesInDatabase();

        // Adding the tabbed jTabbedPane to the jPanel panel
        jPanel.add(jTabbedPane, BorderLayout.CENTER);

        // Adding the panels and menu to the frame
        this.add(jPanel, BorderLayout.CENTER);
        this.add(jMenuBar, BorderLayout.NORTH);
        this.setVisible(true);
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
                .generate();

        menuGenerator.getMenuItem("Preferences")
                .addActionListener(event -> {
                    JFrame innerJFrame = new JFrame("Preferences");
                    innerJFrame.setSize(WIDTH / 4 * 3, HEIGHT / 4 * 3);
                    innerJFrame.setLocationRelativeTo(this);
                    JPanel innerJPanel = new JPanel();
                    PaneGenerator paneGenerator = new PaneGenerator();
                    paneGenerator
                            .addTab("Font Color", new JTextArea(15, 20))
                            .addTab("Font Size", new JTextArea(15, 20))
                            .addTab("BGR Color", new JTextArea(15, 20))
                            .generate();
                    innerJPanel.add(paneGenerator.getTabbedPane(), BorderLayout.CENTER);
                    innerJFrame.add(innerJPanel, BorderLayout.CENTER);
                    innerJFrame.setVisible(true);
                });
        menuGenerator.getMenuItem("Close")
                .addActionListener(event -> System.exit(0));
        menuGenerator.getMenuItem("Open")
                .addActionListener(event -> {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try {
                            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
                            String line;
                            StringBuilder content = new StringBuilder();
                            while((line = reader.readLine()) != null){
                                content.append(line).append("\n");
                            }
                            ViewArea area = new ViewArea();
                            area.generate();
                            area.append(content.toString());
                            area.setBackground(area.getBackground());
                            jTabbedPane.add("Note", area);
                            reader.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
        menuGenerator.getMenuItem("Create")
                .addActionListener(event -> {
                    ViewArea area = new ViewArea();
                    area.generate();
                    jTabbedPane.add(area);
                    jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
                    String title = JOptionPane.showInputDialog("Define the title for the note");
                    jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), (title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase()));
                });
        menuGenerator.getMenuItem("Delete")
                .addActionListener(event -> {
                    for (File file : Objects.requireNonNull(Database.getExistingNotes())) {
                        String[] tokens = file.getName().split("[./]");
                        if ((tokens[0].substring(0, 1).toUpperCase() + tokens[0].substring(1).toLowerCase()).equals(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex()))) {
                            file.delete();
                            jTabbedPane.remove(jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex()));
                            break;
                        }
                    }
                });
        menuGenerator.getMenuItem("Edit")
                .addActionListener(event -> {
                    ((JTextArea) jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex())).setEditable(true);
                });
        menuGenerator.getMenuItem("Rename")
                .addActionListener(event -> {
                    String title = JOptionPane.showInputDialog("Define the title for the note");
                    Objects.requireNonNull(Database.getExistingNotes())
                            .forEach(file -> {
                                String []tokens = file.getName().split("[./]");
                                if((tokens[0].substring(0, 1).toUpperCase() + tokens[0].substring(1).toLowerCase()).equals(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex()))){
                                    file = new File(Database.getPath() + "/" + title);
                                    try {
                                        BufferedWriter writer = new BufferedWriter(new FileWriter(Database.getPath() + "/" + title.toLowerCase()));
                                        writer.write(((ViewArea) jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex())).getText());
                                        writer.close();
                                    } catch (IOException exception) {
                                        exception.printStackTrace();
                                    }
                                    jTabbedPane.setTitleAt(jTabbedPane.getSelectedIndex(), (title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase()));
                                }
                            });
                });
        menuGenerator.getMenuItem("Save")
                .addActionListener(event -> {
                    final boolean[] isFound = { false };
                    Objects.requireNonNull(Database.getExistingNotes())
                            .forEach(file -> {
                                String []tokens = file.getName().split("[./]");
                                if((tokens[0].substring(0, 1).toUpperCase() + tokens[0].substring(1).toLowerCase()).equals(jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex()))){
                                    try {
                                        ViewArea area = ((ViewArea) jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex()));
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
                        File file = new File(Database.getPath() + "/" + jTabbedPane.getTitleAt(jTabbedPane.getSelectedIndex()).toLowerCase() + ".txt");
                        try {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
                            writer.write(((ViewArea) jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex())).getText());
                            writer.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        Database.getExistingNotes().add(file);
                    }
                    ((ViewArea) jTabbedPane.getComponentAt(jTabbedPane.getSelectedIndex())).setEditable(false);
                });
        return menuGenerator.getMenuBar();
    }

    private void checkupForNotesInDatabase(){
        Database.notes = FileGenerator.getFilesFromDirectory(Database.getPath());
        Objects.requireNonNull(Database.getExistingNotes())
                .forEach(file -> {
                    String []tokens = file.getName().split("[./]");
                    ViewArea area = new ViewArea(tokens[0].substring(0, 1).toUpperCase() + tokens[0].substring(1).toLowerCase());
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
                    jTabbedPane.add(area);
                });
    }
}