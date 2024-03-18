import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private boolean isGassed;
    private boolean isCursed;
    private int capacity;
    private int profcount = 0;
    private List<Door> neighbourDoors = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public String getName() { return name; }
    public List<Door> getNeighbourDoors() { return neighbourDoors; }
    public boolean isNotFull() { return persons.size() < capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setGas() {
        isGassed = !isGassed;
    }
    public Room(String name, boolean isGassed, boolean isCursed, int capacity) {
        this.name = name;
        this.isGassed = isGassed;
        this.isCursed = isCursed;
        this.capacity = capacity;
    }
    public void addNeighbour(Room r) {
        neighbourDoors.add(new Door(r));
    }
    public void moveRoom(Student s) {
        if(isNotFull()) {
            s.getPosition().persons.remove(s);
            s.setPosition(this);
            persons.add(s);
            for (int i = 0; i < profcount; i++) {
                s.getDamaged();
            }
        }
    }
    public void moveRoom(Professor p) {
        if(isNotFull()) {
            p.setPosition(this);
            persons.add(p);
            profcount++;
            damageAll();
        }
    }

    public void teleportRoom(Student s) {
        s.getPosition().persons.remove(s);
        s.setPosition(this);
        persons.add(s);
        for (int i = 0; i < profcount; i++) {
            s.getDamaged();
        }
    }
    public void damageAll() {
        for(Person p : persons) {
            p.getDamaged();
        }
    }
    public void addItem(Item t) {
        items.add(t);
    }
    public void removeItem(Item t) {
        items.remove(t);
    }
    public void incProfCount() { profcount++; }
}
