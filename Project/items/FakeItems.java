package items;

import characters.Professor;
import characters.Student;
import roomanddoor.Room;

/**
 * A FakeItems osztálynak nincsen semmilyen felelőssége, ugyanis csak arra való, hogy a játékost megtévessze.
 */
public abstract class FakeItems extends Item {
    /**
     * FakeItems konstruktor: a megadott névvel létre hoz egy hamis tárgyat
     * @param name ezzel a névvel hozza létre a tárgyat
     */
    protected FakeItems(String name) {
        super(name);
    }

    /**
     * pickedUpBy függvény: felülírja a tanulók általi tárgyfelvevést
     * @param s a tanuló, aki felveszi ezt a tárgyat
     */
    @Override
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }

    /**
     * pickedUpBy függvény: felülírja a professzorok általi tárgyfelvevést
     * @param p a tanár, aki felveszi ezt a tárgyat
     */
    @Override
    public void pickedUpBy(Professor p) {}

    /**
     *drop függvény: felülírja a tanuló általi tárgy eldobást
     * @param s a tanuló, aki eldobja a tárgyat
     */
    @Override
    public void drop(Student s) {}

    /**
     * drop függvény: felülírja a professzor általi tárgy eldobást
     * @param p a tanár, aki eldobja a tárgyat
     */
    @Override
    public void drop(Professor p) {}

    /**
     * use függvény: felülírja a tanuló általi tárgyak használatát
     * @param s a tanuló, akire a tárgy hat
     */
    @Override
    public void use(Student s) {}

    /**
     * use függvény: felülírja a professzor általi tárgyak használatát
     * @param p a tanár, akire a tárgy hat
     */
    @Override
    public void use(Professor p) {}
}
