package characters;

import items.Item;
import roomanddoor.*;

import javax.swing.*;
import java.awt.*;

public class Cleaner extends Person {
    public void boardCleanerConscious() {
        //Cleaner can't be made unconscious
    }

    public Cleaner(String name) {
        super(name);
    }
    public void pickUp(Item t) {
        //Can't pick up items
    }
    public void dropItem(Item t) {
        //Can't pick up items, so can't drop it
    }
    public void useItem(Item t) {
        //Can't pick up item, so can't use it
    }
    public void move(Door d) {
        d.movedBy(this);
    }
    public void teleport(Room r) {
        //Can't teleport
    }

    public void getDamaged() {
        //Cleaner can't be damaged, can't die
    }

    @Override
    public Component getView() {
        JLabel shape = new JLabel(new ImageIcon("Resources/cleaner.png"));
        shape.setPreferredSize(new Dimension(25, 25));
        return shape;
    }
}
