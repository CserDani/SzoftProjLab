import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements ActionListener {
    private final List<Room> map = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Professor> professors = new ArrayList<>();
    private final List<Cleaner> cleaners = new ArrayList<>();
    private final Timer roomActionTimer = new Timer(1000, this);
    private int roomActionCounter = 60;
    private boolean gameWon = false;
    private GameView viewObserver = null;
    private int gameTime = 600;
    private final Timer gameTimer = new Timer(1000,this);
    private Random rand;

    public int getGameTime() { return gameTime; }
    public List<Student> getStudents() { return students; }
    public List<Professor> getProfessors() { return professors; }
    public List<Cleaner> getCleaners() { return cleaners; }
    public List<Room> getMap() { return map; }
    public Room getRandomRoom() {
        return map.get(rand.nextInt(map.size()));
    }
    public Door getRandomDoor(Room r) {
        List<Door> neighbours = r.getNeighbourDoors();
        return neighbours.get(rand.nextInt(neighbours.size()));
    }

    public Game() {
        /* To initialize Game, the Game object is always made after gameLoad */
    }

    public void setViewObserver(GameView viewObserver) {
        this.viewObserver = viewObserver;
    }

    public void removeViewObserver() {
        this.viewObserver = null;
    }

    public void setGameObservers() {
        for(Student student : students) {
            student.setGameObserver(this);
        }
    }

    public void setVanishObservers() {
        for(Room room : map) {
            room.setGameObserver(this);
        }
    }

    public void gameLoad(String filename){
        try{
            new LoadGameState(filename, this);
        } catch (IOException e){
            System.out.println("Sikertelen beolvasas: " + e.getMessage());
        }
    }

    public void testLoad(String filename){
        try{
            new LoadTest(filename, this);
        } catch (IOException e) {
            System.out.println("Sikertelen beolvasas: " + e.getMessage());
        }
    }

    public void setWon() {
        gameWon = true;
        if(viewObserver != null) {
            gameTimer.stop();
            viewObserver.notifyWin();
        }
    }
    public boolean getWon() { return gameWon; }

    public void move(Person p, Door d) {
        p.move(d);

        int deadStudents = 0;
        for(int i = 0; i < students.size(); i++) {
            Student currentStudent = students.get(i);
            if(currentStudent.getHealth() <= 0) {
                if(currentStudent.getPosition() != null) {
                    Room room = currentStudent.getPosition();
                    room.getPersons().remove(currentStudent);
                    currentStudent.setPosNull();
                }
                deadStudents++;
            }
        }

        if(deadStudents == students.size()) {
            viewObserver.notifyLose();
            stopTimers();
        }
        viewObserver.updateUI(students);
    }
    public void pickUp(Person p, Item i) {
        p.pickUp(i);
        viewObserver.updateUI(students);
    }
    public void drop(Person p, Item i) {
        p.dropItem(i);
        viewObserver.updateUI(students);
    }
    public void use(Person p, Item i) {
        p.useItem(i);
        viewObserver.updateUI(students);
    }
    public void divideRoom(Room r1) {
        Room r2 = r1.roomDivision();
        if(r2 != null) {
            map.add(r2);
            viewObserver.updateUI(students);
        }
    }
    public void mergeRooms(Room r1, Room r2) {
        boolean mergeResult = r1.mergeRooms(r2);
        if(mergeResult) {
            map.remove(r2);
            viewObserver.updateUI(students);
        }
    }

    public void notifyGameData() {
        if(viewObserver != null) {
            viewObserver.updateUI(students);
        }
    }

    public void startTimers() {
        gameTimer.start();
        roomActionTimer.start();
    }

    public void stopTimers() {
        gameTimer.stop();
        roomActionTimer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == roomActionTimer) {
            roomActionCounter--;
            if(roomActionCounter == 0) {
                roomActionCounter = 60;
                Room r1 = getRandomRoom();
                Room r2 = getRandomDoor(r1).getNextRoom(r1);
                mergeRooms(r1, r2);
                divideRoom(getRandomRoom());
            }
        }

        if(e.getSource() == gameTimer) {
            gameTime--;

            if(gameTime == 0) {
                stopTimers();
            }

            if(viewObserver != null) {
                viewObserver.notifyGameTime(gameTime);
            }
        }
    }
}
