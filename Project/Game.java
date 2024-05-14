import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game implements ActionListener {
    private final List<Room> map = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Professor> professors = new ArrayList<>();
    private final List<Cleaner> cleaners = new ArrayList<>();
    private final Timer mergeTimer = new Timer(1000, this);
    private int mergeCounter = 60;
    private boolean gameWon = false;
    private GameView viewObserver = null;

    public List<Student> getStudents() { return students; }
    public List<Professor> getProfessors() { return professors; }
    public List<Cleaner> getCleaners() { return cleaners; }
    public List<Room> getMap() { return map; }

    public Game() {
        /* To initialize Game, the Game object is always made after gameLoad */
    }

    public void setViewObserver(GameView viewObserver) {
        this.viewObserver = viewObserver;
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
            viewObserver.notifyWin();
        }
    }
    public boolean getWon() { return gameWon; }

    public void move(Person p, Door d) {
        p.move(d);
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
        mergeTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mergeTimer) {
            mergeCounter--;
            if(mergeCounter == 0) {
                mergeCounter = 60;
            }
        }
    }
}
