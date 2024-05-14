import javax.swing.Timer;
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
    private Game consObserver;

    public void boardCleanerConscious() {
        //Board cleaner can't stun a student, so this function's implementation is empty
    }

    /**
     * Health getter
     * @result Az hallgató életereje
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
    public void setImmunityCounter(int count) {
        this.immunityCounter = count;
    }
    public int getImmunityCounter() { return immunityCounter; }
    public void startImmunityTimer() {
        immunityTimer.restart();
    }

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
        if (!getNotConscious()) {
            if(getPosition().getAfterCleanCount() > 0) {
                if (getInventorySize() < 5) {
                    t.pickedUpBy(this);
                }
            }
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

    public void notifyConsObserver() {
        this.consObserver.notifyConsciousness();
    }

    public void setConsObserver(Game game) { this.consObserver = game; }

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
                notifyConsObserver();
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
                    notifyConsObserver();
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
    }

    @Override
    public Shape getView() {
        return new Rectangle(10,10, 20,20);
    }
}
