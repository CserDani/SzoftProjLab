
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
    }
    public void pickUp(Item t) {
        if(getInventorySize() < 5) {
            t.pickedUpBy(this);
        }
    }
    public void useItem(Item t) {
        t.use(this);
    }
    public void move(Room sz) {
        if(sz.isNotFull()) {
            sz.movedBy(this);
        }
    }
    public void getDamaged() {
        health -= 10;
    }
}
