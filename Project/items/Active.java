package items;

import characters.Professor;
import characters.Student;
import roomanddoor.Room;

/**
 * Az Aktív objektum az aktív tárgyak funkcióit implementálja, tehát hogy az aktív tárggyal
 * milyen előnyhöz tudjuk magunkat juttatni és hogy ez az előny mit is csinál.
 * Az „aktív” tárgyak jelentik azokat a tárgyakat, amelyeket valamilyen inputtal használni
 * tudunk és csak akkor kapjuk meg ezt a fejlesztést vagy bónuszt.
 */
public abstract class Active extends Item {
    /**
     * Aktív konstruktor: a megadott névvel létre hoz egy aktív tárgyat
     */
    protected Active(String name) {
        super(name);
    }

    /**
     * pickedUpBy függvény: átadja ezt a tárgyat s hallgatónak, majd kitörli a sobából
     * @param s
     */
    public void pickedUpBy(Student s) {
        Room r = s.getPosition();
        if(r.getAfterCleanCount() > 0) {
            s.addItemToInventory(this);
            r.removeItem(this);
        }
    }

    /**
     * pickedUpBy függvény: átadja ezt a tárgyat s tanárnak, majd kitörli a sobából
     * @param p
     */
    public void pickedUpBy(Professor p) {
        Room r = p.getPosition();
        if(r.getAfterCleanCount() > 0) {
            p.addItemToInventory(this);
            r.removeItem(this);
        }
    }

    /**
     * drop függvény: szól s hallgatónak, hogy dobja el ezt a tárgyat
     * @param s
     */
    public void drop(Student s) {}

    /**
     * drop függvény: szól p tanárnak, hogy dobja el ezt a tárgyat
     * @param p
     */
    public void drop(Professor p) {}

    /**
     * use függvény: a tárgy használatáért felelős
     * @param s
     */
    public void use(Student s) {}

    /**
     * use függvény: a tárgy használatáért felelős
     * @param p
     */
    public void use(Professor p) {}
}
