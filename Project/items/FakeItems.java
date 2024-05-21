package items;

import characters.Professor;
import characters.Student;
import roomanddoor.Room;

public abstract class FakeItems extends Item {
    protected FakeItems(String name) {
        super(name);
    }

    @Override
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }

    @Override
    public void pickedUpBy(Professor p) {}

    @Override
    public void drop(Student s) {}

    @Override
    public void drop(Professor p) {}

    @Override
    public void use(Student s) {}

    @Override
    public void use(Professor p) {}
}
