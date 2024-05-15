public class AirFreshener extends Active {
    /**
     * Aktív konstruktor: a megadott névvel létre hoz egy aktív tárgyat
     *
     * @param name
     */
    public AirFreshener(String name) {
        super(name);
    }
    /**
     * drop függvény: szól s hallgatónak, hogy dobja el ezt a tárgyat
     * @param s
     */
    @Override
    public void drop(Student s) {
        if(s.getPosition().getIsGassed()) {
            s.getPosition().setGas();
            s.getPosition().removeItem(this);
        }
    }

    /**
     * drop függvény: szól p tanárnak, hogy dobja el ezt a tárgyat
     * @param p
     */
    @Override
    public void drop(Professor p) {
        if(p.getPosition().getIsGassed()) {
            p.getPosition().setGas();
            p.getInventory().remove(this);
        } else {
            p.getPosition().addItem(this);
            p.getInventory().remove(this);
        }
    }
}
