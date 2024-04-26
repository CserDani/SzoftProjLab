import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * A Szoba objektum felelős a szobák tulajdonságaiért és műveleteiért. Tehát ez az objektum implementálja például azokat a funkciókat, amelyek a szobák osztódását és egyesülését viszik végbe. A tulajdonságok közé például a kapacitás tartozik.
 */
public class Room implements ActionListener {
    private String name;
    private boolean isGassed;
    private boolean isCursed;
    private int capacity;
    private int profcount = 0;
    private List<Door> neighbourDoors = new ArrayList<>();
    private List<Person> persons = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private Timer doorVanish = new Timer(20000, this);
    private boolean cleaned = false;
    private int afterCleanCount = 5;
    Random rand = new Random();

    /**
     * Name getter
     * @result A szoba neve
     */
    public String getName() { return name; }

    /**
     * NeighbourDoors getter
     * @result A szomszédos szobákba nyíló ajtók listája
     */
    public List<Door> getNeighbourDoors() { return neighbourDoors; }

    /**
     * Items getter
     * @result A szobában lévő tárgyak listája
     */
    public List<Item> getItems() { return items; }

    /**
     * Items getter
     * @result A szobában lévő személyek listája
     */
    public List<Person> getPersons() { return persons; }

    /**
     * isNoutFull függvény
     * @result Visszaadja, hogy a szoba tele van-e
     */
    public boolean isNotFull() { return persons.size() < capacity; }

    /**
     * Capacity getter
     * @result A szoba kapacitása
     */
    public int getCapacity() { return capacity; }

    /**
     * Capacity setter
     * @param capacity A szoba kapacitása
     */
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getProfcount() { return profcount; }

    /**
     * isGassed getter
     * @result Visszaadja, hogy a szoba gázos-e
     */
    public boolean getIsGassed() { return isGassed; }

    /**
     * isGassed setter
     * Beállítja a szobában lévő gázt
     */
    public void setGas() {
        isGassed = !isGassed;
    }
    public void setCleaned() { cleaned = true; }
    public int getAfterCleanCount() { return afterCleanCount; }

    /**
     * isNeighbour függvény
     * Visszaadja, hogy a paraméterként kapott szoba szomszédos-e
     * @param r A vizsgált szoba
     */
    public boolean isNeighbour(Room r) {
        for(int i = 0; i < this.getNeighbourDoors().size(); i++) {
            if(this.getNeighbourDoors().get(i).getNextRoom(this) == r)
                return true;
        }
        return false;
    }

    /**
     * Room konstruktor
     * Beállítja a szoba nevét, hogy gázos-e, hogy el van-e átkozva, illetve a kapacitását
     * @param name A szoba neve
     * @param isGassed A szoba gázossága
     * @param isCursed A szoba átkozottsága
     * @param capacity A szoba kapacitása
     */
    public Room(String name, boolean isGassed, boolean isCursed, int capacity) {
        this.name = name;
        this.isGassed = isGassed;
        this.isCursed = isCursed;
        this.capacity = capacity;
        if(this.isCursed) {
            doorVanish.start();
        }
    }

    /**
     * addNeighbour függvény
     * Hozzáad egy szomszédot
     * @param r Az új szomszéd (szoba)
     * @param oneWay Az ajtó iránya
     */
    public void addNeighbour(Room r, boolean oneWay) {
        Door newDoor = new Door(this, r, oneWay);
        this.neighbourDoors.add(newDoor);
        r.neighbourDoors.add(newDoor);
    }

    /**
     * moveRoom függvény
     * A szobák közötti mozgást valósítja meg
     * @param s A szobák között mozgó hallgató
     */
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

            if(cleaned && afterCleanCount > 0) {
                afterCleanCount--;
            }
        }
    }

    /**
     * moveRoom függvény
     * A szobák közötti mozgást valósítja meg
     * @param p A szobák között mozgó oktató
     */
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

            if(cleaned && afterCleanCount > 0) {
                afterCleanCount--;
            }
        }
    }


    public void moveRoom(Cleaner c) {
        if(isNotFull()) {
            c.getPosition().persons.remove(c);
            c.setPosition(this);

            if(this.isGassed) {
                List<Person> personList = new ArrayList<>(persons);
                for (Person p : personList) {
                    if (!p.getNotConscious()) {
                        Door d = null;

                        for (Door door : neighbourDoors) {
                            if (door.canMove(this)) {
                                if (door.getNextRoom(this).isNotFull()) {
                                    d = door;
                                    break;
                                }
                            }
                        }

                        if (d != null) {
                            p.move(d);
                        }
                    }
                }

                this.setGas();
            }

            persons.add(c);

            if(cleaned && afterCleanCount > 0) {
                afterCleanCount--;
            }

            if(!cleaned) {
                this.setCleaned();
            }
        }
    }

    /**
     * mergeRooms függvény
     * A szobák egyesülését valósítja meg
     * @param r A szoba, amellyel egyesíteni szeretnénk
     */
    public void mergeRooms(Room r) {
        if(this.getPersons().isEmpty() && r.getPersons().isEmpty() && this.isNeighbour(r)) {
            if(r.isGassed)
                this.isGassed = true;
            if(r.isCursed)
                this.isCursed = true;
            if(r.capacity > this.capacity)
                this.capacity = r.capacity;
            for(int i = 0; i < r.getNeighbourDoors().size(); i++) {
                if(r.getNeighbourDoors().get(i).getNextRoom(r) != this) {
                    this.neighbourDoors.add(r.getNeighbourDoors().get(i));
                    r.getNeighbourDoors().get(i).setRoom(r, this);
                }
            }
            List<Door> neighDoors = new ArrayList<>(this.neighbourDoors);
            for(Door d : neighDoors) {
                if(d.getNextRoom(this) == r) {
                    this.neighbourDoors.remove(d);
                }
            }
            for(int i = 0; i<r.getItems().size(); i++) {
                this.addItem((r.getItems().get(i)));
            }
        }
    }

    /**
     * roomDivision függvény
     * A szobák osztódását valósítja meg
     */
    public Room roomDivision() {
        if(this.getPersons().isEmpty()) {
            int newcap = rand.nextInt(this.capacity);
            if(newcap == this.capacity) {
                newcap -= 1;
            } else if(newcap == 0) {
                newcap = 1;
            }
            Room r = new Room("New Room", this.isGassed && rand.nextBoolean(), this.isCursed && rand.nextBoolean(), newcap);
            this.capacity -= r.capacity;
            int doorSeparation = rand.nextInt(this.getNeighbourDoors().size()+1);
            List<Door> doorsToRemove = new ArrayList<>();
            for(int i = 0; i < doorSeparation; i++) {
                Door d = this.getNeighbourDoors().get(i);
                r.addNeighbour(d.getNextRoom(this), d.isOneWay());
                doorsToRemove.add(d);
            }
            for (Door door : doorsToRemove) {
                this.getNeighbourDoors().remove(door);
            }

            int itemSeparation = rand.nextInt(this.getItems().size()+1);
            List<Item> itemsToRemove = new ArrayList<>();
            for(int i = 0; i < itemSeparation; i++) {
                Item t = this.getItems().get(i);
                r.addItem(t);
                itemsToRemove.add(t);
            }
            for (Item item : itemsToRemove) {
                this.getItems().remove(item);
            }

            this.addNeighbour(r, false);
            return r;
        }
        return null;
    }

    /**
     * teleportRoom függvény
     * A hallgató teleportálásáért felelős függvény
     * @param s A teleportáló hallgató
     */
    public void teleportRoom(Student s) {
        this.moveRoom(s);
    }

    /**
     * damageAll függvény
     * A szobában lévő személyek sebzéséért felelős függvény
     */
    public void damageAll() {
        for(Person p : persons) {
            p.getDamaged();
        }
    }

    /**
     * addItem függvény
     * A szobába rak egy tárgyat
     * @param t A tárgy
     */
    public void addItem(Item t) {
        items.add(t);
    }

    /**
     * removeItem függvény
     * A szobából kitöröl egy tárgyat
     * @param t A tárgy
     */
    public void removeItem(Item t) {
        items.remove(t);
    }

    /**
     * incProfCount függvény
     * Megnöveli a profcount (oktatók száma a szobában) változó értékét
     */
    public void incProfCount() { profcount++; }
    public void decrProfCount() { profcount--; }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == doorVanish) {
            for(Door d : neighbourDoors) {
                d.setVanish();
            }
        }
    }
}
