package items;

import characters.Person;
import characters.Professor;
import characters.Student;
import roomanddoor.Room;

public class BoardCleaner extends Active {
    public BoardCleaner(String name) {
        super(name);
    }

    @Override
    public void pickedUpBy(Professor p) {
        //Professor can't pick it up, it overrides default function in Active as Professor can pick up other items
    }

    @Override
    public void use(Student s) {
        Room r = s.getPosition();
        for(Person p : r.getPersons()) {
            p.boardCleanerConscious();
        }
        s.getInventory().remove(this);
    }
}
