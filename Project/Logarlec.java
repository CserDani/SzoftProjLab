/**
 *  A Logarléc objektum, ahogy a neve is mutatja, a játék főeleme. Ez a tárgy az, amivel a játékos meg tudja nyerni a játékot.
 * Az objektum felel a játék megnyeréséért.
 */
public class Logarlec extends Item {
    /**
     * logarléc konstruktor: a megadott névvel létre hoz egy logarlécet
     * @param name ezzel a névvel hozza létre a logarlécet
     */
    public Logarlec(String name) {
        super(name);
    }

    /**
     * winGame függvény: feladata levezényelni a játék végét, miután a játékosok nyernek
     */
    public void winGame() {

    }

    /**
     * pickedUpBy absztrakt függvény: a tárgyak tanulók általi felvételéért felelős
     * @param s a tanuló, aki felveszi ezt a logarlécet
     */
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        Room r = s.getPosition();
        r.removeItem(this);
        winGame();
    }
    /**
     * pickedUpBy absztrakt függvény: a tárgyak tanárok általi felvételéért felelős
     * @param p a tanár, aki felveszi ezt a logarlécet
     */
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
    /**
     * drop absztrakt függvény: a tárgyak tanuló általi eldobásáért felel
     * @param s a tanuló, aki eldobja a logarlécet
     */
    public void drop(Student s) {}
    /**
     * drop absztrakt függvény: a tárgyak tanárok általi eldobásáért felel
     * @param p a tanár, aki eldobja a logarlécet
     */
    public void drop(Professor p) {}
    /**
     * use absztrakt függvény: a tárgyak hatásainak kiváltásáért felel
     * @param s a tanuló, akire a tárgy hat
     */
    public void use(Student s) {}
    /**
     * use absztrakt függvény: a tárgyak hatásainak kiváltásáért felel
     * @param p a tanár, akire a tárgy hat
     */
    public void use(Professor p) {}
}
