package items;

import characters.Professor;
import characters.Student;

import java.util.Random;

/**
 * Aktív tárgy, az oktatók elleni védelmet szolgálja a hallgatók számára
 */
public class HolyPint extends Active {
    private final int time;
    private final Random r = new Random();

    /**
     * HolyPint konstruktor: A megadott névvel létrehoz egy HolyPint tárgyat és annak lejárati idejét beállítja.
     * @param name A tárgy neve
     */
    public HolyPint(String name) {
        super(name);
        this.time = 10;
    }

    /**
     * pickedUpBy függvény, mely professzor esetén üres implementáció, mivel professzor nem vehet fel HolyPint tárgyat.
     * @param p Az oktató
     */
    @Override
    public void pickedUpBy(Professor p) {
        //Professor can't pick it up, it overrides default function in Active as Professor can pick up other items
    }

    /**
     * use függvény, a tárgyhasználatot kezeli le. Hozzáadja és elindítja az immunitási időhöz tartozó Timer-t, illetve megvalósítja, hogy használatkor a hallgató az egyik tárgyat elejtse.
     * @param s A hallgató
     */
    @Override
    public void use(Student s) {
        s.addImmunityCounter(time);
        s.startImmunityTimer();
        s.getInventory().remove(this);
        if(s.getInventorySize() > 0) {
            int idx = r.nextInt(s.getInventorySize());
            Item item = s.getInventory().get(idx);
            s.removeItem(item);
        }
    }

    /**
     * drop függvény, a tárgy eldobásáért felelős. Ezesetben üres implementáció.
     *
     * @param s A hallgató
     */
    @Override
    public void drop(Student s) {
        //Nothing happens if it's dropped
    }
}
