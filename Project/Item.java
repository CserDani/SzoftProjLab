/**
 * A Tárgy objektum, ahogyan a Személy, a tárgyak alapvető információit tartalmazza,
 * és azokért felelős. Ezen kívül másért nem felelős, csak a tulajdonságait reprezentálja.
 */
public abstract class Item {
    private final String name;

    /**
     * Tárgy konstruktor: a megadott névvel létre hoz egy tárgyat
     * @param name ezzel a névvel hozza létre a tárgyat
     */
    protected Item(String name) {
        this.name = name;
    }

    /**
     * a name paraméter gettere
     * @return name paraméter
     */
    public String getName() { return name; }

    /**
     * pickedUpBy absztrakt függvény: a tárgyak tanulók általi felvételéért felelős
     * @param s a tanuló, aki felveszi ezt a tárgyat
     */
    public abstract void pickedUpBy(Student s);
    /**
     * pickedUpBy absztrakt függvény: a tárgyak tanárok általi felvételéért felelős
     * @param p a tanár, aki felveszi ezt a tárgyat
     */
    public abstract void pickedUpBy(Professor p);

    /**
     * drop absztrakt függvény: a tárgyak tanuló általi eldobásáért felel
     * @param s a tanuló, aki eldobja a tárgyat
     */
    public abstract void drop(Student s);
    /**
     * drop absztrakt függvény: a tárgyak tanárok általi eldobásáért felel
     * @param p a tanár, aki eldobja a tárgyat
     */
    public abstract void drop(Professor p);

    /**
     * use absztrakt függvény: a tárgyak hatásainak kiváltásáért felel
     * @param s a tanuló, akire a tárgy hat
     */
    public abstract void use(Student s);
    /**
     * use absztrakt függvény: a tárgyak hatásainak kiváltásáért felel
     * @param p a tanár, akire a tárgy hat
     */
    public abstract void use(Professor p);
}
