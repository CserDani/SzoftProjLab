public class ActiveGas extends Item {
    public ActiveGas(String name) {
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
