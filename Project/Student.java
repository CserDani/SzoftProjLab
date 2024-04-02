/**
 * A Hallgató objektum igazából a játékost magát képviseli, és így a játékos tulajdonságait és a játékos működtetését szolgáló funkciókat implementálja.
 * Ezekért az információkért és funkciókért felelős, tehát csak azért, hogy a játékos úgy tudja játszani a játékot, ahogy azt a játék szabályai előírják.
 */
public class Student extends Person {
    private int health;
    private Transistor prevUsedTrans;

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

    /**
     * Student konstruktor
     * Beállítja a hallgató nevét, életerejét és az előzőleg használt tranzisztort
     * @param name Az oktató neve
     */
    public Student(String name) {
        super(name);
        this.health = 100;
        this.prevUsedTrans = null;
        System.out.println("Student constructor!");
    }

    /**
     * pickUp függvény
     * Egy tárgy felvételéért felelős
     * @param t A tárgy
     */
    public void pickUp(Item t) {
        if(!getNotConscious()) {
            if (getInventorySize() < 5) {
                t.pickedUpBy(this);
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
        if(getDamageHelpItems().isEmpty()) {
            health -= 10;
        } else {
            Passive passive = getDamageHelpItems().get(0);
            passive.durabilityDecr();
            if(passive.getDurability() == 0) {
                getInventory().remove(passive);
                getDamageHelpItems().remove(passive);
            }
        }
    }
}
