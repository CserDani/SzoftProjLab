/**
 * Olyan, mint egy aktív tárgy, viszont ez a szobák elgázosításához van használva
 */
public class ActiveGas extends Item {
    /**
     * AktívGáz konstruktor: a megadott névvel létre hoz egy aktív gázt
     * @param name
     */
    public ActiveGas(String name) {
        super(name);
        System.out.println("ActiveGas constructor!");
    }

    /**
     * pickedUpBy függvény: átadja ezt a tárgyat s hallgatónak, majd kitörli a sobából
     * @param s
     */
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }
    /**
     * pickedUpBy függvény: átadja ezt a tárgyat s tanárnak, majd kitörli a sobából
     * @param p
     */
    public void pickedUpBy(Professor p) {
        p.addItemToInventory(this);
        Room r = p.getPosition();
        r.removeItem(this);
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
    public void use(Student s) { s.getPosition().setGas(); }
    /**
     * use függvény: a tárgy használatáért felelős
     * @param p
     */
    public void use(Professor p) {}
}
