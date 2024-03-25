import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public abstract class Person implements ActionListener {
    private String name;
    private List<Item> inventory = new ArrayList<>();
    private Room position;
    private boolean notConscious = false;
    private Timer knockOutTimer = new Timer(1000, this);
    private int cooldown;

    public String getName() { return name; }
    public int getInventorySize() { return inventory.size(); }
    public List<Item> getInventory() { return inventory; }
    public Room getPosition() { return position; }
    public void setPosition(Room r) { position = r; }
    public boolean getNotConscious() { return notConscious; }
    public void setNotConscious() {
        notConscious = true;
        cooldown = 5;
        for(Item i : inventory) {
            inventory.remove(i);
            position.addItem(i);
        }
        knockOutTimer.restart();
    }
    public int getCooldown() { return cooldown; }
    public Timer getKnockOutTimer() { return knockOutTimer; }
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

    public void decrCooldown() { cooldown--; }
    public void stopTimer() { knockOutTimer.stop(); }
    public void setNotConsciousFalse() { notConscious = false; }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getKnockOutTimer()) {
            decrCooldown();
            if (getCooldown() == 0) {
                stopTimer();
                setNotConsciousFalse();
            }
        }
    }
}
