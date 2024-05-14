import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        this.game.startTimers();
    }
    @Override
    public void keyTyped(KeyEvent e) { /* The Game only works when key is Pressed */ }

    @Override
    public void keyPressed(KeyEvent e) {
        char ch = e.getKeyChar();
        int i = -1;

        if(ch == 'q' || ch == 'Q' || ch == 'h' || ch == 'H') {
            if(ch == 'q' || ch == 'Q') {
                i = 0;
            }
            if(ch == 'h' || ch == 'H') {
                i = 1;
            }

            List<JList<String>> menus = this.view.getMenus();
            JList<String> actualMenu = menus.get(i);
            int idx = actualMenu.getSelectedIndex();
            int newIdx = idx+1;
            int max = actualMenu.getModel().getSize() - 1;

            if(idx == -1)
                return;

            if(idx == max) {
                actualMenu.setSelectedIndex(0);
            } else {
                actualMenu.setSelectedIndex(newIdx);
            }
        }

        if(ch == 'w' || ch == 'W' || ch == 'j' || ch == 'J') {
            if(ch == 'w' || ch == 'W') {
                i = 0;
            }
            if(ch == 'j' || ch == 'J') {
                i = 1;
            }

            List<JList<String>> menus = this.view.getMenus();
            JList<String> actualMenu = menus.get(i);
            int idx = actualMenu.getSelectedIndex();
            int newIdx = idx-1;

            if(idx == -1)
                return;

            if(idx == 0) {
                int maxIdx = actualMenu.getModel().getSize() - 1;
                actualMenu.setSelectedIndex(maxIdx);
            } else {
                actualMenu.setSelectedIndex(newIdx);
            }
        }

        if(ch == 'e' || ch == 'E' || ch == 'k' || ch == 'K') {
            List<Student> students = this.game.getStudents();

            if(ch == 'e' || ch == 'E') {
                i = 0;
                currentStudent = students.get(0);
            }
            if(ch == 'k' || ch == 'K') {
                i = 1;
                currentStudent = students.get(1);
            }


            if(i == 0) {
                setModelByInput(playerOneNextMenu, playerOneAction, i);
            }
            if(i == 1) {
                setModelByInput(playerTwoNextMenu, playerTwoAction, i);
            }

            this.view.packChanges();
        }

        if(ch == 'r' || ch == 'R' || ch == 'l' || ch == 'L') {
            if(ch == 'r' || ch == 'R') {
                i = 0;
            }
            if(ch == 'l' || ch == 'L') {
                i = 1;
            }

            if(ch == 'r' || ch == 'R') {
                if(playerOneNextMenu) {
                    this.view.setDefaultMenu(i);
                    playerOneNextMenu = !playerOneNextMenu;
                }
            }

            if(ch == 'l' || ch == 'L') {
                if(playerTwoNextMenu) {
                    this.view.setDefaultMenu(i);
                    playerTwoNextMenu = !playerTwoNextMenu;
                }
            }

            this.view.packChanges();
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
            if(idx >= 0) {
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

                this.view.setDefaultMenu(i);

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
    }
}
