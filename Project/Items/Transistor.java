package Items;

import Characters.Professor;
import Characters.Student;
import RoomAndDoor.Room;

/**
 * A Tranzisztor objektum egy különleges tárgy, így az eltérő „képessége” miatt, célszerű egy saját objektumot létrehozni.
 * Az objektum a Tranzisztor információinak tárolását, illetve a képességét valósítja meg.
 */
public class Transistor extends Item {
    private Room position;
    private Transistor pair;
    private boolean paired = false;
    private boolean dropped = false;
    private boolean activated = false;

    /**
     * Position getter
     * @result A tranzisztor pozíciója (szoba)
     */
    public Room getPosition() { return position; }

    /**
     * Pair setter
     * Egy tranzisztor párját állítja be
     * @param t A másik tranzisztor
     */
    public void setPair(Transistor t) {
        this.pair = t;
        this.paired = true;
    }

    /**
     * Pair getter
     * @result A tranzisztor párja
     */
    public Transistor getPair() { return pair; }

    /**
     * PairedOff setter
     * A tranzisztor párját törli
     */
    public void setPairedOff() {
        this.paired = false;
        this.pair = null;
    }

    /**
     * Activated setter
     * A tranzisztort aktivált vagy nem aktivált állapotra állítja
     */
    public void setActivated() {
        this.activated = !activated;
    }

    /**
     * Transistor konstruktor
     * A tranzisztor nevét állítja be
     * @param name A tranzisztor neve
     */
    public Transistor(String name) {
        super(name);
    }

    /**
     * pickedUpBy függvény
     * Egy hallgató inventory-jába elhelyezi a tranzisztor, majd a szobából törli azt
     * @param s A hallgató, aki felvette a tranzisztort
     */
    public void pickedUpBy(Student s) {
        if(s.getPosition().getAfterCleanCount() > 0) {
            if (!activated) {
                s.addItemToInventory(this);
                Room r = s.getPosition();
                r.removeItem(this);
            }
        }
    }

    /**
     * pickedUpBy függvény
     * Üres implementáció, mert oktató nem vehet fel tranzisztort
     * @param p Az oktató, aki felvette (volna) a tranzisztort
     */
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }

    /**
     * drop függvény
     * A tranzisztor eldobásáért felelős
     * @param s A hallgató, aki eldobta a tranzisztort
     */
    public void drop(Student s) {
        if(paired) {
            if (!pair.dropped) {
                position = s.getPosition();
                dropped = true;
            } else {
                Transistor thisPair = this.getPair();
                s.teleport(thisPair.getPosition());
                thisPair.setPairedOff();
                thisPair.setActivated();
                this.setPairedOff();
                this.setActivated();
            }
        }
    }

    /**
     * drop függvény
     * Üres implementáció, mert oktató nem vehet fel tranzisztort
     * @param p Az oktató, aki eldobta (volna) a tranzisztort
     */
    public void drop(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }

    /**
     * use függvény
     * A tranzisztor használatáért felelős
     * @param s A hallgató, aki használta a tranzisztort
     */
    public void use(Student s) {
        if(!paired) {
            if(s.getUsedTrans() == null) {
                s.setUsedTrans(this);
                this.setActivated();
            } else if(s.getUsedTrans() != this) {
                Transistor first = s.getUsedTrans();
                first.setPair(this);
                this.setPair(first);
                this.setActivated();
                s.setUsedTransNull();
            }
        }
    }

    /**
     * use függvény
     * Üres implementáció, mert oktató nem vehet fel tranzisztort
     * @param p Az oktató, aki használta (volna) a tranzisztort
     */
    public void use(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
}