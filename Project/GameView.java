import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JFrame implements ActionListener {
    private final List<String> menuItems = new ArrayList<>();
    private final List<JList<String>> menus = new ArrayList<>();
    private final List<String> playersDataItems = new ArrayList<>();
    private final List<JList<String>> playersData = new ArrayList<>();
    private final List<String> actionsForPlayers = new ArrayList<>();
    private final Timer gameTimer = new Timer(1000,this);
    private int gameTime = 600;
    private JTextField gameTimeField;
    public List<JList<String>> getMenus() {
        return menus;
    }
    public GameView(Game game) {
        initWindow(game);
    }

    public void initWindow(Game game) {
        this.setTitle("Rektori Rejtvenyek");
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel north = new JPanel(new BorderLayout());
        JPanel northWp = new JPanel(new GridBagLayout());
        JPanel northEp = new JPanel(new FlowLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0,5,0,5);
        gbc.gridx = 0;
        gbc.gridy = 0;

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

        List<Student> students = game.getStudents();
        int studentsSize = students.size();
        for(int i = 0; i < studentsSize; i++) {
            actionsForPlayers.add(null);
            JPanel jp = new JPanel(new GridBagLayout());

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
            jp.add(jlist, gbc);

            gbc.gridx++;

            Student currentStudent = students.get(i);
            JList<String> jListForData = new JList<>();
            jListForData.setModel(buildDataModelForStudent(currentStudent));
            jListForData.setLayoutOrientation(JList.VERTICAL);
            jListForData.setEnabled(false);

            playersData.add(jListForData);
            jp.add(jListForData, gbc);

            if(i < studentsSize - 1) {
                jp.add(Box.createHorizontalStrut(200));
            }

            northWp.add(jp);
            gbc.gridx++;
        }

        northWp.add(Box.createHorizontalStrut(100));

        gameTimeField = new JTextField();
        setGameTimeText();
        gameTimeField.setBorder(new LineBorder(Color.BLACK));
        gameTimeField.setEnabled(false);
        gameTimeField.setPreferredSize(new Dimension(70, 20));
        gameTimeField.setHorizontalAlignment(SwingConstants.CENTER);

        Font gameTimeAreaFont = gameTimeField.getFont();
        Font newFont = new Font(gameTimeAreaFont.getName(), Font.BOLD, 20);
        gameTimeField.setFont(newFont);

        northEp.add(gameTimeField);
        northEp.add(Box.createHorizontalStrut(5));
        northEp.setAlignmentX(Component.RIGHT_ALIGNMENT);
        northEp.setAlignmentY(Component.TOP_ALIGNMENT);

        north.add(northWp, BorderLayout.WEST);
        north.add(northEp, BorderLayout.EAST);

        JPanel center = new JPanel();

        this.add(north, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
        this.pack();
        gameTimer.start();
    }

    private DefaultListModel<String> buildDataModelForStudent(Student student) {
        DefaultListModel<String> modelForPlayData = new DefaultListModel<>();
        for(int i = 0; i < playersDataItems.size(); i++) {
            String item = playersDataItems.get(i);
            switch(item) {
                case "Health: ":
                    String healthString = item + student.getHealth();
                    modelForPlayData.addElement(healthString);
                    break;
                case "Position: ":
                    String posString = item + student.getPosition().getName();
                    modelForPlayData.addElement(posString);
                    break;
                case "Inventory size: ":
                    String invString = item + student.getInventory().size();
                    modelForPlayData.addElement(invString);
                    break;
                case "Consciousness: ":
                    boolean notConscious = student.getNotConscious();
                    String conscious = notConscious ? "Not conscious" : "Conscious";
                    String conString = item + conscious;
                    modelForPlayData.addElement(conString);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gameTimer) {
            gameTime--;
            setGameTimeText();
        }
    }

    private void setGameTimeText() {
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
            gameTimer.stop();
            this.setEnabled(false);
        }
    }

    public void notifyWin() {
        gameTimer.stop();
        this.setEnabled(false);
    }

    public void setDefaultMenu(int i) {
        JList<String> menu = menus.get(i);
        actionsForPlayers.set(i, null);

        DefaultListModel<String> model = new DefaultListModel<>();

        for (String item : menuItems) {
            model.addElement(item);
        }

        menu.setModel(model);
        menu.setSelectedIndex(0);
    }

    public void setMenu(String action, Student currentStudent, int i) {
        JList<String> menu = menus.get(i);
        actionsForPlayers.set(i, action);

        Room currentRoom = currentStudent.getPosition();
        List<Item> itemsInPlayerInventory = currentStudent.getInventory();
        DefaultListModel<String> model = new DefaultListModel<>();
        switch (action) {
            case "MOVE":
                List<Door> doorsInCurrentRoom = currentRoom.getNeighbourDoors();
                for (Door door : doorsInCurrentRoom) {
                    Room nextRoom = door.getNextRoom(currentRoom);
                    model.addElement(nextRoom.getName());
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

        menu.setModel(model);
        menu.setSelectedIndex(0);
    }

    private void updateMenu(List<Student> students) {
        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String action = actionsForPlayers.get(i);
            if (action != null) {
                this.setMenu(action, student, i);
            }
        }
    }

    public void updateData(List<Student> students) {
        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            JList<String> dataList = playersData.get(i);
            dataList.setModel(buildDataModelForStudent(student));
        }
    }

    public void updateUI(List<Student> students) {
        if(students.size() != menus.size() && students.size() != actionsForPlayers.size()) {
            System.out.println("Nagy baj van!");
            return;
        }

        updateMenu(students);
        updateData(students);
    }
}
