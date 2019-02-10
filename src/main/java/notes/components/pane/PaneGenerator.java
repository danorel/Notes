package notes.components.pane;

import javax.swing.*;
import java.awt.*;

public class PaneGenerator {
    private JTabbedPane tabbedPane;

    public PaneGenerator(){
        tabbedPane = new JTabbedPane();
    }

    public void generate(){
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    public PaneGenerator addTab(String tabName, Component component){
        tabbedPane.addTab(tabName, component);
        return this;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
