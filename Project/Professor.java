public class Professor extends Person {
    public Professor(String name) {
        super(name);
    }
    public void pickUp(Item t) {
        t.pickedUpBy(this);
    }
}
