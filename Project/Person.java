import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    private String name;
    private List<Item> inventory = new ArrayList<>();
    private Room position;
    private boolean notConscious = false;

    public String getName() { return name; }
    public int getInventorySize() { return inventory.size(); }
    public List<Item> getInventory() { return inventory; }
    public Room getPosition() { return position; }
    public void setPosition(Room r) { position = r; }
    public boolean getNotConscious() { return notConscious; }
    public void setNotConsciousTrue() {
        notConscious = true;
        for(Item i : inventory) {
            inventory.remove(i);
            position.addItem(i);
        }
    }
    public void setNotConsciousFalse() {
        notConscious = false;
    }
    protected Person(String name) {
        this.name = name;
        System.out.println("Person constructor!");
    }

    public void addItemToInventory(Item t) {
        inventory.add(t);
    }


    public abstract void pickUp(Item t);
    public abstract void dropItem(Item t);
    public abstract void useItem(Item t);
    public abstract void move(Door d);
    public abstract void teleport(Room r);
    public abstract void getDamaged();
}
