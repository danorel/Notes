package notes.components.menu;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuGenerator {

    // Menu bar items and notes.menuBar bar itself
    private JMenuBar menuBar;
    private ArrayList<JMenu> menus;
    private ArrayList<JMenuItem> menuItems;

    public MenuGenerator() {
        menuBar = new JMenuBar();
        menus = new ArrayList<>();
        menuItems = new ArrayList<>();
    }

    public void generate(){
        menus
                .forEach(item -> {
                    menuBar.add(item);
                });
    }

    public MenuGenerator defineMenu(String fieldName){
        menus.add(new JMenu(fieldName));
        return this;
    }

    public MenuGenerator defineMenus(List<String> fieldNames){
        fieldNames
                .forEach(fieldName -> {
                    JMenu currentMenu = new JMenu(fieldName);
                    menus.add(currentMenu);
                });
        return this;
    }

    public MenuGenerator defineMenuItemTo(String menuName, String fieldName) {
        menus
                .forEach(menuElement -> {
                    if(menuElement.getText().equals(menuName)){
                        JMenuItem field = new JMenuItem(fieldName);
                        menuElement.add(field);
                        menuItems.add(field);
                    }
                });
        return this;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JMenuItem getMenuItem(String fieldName){
        JMenuItem []items = new JMenuItem[1];
        menuItems
                .forEach(field -> {
                    if(field.getText().equals(fieldName)){
                        items[0] = field;
                    }
                });
        return items[0];
    }

    /*
    public MenuGenerator addActionListener(String fieldName, String listenerName){
        menuItems
                .forEach(field -> {
                    if(field.getText().equals(fieldName)){
                        field.addActionListener(getActionListener(listenerName));
                    }
                });
        return this;
    }

    private ActionListener getActionListener(String listenerName){
        for (ActionListener listener : actionListeners) {
            if (listener.getClass().getSimpleName().equals(listenerName)) {
                return listener;
            }
        }
        return null;
    }
    */
}
