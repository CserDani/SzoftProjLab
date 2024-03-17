public class Passive extends Item {
    public Passive(String name) {
        super(name);
    }
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }
    public void pickedUpBy(Professor p) {
        p.addItemToInventory(this);
        Room r = p.getPosition();
        r.removeItem(this);
    }
    public void drop(Student s) {}
    public void drop(Professor p) {}
    public void use(Student s) {}
    public void use(Professor p) {}
}
