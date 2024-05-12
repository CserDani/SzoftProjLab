import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Room> map = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Professor> professors = new ArrayList<>();
    private final List<Cleaner> cleaners = new ArrayList<>();
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
    public void addRoom(Room r) {
        map.add(r);
    }
    public void addPlayer(Student s) {
        students.add(s);
    }
    public void addProfessor(Professor p) {
        professors.add(p);
    }
    public void addCleaner(Cleaner c) {
        cleaners.add(c);
    }

    public void move(Person p, Door d) {
        p.move(d);
    }
    public void teleport(Person p, Room r) {
        p.teleport(r);
    }
    public void pickUp(Person p, Item i) {
        p.pickUp(i);
    }
    public void drop(Person p, Item i) {
        p.dropItem(i);
    }
    public void use(Person p, Item i) {
        p.useItem(i);
    }
    public void divideRoom(Room r1) {
        Room r2 = r1.roomDivision();
        if(r2 != null)
            map.add(r2);
    }
    public void mergeRooms(Room r1, Room r2) {
        r1.mergeRooms(r2);
        map.remove(r2);
    }
}
