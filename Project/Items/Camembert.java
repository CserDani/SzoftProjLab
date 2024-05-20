package Items;

import Characters.Person;
import Characters.Professor;
import Characters.Student;

/**
 * Olyan, mint egy aktív tárgy, viszont ez a szobák elgázosításához van használva
 */
public class Camembert extends Active {
    /**
     * Camembert konstruktor: a megadott névvel létre hoz egy Items.Camembert tárgyat
     * @param name
     */
    public Camembert(String name) {
        super(name);
    }

    /**
     * use függvény: a tárgy használatáért felelős
     * @param s
     */
    @Override
    public void use(Student s) {
        if(!s.getPosition().getIsGassed()) {
            s.getPosition().setGas();

            s.getInventory().remove(this);

            for (Person p : s.getPosition().getPersons()) {
                p.setNotConscious();
            }
        }
    }

    @Override
    public void use(Professor p) {
        if(!p.getPosition().getIsGassed()) {
            p.getPosition().setGas();

            for (Person person : p.getPosition().getPersons()) {
                person.setNotConscious();
            }

            p.getInventory().remove(this);
        }
    }
}
