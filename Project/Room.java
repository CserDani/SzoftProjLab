import java.util.ArrayList;
import java.util.List;

public class Room {
    private static int idAll = 0;
    private int id;
    private boolean isGassed;
    private int capacity;
    private int profcount = 0;
    private List<Person> persons = new ArrayList<>();
    private List<Item> Items = new ArrayList<>();

    public boolean isNotFull() { return persons.size() < capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setGas() {
        isGassed = !isGassed;
    }
    public Room(boolean isGassed, int capacity) {
        this.isGassed = isGassed;
        this.capacity = capacity;
        this.id = idAll++;
    }
    public int getId() { return id; }
    public void movedBy(Student s) {
        s.setPosition(this);
        persons.add(s);
        for(int i = 0; i < profcount; i++) {
            s.getDamaged();
        }
    }
    public void movedBy(Professor p) {
        p.setPosition(this);
        persons.add(p);
        profcount++;
        damageAll();
    }
    public void damageAll() {
        for(Person p : persons) {
            p.getDamaged();
        }
    }
    public void addItem(Item t) {
        Items.add(t);
    }
    public void removeItem(Item t) {
        Items.remove(t);
    }
}
