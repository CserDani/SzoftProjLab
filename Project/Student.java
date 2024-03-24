public class Student extends Person {
    private int health;
    private Transistor prevUsedTrans;

    public int getHealth() { return health; }
    public Transistor getUsedTrans() { return prevUsedTrans; }
    public void setUsedTrans(Transistor t) { this.prevUsedTrans = t; }
    public void setUsedTransNull() { this.prevUsedTrans = null; }
    public Student(String name) {
        super(name);
        this.health = 100;
        this.prevUsedTrans = null;
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
