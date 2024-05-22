package items;

import characters.Professor;
import characters.Student;
import roomanddoor.Room;

/**
 * Passzív tárgy, a gázos szobák elleni védelmet szolgálja.
 */
public class FFP2 extends Passive {
    /**
     * FFP2 konstruktor: a megadott névvel és 10-es durability-vel létre hoz egy FFP2 tárgyat
     * @param name
     */
    public FFP2(String name) {
        super(name, 10);
    }

    /**
     * pickedUpBy függvény, a paraméterként kapott hallgató inventory-jába belehelyezi a tárgyat, majd a szobából kitörli azt. Elmenti a hallgató gázos szobák elleni védelmét reprezentáló listába.
     *
     * @param s a hallgató
     */
    @Override
    public void pickedUpBy(Student s) {
        Room r = s.getPosition();
        if(r.getAfterCleanCount() > 0) {
            s.addItemToInventory(this);
            s.getGasHelpItems().add(this);
            r.removeItem(this);
        }
    }

    /**
     * pickedUpBy függvény, a paraméterként kapott oktató inventory-jába belehelyezi a tárgyat, majd a szobából kitörli azt. Elmenti az oktató gázos szobák elleni védelmét reprezentáló listába.
     *
     * @param p az oktató
     */
    @Override
    public void pickedUpBy(Professor p) {
        Room r = p.getPosition();
        if(r.getAfterCleanCount() > 0) {
            p.addItemToInventory(this);
            p.getGasHelpItems().add(this);
            r.removeItem(this);
        }
    }

    /**
     * drop függvény, a tárgy elejtéséért felelős. Kitörli a hallgató gáz elleni tárgyai közül az adott tárgyat.
     *
     * @param s a hallgató, aki elejti a tárgyat
     */
    @Override
    public void drop(Student s) {
        s.getGasHelpItems().remove(this);
    }

    /**
     * drop függvény, a tárgy elejtéséért felelős. Kitörli az oktató gáz elleni tárgyai közül az adott tárgyat.
     *
     * @param p az oktató, aki elejti a tárgyat
     */
    @Override
    public void drop(Professor p) {
        p.getGasHelpItems().remove(this);
    }
}
