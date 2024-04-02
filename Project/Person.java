import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A Személy objektum egy szereplő alapvető tulajdonságait tartalmazza, és az azokkal kapcsolatos műveleteket implementálja.
 * Az alapvető tulajdonságok közé tartozik például a személy neve, raktára stb. Tehát azokat az információkat reprezentálja az objektum, amelyekkel minden szereplő kötelezően rendelkezik.
 */
public abstract class Person implements ActionListener {
    private String name;
    private List<Item> inventory = new ArrayList<>();
    private List<Passive> damageHelpItems = new ArrayList<>();
    private List<Passive> gasHelpItems = new ArrayList<>();
    private Room position;
    private boolean notConscious = false;
    private Timer knockOutTimer = new Timer(1000, this);
    private int cooldown;
    private Timer gasTimer = new Timer(1000, this);

    /**
     * Name getter
     * @return A személy neve
     */
    public String getName() { return name; }

    /**
     * Inventory méret getter
     * @return Az inventory mérete
     */
    public int getInventorySize() { return inventory.size(); }

    /**
     * Inventory getter
     * @return Az inventory-ban lévő tárgyak listája
     */
    public List<Item> getInventory() { return inventory; }

    public List<Passive> getDamageHelpItems() { return damageHelpItems; }
    public List<Passive> getGasHelpItems() { return gasHelpItems; }

    /**
     * Position getter
     * @return A személy pozíciója (szoba)
     */
    public Room getPosition() { return position; }

    /**
     * Position setter
     * @param r A személy új pozíciója (szoba)
     */
    public void setPosition(Room r) {
        position = r;
    }

    public void setStartPosition(Room r) {
        position = r;
        r.getPersons().add(this);
    }

    /**
     * NotConscious getter
     * @return A személy eszméleti állapota
     */
    public boolean getNotConscious() { return notConscious; }

    public void setNotConsciousAndTimer() {
        notConscious = true;
        cooldown = 5;
        for (Item i : inventory) {
            position.addItem(i);
        }
        knockOutTimer.restart();
    }

    /**
     * NotConscious setter
     * A személy eszméleti állapotát állítja be
     */
    public void setNotConscious() {
        if(gasHelpItems.isEmpty()) {
            setNotConsciousAndTimer();
            inventory.clear();
        } else {
            gasTimer.restart();
        }
    }

    public abstract void boardCleanerConscious();

    /**
     * Cooldown getter
     * @return A cooldown változó értéke
     */
    public int getCooldown() { return cooldown; }

    /**
     * KnockOutTimer getter
     * @return A knockOutTimer változó értéke
     */
    public Timer getKnockOutTimer() { return knockOutTimer; }
    public Timer getGasTimer() { return gasTimer; }

    /**
     * Person konstruktor
     * A személy nevét állítja be
     * @param name A személy neve
     */
    protected Person(String name) {
        this.name = name;
        System.out.println("Person constructor!");
    }

    /**
     * addItemToInventory függvény
     * Hozzáad egy tárgyat a személy inventory-jába
     * @param t A tárgy
     */
    public void addItemToInventory(Item t) {
        inventory.add(t);
    }

    /**
     * pickUp absztrakt függvény
     * Absztrakt függvény egy tárgy felvételéhez
     * @param t A tárgy
     */
    public abstract void pickUp(Item t);

    /**
     * dropItem absztrakt függvény
     * Absztrakt függvény egy tárgy eldobásához
     * @param t A tárgy
     */
    public abstract void dropItem(Item t);

    /**
     * useItem absztrakt függvény
     * Absztrakt függvény egy tárgy használatához
     * @param t A tárgy
     */
    public abstract void useItem(Item t);

    /**
     * move absztrakt függvény
     * Absztrakt függvény egy személy mozgatásához
     * @param d Az ajtó, amin a személy átmegy
     */
    public abstract void move(Door d);

    /**
     * teleport absztrakt függvény
     * Absztrakt függvény egy személy teleportálásához
     * @param r A szoba, ahova a személy teleportál
     */
    public abstract void teleport(Room r);

    /**
     * getDamaged absztrakt függvény
     * Absztrakt függvény egy személy sebződéséhez
     */
    public abstract void getDamaged();

    /**
     * decrCooldown függvény
     * A cooldown változó értékét csökkenti
     */
    public void decrCooldown() { cooldown--; }

    /**
     * stopTimer függvény
     * A knockOutTimer-t megállítja
     */
    public void stopTimer() { knockOutTimer.stop(); }

    /**
     * NotConsciousFalse setter
     * A notConscious változó értékét hamisra állítja.
     */
    public void setNotConsciousFalse() { notConscious = false; }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
