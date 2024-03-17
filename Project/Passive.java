public class Passive extends Item {
    public Passive(String name) {
        super(name);
    }
    public void pickedUpBy(Student s) {
        if(s.getInventorySize() < 5) {
            s.addToInventory(this);
        }
    }
    public void pickedUpBy(Professor p) {
        if(p.getInventorySize() < 5) {
            p.addToInventory(this);
        }
    }
}
