package guimvc;

import characters.Student;
import roomanddoor.*;
import items.Item;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;


public class Controller implements KeyListener {
    private final Game game;
    private final GameView view;
    private String playerOneAction = null;
    private boolean playerOneNextMenu = false;
    private String playerTwoAction = null;
    private boolean playerTwoNextMenu = false;
    private Student currentStudent = null;
    public Controller(Game game, GameView view) {
        this.game = game;
        this.view = view;
        this.view.setVisible(true);
        this.view.addClassAsKeyListener(this);
        this.game.setViewObserver(view);
        this.game.setGameObservers();
        this.game.setVanishObservers();
        this.game.startTimers();
    }
    @Override
    public void keyTyped(KeyEvent e) { /* The Game only works when key is Pressed */ }

    @Override
    public void keyPressed(KeyEvent e) {
        char ch = e.getKeyChar();
        int i = -1;
        List<Student> students = this.game.getStudents();
        List<JList<String>> menus = this.view.getMenus();

        if(ch == 'q' || ch == 'Q' || ch == 'h' || ch == 'H') {
            if(!students.isEmpty() && (ch == 'q' || ch == 'Q')) {
                i = 0;
            }

            if(students.size() >= 2 && (ch == 'h' || ch == 'H')) {
                i = 1;
            }

            if(i != -1) {
                JList<String> actualMenu = menus.get(i);
                int idx = actualMenu.getSelectedIndex();
                int newIdx = idx + 1;
                int max = actualMenu.getModel().getSize() - 1;

                if (idx == -1)
                    return;

                if (idx == max) {
                    actualMenu.setSelectedIndex(0);
                } else {
                    actualMenu.setSelectedIndex(newIdx);
                }
            }
        }

        if(ch == 'w' || ch == 'W' || ch == 'j' || ch == 'J') {
            if(!students.isEmpty() && (ch == 'w' || ch == 'W')) {
                i = 0;
            }

            if(students.size() >= 2 && (ch == 'j' || ch == 'J')) {
                i = 1;
            }

            if(i != -1) {
                JList<String> actualMenu = menus.get(i);
                int idx = actualMenu.getSelectedIndex();
                int newIdx = idx - 1;

                if (idx == -1)
                    return;

                if (idx == 0) {
                    int maxIdx = actualMenu.getModel().getSize() - 1;
                    actualMenu.setSelectedIndex(maxIdx);
                } else {
                    actualMenu.setSelectedIndex(newIdx);
                }
            }
        }

        if(ch == 'e' || ch == 'E' || ch == 'k' || ch == 'K') {
            if(!students.isEmpty() && (ch == 'e' || ch == 'E')) {
                i = 0;
                currentStudent = students.get(0);
            }

            if(students.size() >= 2 && (ch == 'k' || ch == 'K')) {
                i = 1;
                currentStudent = students.get(1);
            }


            if(i == 0) {
                setModelByInput(playerOneNextMenu, playerOneAction, i);
            }
            if(i == 1) {
                setModelByInput(playerTwoNextMenu, playerTwoAction, i);
            }
        }

        if(ch == 'r' || ch == 'R' || ch == 'l' || ch == 'L') {
            if(ch == 'r' || ch == 'R') {
                i = 0;
            }
            if(ch == 'l' || ch == 'L') {
                i = 1;
            }

            if(!students.isEmpty() && (ch == 'r' || ch == 'R' && (playerOneNextMenu))) {
                currentStudent = students.get(i);
                this.view.setDefaultMenu(currentStudent, i);
                playerOneNextMenu = false;
            }

            if(students.size() >= 2 && (ch == 'l' || ch == 'L' && (playerTwoNextMenu))) {
                currentStudent = students.get(i);
                this.view.setDefaultMenu(currentStudent, i);
                playerTwoNextMenu = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { /* The Game only works when key is Pressed */ }

    private void setModelByInput(boolean isNextMenu, String action, int i) {
        List<JList<String>> menus = this.view.getMenus();
        JList<String> actualMenu = menus.get(i);

        Room currentRoom = currentStudent.getPosition();

        String value;
        value = actualMenu.getSelectedValue();
        if(!isNextMenu) {
            if(value != null) {
                if (i == 0) {
                    playerOneAction = value;
                    playerOneNextMenu = !playerOneNextMenu;
                }
                if (i == 1) {
                    playerTwoAction = value;
                    playerTwoNextMenu = !playerTwoNextMenu;
                }

                this.view.setMenu(value, currentStudent, i);
            }
        } else {
            int idx;
            idx = actualMenu.getSelectedIndex();
            if(idx >= 0 && action != null) {
                switch (action) {
                    case "MOVE":
                        List<Door> doors = currentRoom.getNeighbourDoors();
                        game.move(currentStudent, doors.get(idx));
                        break;
                    case "USE ITEM":
                        List<Item> itemsInInv = currentStudent.getInventory();
                        game.use(currentStudent, itemsInInv.get(idx));
                        break;
                    case "PICK UP ITEM":
                        List<Item> itemsInRoom = currentRoom.getItems();
                        game.pickUp(currentStudent, itemsInRoom.get(idx));
                        break;
                    case "DROP ITEM":
                        List<Item> itemsInInv2 = currentStudent.getInventory();
                        game.drop(currentStudent, itemsInInv2.get(idx));
                        break;
                    default:
                        break;
                }

                this.view.setDefaultMenu(currentStudent, i);

                if (i == 0) {
                    playerOneAction = null;
                    playerOneNextMenu = !playerOneNextMenu;
                }
                if (i == 1) {
                    playerTwoAction = null;
                    playerTwoNextMenu = !playerTwoNextMenu;
                }
            }
        }

        this.view.packChanges();
    }
}
