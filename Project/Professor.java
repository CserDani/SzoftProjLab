import java.awt.event.ActionEvent;
/**
 * Az Oktató objektum, ahogy a neve is mutatja, az oktatókat, tehát az ellenfeleket testesíti meg. Ahogyan a Hallgató objektum, így ez is saját működéséért felelős, tehát azért, hogy az ellenfeleink se hazudtolják meg a játék törvényeit, és csak úgy mozogjanak, és csináljanak más cselekvést, hogy az a szabályok szerint történjen.
 */
public class Professor extends Person {

    public void boardCleanerConscious() {
        if(!getNotConscious()) {
            getPosition().decrProfCount();
        }
        setNotConsciousAndTimer();
    }

    /**
     * Professor konstruktor
     * Az oktató nevét állítja be
     * @param name Az oktató neve
     */
    public Professor(String name) {
        super(name);
        System.out.println("Professor constructor!");
    }

    /**
     * ProfPosition setter
     * Az oktató pozícióját (szoba) állítja be
     * @param r A szoba
     */
    public void setProfPosition(Room r) {
        this.setPosition(r);
        r.incProfCount();
    }

    /**
     * pickUp függvény
     * Egy tárgy felvételéért felelős
     * @param t A tárgy
     */
    public void pickUp(Item t) {
        if (!getNotConscious()) {
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
        if(!getNotConscious()) {
            getInventory().remove(t);
            t.drop(this);
            getPosition().addItem(t);
        }
    }

    /**
     * useItem függvény
     * Egy tárgy használatáért felelős
     * @param t A tárgy
     */
    public void useItem(Item t) {
        if (!getNotConscious()) {
            t.use(this);
        }
    }

    /**
     * move függvény
     * Függvény egy oktató mozgatásához
     * @param d Az ajtó, amin az oktató átmegy
     */
    public void move(Door d) {
        if(!getNotConscious()) {
            d.movedBy(this);
        }
    }

    /**
     * teleport függvény
     * Függvény egy oktató teleportálásához
     * Üres implementáció, mert oktató nem vehet fel tranzisztort
     * @param r A szoba, ahova az oktató teleportál(na)
     */
    public void teleport(Room r) {
        //Professor can't pick up Transistor, so can't teleport.
    }

    /**
     * getDamaged függvény
     * Függvény egy oktató sebződéséhez
     * Üres implementáció, mert oktató nem sebződhet
     */
    public void getDamaged() {
        //Professor can't be damaged (It can't die).
    }

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
                getPosition().damageAll();
                getPosition().incProfCount();
            }
        }

        if(e.getSource() == getGasTimer()) {
            Passive gasItem = getGasHelpItems().get(0);
            gasItem.durabilityDecr();
            if(gasItem.getDurability() == 0) {
                getGasHelpItems().remove(gasItem);
                getInventory().remove(gasItem);
                getGasTimer().stop();
                setNotConscious();
            }
        }
    }
}
