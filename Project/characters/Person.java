package characters;

import items.Item;
import items.Passive;
import roomanddoor.*;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A Személy objektum egy szereplő alapvető tulajdonságait tartalmazza, és az azokkal kapcsolatos műveleteket implementálja.
 * Az alapvető tulajdonságok közé tartozik például a személy neve, raktára stb. Tehát azokat az információkat reprezentálja az objektum, amelyekkel minden szereplő kötelezően rendelkezik.
 */
public abstract class Person implements ActionListener {
    private final String name;
    private final List<Item> inventory = new ArrayList<>();
    private final List<Passive> damageHelpItems = new ArrayList<>();
    private final List<Passive> gasHelpItems = new ArrayList<>();
    private Room position;
    private boolean notConscious = false;
    private final Timer knockOutTimer = new Timer(1000, this);
    private int cooldown;
    private final Timer gasTimer = new Timer(1000, this);

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

    /**
     * damageHelpItems getter
     * @return A sebzés ellen segítő tárgyak listája
     */
    public List<Passive> getDamageHelpItems() { return damageHelpItems; }

    /**
     * gasHelpItems getter
     * @return A gáz ellen segítő tárgyak listája
     */
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

    /**
     * setPosNull függvény
     * NULL-ra állítja ennek a személynek a pozícióját
     */
    public void setPosNull() { position = null; }

    /**
     * setStartPosition függvény
     * Beállítja a személy helyét
     * @param r A személy új pozíciója (szoba)
     */
    public void setStartPosition(Room r) {
        position = r;
        r.getPersons().add(this);
    }

    /**
     * NotConscious getter
     * @return A személy eszméleti állapota
     */
    public boolean getNotConscious() { return notConscious; }

    /**
     * setNotConsciousAndTimer függvény
     * Elkábítja a személyt és hozzáadja a tárgykészletét a szobához,
     * majd újraindítja a kábultság időzítőt
     */
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

    /**
     * boardCleanerConscious absztrakt függvény
     * Ez a függvény valósítja meg a tanárok kábítását
     */
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

    /**
     * gasTimer gettere
     * @return gasTimer
     */
    public Timer getGasTimer() { return gasTimer; }

    /**
     * Person konstruktor
     * A személy nevét állítja be
     * @param name A személy neve
     */
    protected Person(String name) {
        this.name = name;
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

    /**
     * actionPerformed függvény
     * A Visszaszámláló vezérléséhez szükséges eseménykezelő
     */
    @Override
    public void actionPerformed(ActionEvent e) {}

    /**
     * getView absztrakt függvény
     * Megadja az alakzatát a személynek a grafikus felületen
     * @return
     */
    public abstract Component getView();
}
