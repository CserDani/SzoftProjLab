public class Active extends Item {

    public Active(String name) {
        super(name);
    }
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
    }
    public void pickedUpBy(Professor p) {
        p.addItemToInventory(this);
    }
    public void drop(Person p) {}
    public void use(Student s) {}
    public void use(Professor p) {}
}
