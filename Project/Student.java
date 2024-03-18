public class Student extends Person {
    public enum TransistorsUsed { NONEUSED, ONEUSED }
    private int health;
    private Transistor usedTrans;
    private TransistorsUsed tu = TransistorsUsed.NONEUSED;

    public int getHealth() { return health; }
    public TransistorsUsed getTu() { return tu; }
    public void setTransNONE() { tu = TransistorsUsed.NONEUSED; }
    public void setTransONEUSED() { tu = TransistorsUsed.ONEUSED; }
    public Transistor getUsedTrans() { return usedTrans; }
    public void setUsedTrans(Transistor t) { this.usedTrans = t; }
    public void setUsedTransNone() { this.usedTrans = null; }
    public Student(String name) {
        super(name);
        this.health = 100;
        System.out.println("Student constructor!");
    }
    public void pickUp(Item t) {
        if(getInventorySize() < 5) {
            t.pickedUpBy(this);
        }
    }
    public void dropItem(Item t) {
        getInventory().remove(t);
        getPosition().addItem(t);
        t.drop(this);
    }
    public void useItem(Item t) {
        t.use(this);
    }
    public void move(Door d) {
        d.movedBy(this);
    }
    public void teleport(Room r) {
        r.teleportRoom(this);
    }
    public void getDamaged() {
        health -= 10;
    }
}
