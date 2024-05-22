package characters;

import items.Item;
import roomanddoor.*;

import javax.swing.*;
import java.awt.*;

public class Cleaner extends Person {
    /**
     * boardCleanerConscious függvény
     * Override a Person-ben szereplő absztrakt függvényhez,
     */
    public void boardCleanerConscious() {
        //Cleaner can't be made unconscious
    }

    /**
     * Cleaner konstruktor
     * Beállítja a takarító nevér
     * @param name A takarító neve
     */
    public Cleaner(String name) {
        super(name);
    }

    /**
     * pickUp függvény
     * Egy tárgy felvételéért felelős
     * @param t A tárgy
     */
    public void pickUp(Item t) {
        //Can't pick up items
    }
    /**
     * dropItem függvény
     * Egy tárgy eldobásáért felelős
     * @param t A tárgy
     */
    public void dropItem(Item t) {
        //Can't pick up items, so can't drop it
    }
    /**
     * useItem függvény
     * Egy tárgy használatáért felelős
     * @param t A tárgy
     */
    public void useItem(Item t) {
        //Can't pick up item, so can't use it
    }
    /**
     * move függvény
     * Függvény egy oktató mozgatásához
     * @param d Az ajtó, amin a hallgató átmegy
     */
    public void move(Door d) {
        d.movedBy(this);
    }
    /**
     * teleport függvény
     * A takarító nem tud teleportálni
     * @param r A szoba, ahova a takarító teleportálna, ha tudna teleportálni
     */
    public void teleport(Room r) {
        //Can't teleport
    }

    /**
     * getDamaged függvény
     * Egy takarító nem tud sebződni
     */
    public void getDamaged() {
        //Cleaner can't be damaged, can't die
    }

    /**
     * getView függvény
     * Megadja az alakzatát a takarítónak a grafikus felületen
     * @return A takarító alakzata a grafikus felületen.
     */
    @Override
    public Component getView() {
        JLabel shape = new JLabel(new ImageIcon("Resources/cleaner.png"));
        shape.setPreferredSize(new Dimension(25, 25));
        return shape;
    }
}