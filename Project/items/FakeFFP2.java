package items;

import characters.Professor;
import roomanddoor.Room;

/**
 * A hamis FFP2-es maszk, olyan, mint a sima maszk, annyi különbséggel, hogy nem tud semmit.
 */
public class FakeFFP2 extends FakeItems {
    /**
     * FakeFFP2 konstruktor: a megadott névvel létre hoz egy Items.FakeItems.FakeFFP2 tárgyat
     * @param name
     */
    public FakeFFP2(String name) {
        super(name);
    }
    @Override
    public void pickedUpBy(Professor p) {
        p.addItemToInventory(this);
        Room r = p.getPosition();
        r.removeItem(this);
    }
}
