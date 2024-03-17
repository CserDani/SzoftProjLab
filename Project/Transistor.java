public class Transistor extends Item {
    public Transistor(String name) {
        super(name);
    }
    public void pickedUpBy(Student s) {
        if(s.getInventorySize() < 5) {
            s.addToInventory(this);
        }
    }
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
}
