package Items;

import Characters.Student;
import RoomAndDoor.Room;

public class TVSZ extends Passive {
    public TVSZ(String name) {
        super(name, 3);
    }

    @Override
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        s.getDamageHelpItems().add(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }

    @Override
    public void drop(Student s) {
        s.getDamageHelpItems().remove(this);
    }
}
