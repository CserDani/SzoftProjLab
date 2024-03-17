import java.util.ArrayList;
import java.util.List;

public abstract class Person {
    private String name;
    private List<Item> inventory;

    public String getName() { return name; }
    public int getInventorySize() { return inventory.size(); }
    protected Person(String name) {
        this.name = name;
        inventory = new ArrayList<>();
    }

    public void addToInventory(Item t) {
        inventory.add(t);
    }

    public void removeFromInventory(Item t) {
        inventory.remove(t);
    }

    public abstract void pickUp(Item t);
}
