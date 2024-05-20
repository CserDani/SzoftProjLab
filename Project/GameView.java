import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JFrame {
    private final List<String> menuItems = new ArrayList<>();
    private final List<JList<String>> menus = new ArrayList<>();
    private final JPanel center = new JPanel(new GridBagLayout());
    private final List<String> playersDataItems = new ArrayList<>();
    private final List<JList<String>> playersData = new ArrayList<>();
    private final List<String> actionsForPlayers = new ArrayList<>();
    private int widthForPanel = 0;
    private final Color roomBackground = new Color(126, 67, 38);
    private JTextField gameTimeField;
    public List<JList<String>> getMenus() {
        return menus;
    }
    public GameView(Game game) {
        initWindow(game);
    }

    public void initWindow(Game game) {
        this.setTitle("Rektori Rejtvenyek");
        this.setLayout(new BorderLayout(0, 20));
        this.getContentPane().setBackground(Color.GRAY);
        this.setResizable(false);

        JPanel north = new JPanel(new BorderLayout());
        north.setBorder(new LineBorder(Color.BLACK, 2));

        JPanel northCp = new JPanel(new GridBagLayout());
        northCp.setBorder(new LineBorder(Color.BLACK, 2));
        northCp.setBackground(Color.GRAY);

        JPanel northEp = new JPanel(new FlowLayout());
        northEp.setBorder(new LineBorder(Color.BLACK, 2));
        northEp.setBackground(Color.GRAY);

        center.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.setBackground(Color.GRAY);

        GridBagConstraints gbcNorth = new GridBagConstraints();
        gbcNorth.insets = new Insets(0,5,0,5);

        UIManager.put("Label.disabledForeground", Color.BLACK);
        UIManager.put("TextField.inactiveForeground", Color.BLACK);
        menuItems.add("MOVE");
        menuItems.add("USE ITEM");
        menuItems.add("PICK UP ITEM");
        menuItems.add("DROP ITEM");

        playersDataItems.add("Health: ");
        playersDataItems.add("Position: ");
        playersDataItems.add("Inventory size: ");
        playersDataItems.add("Consciousness: ");
        playersDataItems.add("Immunity time: ");

        List<Student> students = game.getStudents();
        int studentsSize = students.size();

        for(int i = 0; i < studentsSize; i++) {
            gbcNorth.gridx = 0;
            gbcNorth.gridy = 0;
            actionsForPlayers.add(null);
            JPanel jpNorth = new JPanel(new GridBagLayout());
            jpNorth.setBackground(Color.GRAY);

            DefaultListModel<String> modelForMenu = new DefaultListModel<>();
            for(String item : menuItems) {
                modelForMenu.addElement(item);
            }
            JList<String> jlist = new JList<>(modelForMenu);
            jlist.setLayoutOrientation(JList.VERTICAL);
            jlist.setSelectedIndex(0);
            jlist.setEnabled(false);
            jlist.setBorder(new LineBorder(Color.BLACK));

            menus.add(jlist);
            jpNorth.add(jlist, gbcNorth);

            gbcNorth.gridx++;

            Student currentStudent = students.get(i);
            JList<String> jListForData = new JList<>();
            jListForData.setBackground(Color.GRAY);

            jListForData.setModel(buildDataModelForStudent(currentStudent));
            jListForData.setLayoutOrientation(JList.VERTICAL);
            jListForData.setEnabled(false);
            jListForData.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
                JLabel text = new JLabel(value);

                text.setForeground(Color.ORANGE);

                return text;
            });

            playersData.add(jListForData);
            jpNorth.add(jListForData, gbcNorth);

            jpNorth.add(Box.createHorizontalStrut(200));

            if(widthForPanel == 0) {
                widthForPanel = jpNorth.getPreferredSize().width;
            }

            northCp.add(jpNorth);
            gbcNorth.gridx++;
        }

        for(Student student : students) {
            this.addRoomToPanel(student);
        }

        gameTimeField = new JTextField();
        setGameTimeText(game.getGameTime());
        gameTimeField.setBorder(new LineBorder(Color.GRAY));
        gameTimeField.setEnabled(false);
        gameTimeField.setPreferredSize(new Dimension(70, 20));
        gameTimeField.setHorizontalAlignment(SwingConstants.CENTER);
        gameTimeField.setBackground(Color.GRAY);
        gameTimeField.setDisabledTextColor(Color.ORANGE);

        Font gameTimeAreaFont = gameTimeField.getFont();
        Font newFont = new Font(gameTimeAreaFont.getName(), Font.BOLD, 20);
        gameTimeField.setFont(newFont);

        northEp.add(gameTimeField);
        northEp.setAlignmentX(Component.RIGHT_ALIGNMENT);
        northEp.setAlignmentY(Component.TOP_ALIGNMENT);

        north.add(northCp, BorderLayout.CENTER);
        north.add(northEp, BorderLayout.EAST);

        this.add(north, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public JPanel genRoomPanel(Student currentStudent, Room currentRoom){
        JPanel roomPanel = new JPanel();
        List<Person> persons = currentRoom.getPersons();
        List<Item> items = currentRoom.getItems();

        int width = 300;
        int height = 150;

        roomPanel.setPreferredSize(new Dimension(width, height));
        roomPanel.setBorder(new LineBorder(Color.BLACK, 3));
        roomPanel.setLayout(new GridLayout(2,1));

        JPanel personPanel = setPersonPanel(currentStudent, persons, width);
        JPanel itemPanel = setItemPanel(items, width);

        personPanel.setBackground(roomBackground);
        itemPanel.setBackground(roomBackground);

        roomPanel.add(personPanel);
        roomPanel.add(itemPanel);

        return roomPanel;
    }

    public JPanel setPersonPanel(Student currentStudent, List<Person> persons, int width) {
        JPanel personPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        int rowSize = 0;

        for(Person person : persons) {
            JPanel personViewPanel = new JPanel();
            personViewPanel.setBackground(roomBackground);
            if(person == currentStudent) {
                JLabel currentView = new JLabel(new ImageIcon("Resources/currentStudent.png"));
                personViewPanel.add(currentView);
            } else {
                personViewPanel.add(person.getView());
            }

            rowSize += personViewPanel.getPreferredSize().width;
            if(rowSize > width) {
                rowSize = 0;
                gbc.gridy++;
            }

            personPanel.add(personViewPanel, gbc);
        }
        return personPanel;
    }

    public JPanel setItemPanel(List<Item> items, int width){
        JPanel itemsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        int rowSize = 0;

        for (Item item : items) {
            JPanel itemViewPanel = new JPanel();
            itemViewPanel.setBackground(roomBackground);
            JLabel itemIcon = new JLabel(new ImageIcon("Resources/item.png"));
            itemIcon.setPreferredSize(new Dimension(15,15));
            itemIcon.setBorder(new LineBorder(Color.BLACK));
            itemViewPanel.add(itemIcon);

            rowSize += (itemViewPanel.getPreferredSize().width + 1);
            if(rowSize >= width) {
                rowSize = 0;
                gbc.gridy++;
            }

            itemsPanel.add(itemViewPanel, gbc);
        }

        return itemsPanel;
    }

    private DefaultListModel<String> buildDataModelForStudent(Student student) {
        DefaultListModel<String> modelForPlayData = new DefaultListModel<>();
        int health = student.getHealth();
        for (String item : playersDataItems) {
            switch (item) {
                case "Health: ":
                    String healthString = item;
                    healthString += health > 0 ? health : "0";
                    modelForPlayData.addElement(healthString);
                    break;
                case "Position: ":
                    String roomName = student.getPosition() == null ? "-" : student.getPosition().getName();
                    String posString = item + roomName;
                    modelForPlayData.addElement(posString);
                    break;
                case "Inventory size: ":
                    int inventorySize = student.getInventorySize();
                    String isFull = inventorySize == 5 ? " - Full" : "";
                    String inventoryString = health > 0 ? student.getInventory().size() + isFull : "-";
                    String invString = item + inventoryString;
                    modelForPlayData.addElement(invString);
                    break;
                case "Consciousness: ":
                    boolean notConscious = student.getNotConscious();
                    String conscious = notConscious ? "Not conscious" : "Conscious";
                    conscious = health > 0 ? conscious : "-";
                    String conString = item + conscious;
                    modelForPlayData.addElement(conString);
                    break;
                case "Immunity time: ":
                    int immunityTime = student.getImmunityCounter();
                    String immunityString = health > 0 ? String.valueOf(immunityTime) : "-";
                    String immunity = item + immunityString;
                    modelForPlayData.addElement(immunity);
                    break;
                default:
                    break;
            }
        }

        return modelForPlayData;
    }

    public void addClassAsKeyListener(KeyListener kl) {
        this.addKeyListener(kl);
    }

    public void packChanges() {
        this.pack();
    }

    private void setGameTimeText(int gameTime) {
        int minute = gameTime / 60;
        int second = gameTime % 60;
        String minuteString;
        if(minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = Integer.toString(minute);
        }
        String secondString;
        if(second < 10) {
            secondString = "0" + second;
        } else {
            secondString = Integer.toString(second);
        }
        gameTimeField.setText(minuteString + ":" + secondString);

        if(minute == 0 && second == 0) {
            notifyLose();
        }
    }

    public void notifyGameTime(int gameTime) {
        setGameTimeText(gameTime);
    }

    public void notifyWin() {
        this.setEnabled(false);
        new WinGameMenu(this);
    }

    public void notifyLose() {
        this.setEnabled(false);
        new LoseGameWindow(this);
    }

    public void setDefaultMenu(Student student, int i) {
        JList<String> menu = menus.get(i);
        actionsForPlayers.set(i, null);

        DefaultListModel<String> model = new DefaultListModel<>();

        for (String item : menuItems) {
            model.addElement(item);
        }

        menu.setModel(model);
        if(student.getHealth() != 0) {
            menu.setSelectedIndex(0);
        }
        packChanges();
    }

    public void setMenu(String action, Student currentStudent, int i) {
        JList<String> menu = menus.get(i);
        int idx = 0;
        if(action.equals(actionsForPlayers.get(i))) {
            idx = menu.getSelectedIndex();
        } else {
            actionsForPlayers.set(i, action);

        }

        Room currentRoom = currentStudent.getPosition();
        List<Item> itemsInPlayerInventory = currentStudent.getInventory();
        DefaultListModel<String> model = new DefaultListModel<>();
        switch (action) {
            case "MOVE":
                List<Door> doorsInCurrentRoom = currentRoom.getNeighbourDoors();
                for (Door door : doorsInCurrentRoom) {
                    Room nextRoom = door.getNextRoom(currentRoom);
                    String element = nextRoom.getName();
                    String canMove = door.canMove(currentRoom) ? " - Can move" : " - Can't move";
                    String doorVanish = door.getVanish() ? ", Vanished" : "";
                    model.addElement(element + canMove + doorVanish);
                }
                break;
            case "USE ITEM", "DROP ITEM":
                for (Item item : itemsInPlayerInventory) {
                    model.addElement(item.getName());
                }
                break;
            case "PICK UP ITEM":
                List<Item> itemsInCurrentRoom = currentRoom.getItems();
                for (Item item : itemsInCurrentRoom) {
                    model.addElement(item.getName());
                }
                break;
            default:
                break;
        }

        if(!model.isEmpty()) {
            menu.setModel(model);
            menu.setSelectedIndex(idx);
        } else {
            model.addElement("No option!");
            menu.setModel(model);
        }
    }

    private void updateMenu(Student student, int i) {
        String action = actionsForPlayers.get(i);
        if (action != null) {
            this.setMenu(action, student, i);
        }
    }

    public void updateData(Student student, int i) {
        JList<String> dataList = playersData.get(i);
        dataList.setModel(buildDataModelForStudent(student));
    }

    public void addRoomToPanel(Student student) {
        JPanel newPanel = genRoomPanel(student, student.getPosition());

        center.add(newPanel);

        int newStrut = widthForPanel - newPanel.getPreferredSize().width - 35;

        center.add(Box.createHorizontalStrut(newStrut));
    }

    public void genNothingRoom() {
        JPanel roomPanel = new JPanel();

        int width = 300;
        int height = 150;

        roomPanel.setPreferredSize(new Dimension(width, height));
        roomPanel.setBorder(new LineBorder(Color.BLACK, 3));
        roomPanel.setBackground(roomBackground);

        center.add(roomPanel);

        int newStrut = widthForPanel - roomPanel.getPreferredSize().width - 35;

        center.add(Box.createHorizontalStrut(newStrut));
    }

    public void setMenuClear(int i) {
        JList<String> menu = menus.get(i);
        menu.clearSelection();
    }

    public void updateUI(List<Student> students) {
        if(students.size() != menus.size() && students.size() != actionsForPlayers.size()) {
            System.out.println("Nagy baj van!");
            return;
        }

        center.removeAll();
        for(int i = 0; i < students.size(); i++) {
            Student currentStudent = students.get(i);
            updateData(currentStudent, i);
            if(currentStudent.getHealth() > 0) {
                updateMenu(currentStudent, i);
                addRoomToPanel(currentStudent);
            } else {
                setMenuClear(i);
                genNothingRoom();
            }
        }

        packChanges();
    }
}
