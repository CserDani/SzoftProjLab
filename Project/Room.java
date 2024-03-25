import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private String name;
    private boolean isGassed;
    private boolean isCursed;
    private int capacity;
    private int profcount = 0;
    private List<Door> neighbourDoors = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    Random rand = new Random();

    public String getName() { return name; }
    public List<Door> getNeighbourDoors() { return neighbourDoors; }
    public List<Item> getItems() { return items; }
    public List<Person> getPersons() { return persons; }
    public boolean isNotFull() { return persons.size() < capacity; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setGas() {
        isGassed = !isGassed;
    }
    public boolean isNeighbour(Room r) {
        for(int i = 0; i < this.getNeighbourDoors().size(); i++) {
            if(this.getNeighbourDoors().get(i).getNextRoom(this) == r)
                return true;
        }
        return false;
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
    public void moveRoom(Student s) {
        if(isNotFull()) {
            s.getPosition().persons.remove(s);
            s.setPosition(this);
            persons.add(s);

            if(isGassed) {
                s.setNotConscious();
            }

            for (int i = 0; i < profcount; i++) {
                s.getDamaged();
            }
        }
    }

    public void moveRoom(Professor p) {
        if(isNotFull()) {
            p.getPosition().persons.remove(p);
            p.setPosition(this);
            persons.add(p);

            if(isGassed) {
                p.setNotConscious();
            }

            if(!p.getNotConscious()) {
                damageAll();
                incProfCount();
            }
        }
    }

    public void mergeRooms(Room r) {
        if(this.getPersons().isEmpty() && r.getPersons().isEmpty() && this.isNeighbour(r)) {
            if(r.isGassed)
                this.isGassed = true;
            if(r.isCursed)
                this.isCursed = true;
            if(r.capacity > this.capacity)
                this.capacity = r.capacity;
            for(int i = 0; i < r.getNeighbourDoors().size(); i++) {
                this.addNeighbour(r.getNeighbourDoors().get(i).getNextRoom(r), r.getNeighbourDoors().get(i).isOneWay());
            }
            for(int i = 0; i<r.getItems().size(); i++) {
                this.addItem((r.getItems().get(i)));
            }
            r = null;
        }
    }

    public void roomDivision() {
        if(this.getPersons().isEmpty()) {
            Room r = new Room("New Room", this.isGassed && rand.nextBoolean(), this.isCursed && rand.nextBoolean(), rand.nextInt(this.capacity));
            this.capacity -= r.capacity;
            this.addNeighbour(r, false);
            int doorSeparation = rand.nextInt(this.getNeighbourDoors().size());
            List<Door> doorsToRemove = new ArrayList<>();
            for(int i = 0; i < doorSeparation; i++) {
                Door d = this.getNeighbourDoors().get(i);
                r.addNeighbour(d.getNextRoom(this), d.isOneWay());
                doorsToRemove.add(d);
            }
            for (Door door : doorsToRemove) {
                this.getNeighbourDoors().remove(door);
            }

            int itemSeparation = rand.nextInt(this.getItems().size());
            List<Item> itemsToRemove = new ArrayList<>();
            for(int i = 0; i < itemSeparation; i++) {
                Item t = this.getItems().get(i);
                r.addItem(t);
                itemsToRemove.add(t);
            }
            for (Item item : itemsToRemove) {
                this.getItems().remove(item);
            }
        }
    }

    public void teleportRoom(Student s) {
        this.moveRoom(s);
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
