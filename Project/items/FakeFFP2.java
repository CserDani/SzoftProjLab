package items;

import characters.Professor;
import roomanddoor.Room;

public class FakeFFP2 extends FakeItems {
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
