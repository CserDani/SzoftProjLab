
/**
 * A Passzív objektum a passzív tárgyak funkcióiért felelős. Hasonlóan, mint az Aktív objektum, csak a használata más.
 * A passzív tárgyak jelentik azokat a tárgyakat, amelyek csupán a magunknál tartással egy valamilyen bónuszt ad számunkra. Tehát ezt külön „aktiválni” nem kell.
 */
public abstract class Passive extends Item {
    private int durability;

    public int getDurability() { return durability; }

    /**
     * Passzív konstruktor, beállítja a passzív tárgy nevét.
     *
     * @param  name a tárgy megnevezése
     */
    protected Passive(String name, int durability) {
        super(name);
        this.durability = durability;
    }

    /**
     * pickedUpBy függvény, a paraméterként kapott hallgató inventory-jába belehelyezi a tárgyat, majd a szobából kitörli azt.
     *
     * @param s a hallgató
     */
    public void pickedUpBy(Student s) {}
    /**
     * pickedUpBy függvény, a paraméterként kapott oktató inventory-jába belehelyezi a tárgyat, majd a szobából kitörli azt.
     *
     * @param p a hallgató
     */
    public void pickedUpBy(Professor p) {}

    /**
     * drop függvény, a tárgy elejtéséért felelős.
     *
     * @param s a hallgató, aki elejti a tárgyat
     */
    public void drop(Student s) {}

    /**
     * drop függvény, a tárgy elejtéséért felelős.
     *
     * @param p az oktató, aki elejti a tárgyat
     */
    public void drop(Professor p) {}

    /**
     * drop függvény, a tárgy használatáért felelős.
     *
     * @param s a hallgató, aki használja a tárgyat
     */
    public void use(Student s) {}

    /**
     * drop függvény, a tárgy használatáért felelős.
     *
     * @param p az oktató, aki használja a tárgyat
     */
    public void use(Professor p) {}

    public void durabilityDecr() {
        durability--;
    }
}
