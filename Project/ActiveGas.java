public class ActiveGas extends Item {
    public ActiveGas(String name) {
        super(name);
        System.out.println("ActiveGas constructor!");
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
    public void use(Student s) { s.getPosition().setGas(); }
    public void use(Professor p) {}
}
