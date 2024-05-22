package items;

import characters.Student;
import roomanddoor.Room;

/**
 * Passzív tárgy, az oktatók elleni védelmet szolgálja a hallgatók számára
 */
public class TVSZ extends Passive {
    /**
     * TVSZ konstruktor: A megadott névvel és durability-vel létrehoz egy TVSZ tárgyat.
     * @param name A tárgy neve
     */
    public TVSZ(String name) {
        super(name, 3);
    }

    /**
     * pickedUpBy függvény, lekezeli a hallgató általi tárgyfelvételt. Hozzáadja a hallgató sebződés elleni tárgyaihoz az adott TVSZ tárgyat.
     * @param s A hallgató
     */
    @Override
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        s.getDamageHelpItems().add(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }

    /**
     * drop függvény, lekezeli a hallgató általi tárgyeldobást. Kitörli a hallgató sebződés elleni tárgyai közül az adott TVSZ tárgyat.
     * @param s A hallgató
     */
    @Override
    public void drop(Student s) {
        s.getDamageHelpItems().remove(this);
    }
}
