package items;

import characters.Person;
import characters.Professor;
import characters.Student;
import roomanddoor.Room;
/**
 * Aktív tárgy, ennek segítségével a hallgatók megbéníthatják a szobában lévő oktatókat.
 */
public class BoardCleaner extends Active {
    /**
     * BoardCleaner konstruktor: A megadott névvel létrehoz egy BoardCleaner tárgyat.
     * @param name A tárgy neve
     */
    public BoardCleaner(String name) {
        super(name);
    }

    /**
     * pickedUpBy függvény, oktató esetén üres implementáció, mivel oktató nem veheti fel.
     * @param p Az oktató
     */
    @Override
    public void pickedUpBy(Professor p) {
        //Professor can't pick it up, it overrides default function in Active as Professor can pick up other items
    }

    /**
     * use függvény, a tárgyhasználatot kezeli le. Hatására megbénulnak az adott szobában lévő oktatók.
     * @param s A hallgató, aki használja a tárgyat.
     */
    @Override
    public void use(Student s) {
        Room r = s.getPosition();
        for(Person p : r.getPersons()) {
            p.boardCleanerConscious();
        }
        s.getInventory().remove(this);
    }
}
