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
    public List<Item> getItems() { return items; }
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
        System.out.println("Room constructor!");
    }
    public void addNeighbour(Room r, boolean oneWay) {
        Door newDoor = new Door(this, r, oneWay);
        this.neighbourDoors.add(newDoor);
        r.neighbourDoors.add(newDoor);
    }
    public void moveRoom(Person p) {
        if(isNotFull()) {
            p.getPosition().persons.remove(p);
            p.setPosition(this);
            persons.add(p);
            for (int i = 0; i < profcount; i++) {
                p.getDamaged();
            }
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
    public void teleportRoom(Professor p) {}
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
