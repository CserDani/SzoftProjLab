package characters;

import guimvc.Game;
import items.Item;
import items.Passive;
import items.Transistor;
import roomanddoor.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A Hallgató objektum igazából a játékost magát képviseli, és így a játékos tulajdonságait és a játékos működtetését szolgáló funkciókat implementálja.
 * Ezekért az információkért és funkciókért felelős, tehát csak azért, hogy a játékos úgy tudja játszani a játékot, ahogy azt a játék szabályai előírják.
 */
public class Student extends Person implements ActionListener {
    private int health;
    private Transistor prevUsedTrans;
    private int immunityCounter = 0;
    private final Timer immunityTimer = new Timer(1000, this);
    private Game gameObserver = null;

    /**
     * boardCleanerConscious függvény
     * Override a Person-ben szereplő absztrakt függvényhez,
     */
    public void boardCleanerConscious() {
        //Board cleaner can't stun a student, so this function's implementation is empty
    }

    /**
     * Health getter
     * @result A hallgató életereje
     */
    public int getHealth() { return health; }

    /**
     * UsedTrans getter
     * @result Az előzőleg használt tranzisztor
     */
    public Transistor getUsedTrans() { return prevUsedTrans; }

    /**
     * UsedTrans setter
     * @param t Az előzőleg használt tranzisztor
     */
    public void setUsedTrans(Transistor t) { this.prevUsedTrans = t; }

    /**
     * UsedTransNull setter
     * Az előzőleg használt tranzisztort törli
     */
    public void setUsedTransNull() { this.prevUsedTrans = null; }

    /**
     * addImmunityCounter
     * Növeli az immunitás számláló
     * @param count A növelés mértéke
     */
    public void addImmunityCounter(int count) {
        this.immunityCounter += count;
    }

    /**
     * immunityCounter getter
     * @return Az immunitás számláló
     */
    public int getImmunityCounter() { return immunityCounter; }

    /**
     * startImmunityTimer függvény
     * Az immunitás időztőt indítja el
     */
    public void startImmunityTimer() {
        immunityTimer.restart();
    }

    /**
     * stopImmunityCounter függvény
     * Megállítja az immunitás időzítőt
     */
    public void stopImmunityTimer() {
        immunityTimer.stop();
    }

    /**
     * Student konstruktor
     * Beállítja a hallgató nevét, életerejét és az előzőleg használt tranzisztort
     * @param name Az oktató neve
     */
    public Student(String name) {
        super(name);
        this.health = 100;
        this.prevUsedTrans = null;
    }

    /**
     * pickUp függvény
     * Egy tárgy felvételéért felelős
     * @param t A tárgy
     */
    public void pickUp(Item t) {
        if (!getNotConscious() && (getPosition().getAfterCleanCount() > 0) && (getInventorySize() < 5)) {
            t.pickedUpBy(this);
        }
    }

    /**
     * dropItem függvény
     * Egy tárgy eldobásáért felelős
     * @param t A tárgy
     */
    public void dropItem(Item t) {
        if (!getNotConscious()) {
            getInventory().remove(t);
            getPosition().addItem(t);
            t.drop(this);
        }
    }

    /**
     * useItem függvény
     * Egy tárgy használatáért felelős
     * @param t A tárgy
     */
    public void useItem(Item t) {
        if(!getNotConscious()) {
            t.use(this);
        }
    }

    /**
     * move függvény
     * Függvény egy oktató mozgatásához
     * @param d Az ajtó, amin a hallgató átmegy
     */
    public void move(Door d) {
        if(!getNotConscious()) {
            d.movedBy(this);
        }
    }

    /**
     * teleport függvény
     * Függvény egy hallgató teleportálásához
     * @param r A szoba, ahova a hallgató teleportál
     */
    public void teleport(Room r) {
        r.teleportRoom(this);
    }

    /**
     * getDamaged függvény
     * Függvény egy hallgató sebződéséhez
     */
    public void getDamaged() {
        if(immunityCounter == 0) {
            if (getDamageHelpItems().isEmpty()) {
                health -= 10;
            } else {
                Passive passive = getDamageHelpItems().get(0);
                passive.durabilityDecr();
                if (passive.getDurability() == 0) {
                    getInventory().remove(passive);
                    getDamageHelpItems().remove(passive);
                }
            }
        }
    }

    /**
     * removeItem függvény
     * Eltávolít egy bizonyos tárgyat
     * @param item A tárgy, amit eltávolít
     */
    public void removeItem(Item item) {
        if(getInventory().contains(item)) {
            boolean inGasHelp = getGasHelpItems().removeIf(p -> p == item);
            getDamageHelpItems().removeIf(p -> p == item);
            getInventory().remove(item);
            getPosition().addItem(item);

            if(inGasHelp && getPosition().getIsGassed()) {
                getGasTimer().stop();
                setNotConscious();
            }
        }
    }

    /**
     * notifyGameObserver függvény
     * Értesíti a gameObserver-t
     */
    public void notifyGameObserver() {
        if(gameObserver != null) {
            this.gameObserver.notifyGameData();
        }
    }

    /**
     * gameObserber settere
     * @param game A beállított gameObserver
     */
    public void setGameObserver(Game game) { this.gameObserver = game; }

    /**
     * actionPerformed függvény
     * A Visszaszámláló vezérléséhez szükséges eseménykezelő
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getKnockOutTimer()) {
            decrCooldown();
            if (getCooldown() == 0) {
                stopTimer();
                setNotConsciousFalse();
            }
        }

        if(e.getSource() == getGasTimer()) {
            if(getPosition().getIsGassed()) {
                Passive gasItem = getGasHelpItems().get(0);
                gasItem.durabilityDecr();
                if (gasItem.getDurability() == 0) {
                    getGasHelpItems().remove(gasItem);
                    getInventory().remove(gasItem);
                    getGasTimer().stop();
                    setNotConscious();
                }
            } else {
                getGasTimer().stop();
            }
        }

        if(e.getSource() == immunityTimer) {
            immunityCounter--;
            if(immunityCounter == 0) {
                stopImmunityTimer();
                for(int i = 0; i < getPosition().getProfcount(); i++) {
                    getDamaged();
                }
            }
        }

        notifyGameObserver();
    }

    /**
     * getView függvény
     * Megadja az alakzatát a tanuló a grafikus felületen
     * @return A tanuló alakzata a grafikus felületen.
     */
    @Override
    public Component getView() {
        JLabel shape = new JLabel(new ImageIcon("Resources/student.png"));
        shape.setPreferredSize(new Dimension(25, 25));
        return shape;
    }
}
