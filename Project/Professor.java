public class Professor extends Person {
    public Professor(String name) {
        super(name);
    }

    public void setProfPosition(Room r) {
        this.setPosition(r);
        r.incProfCount();
    }
    public void pickUp(Item t) {
        if (getInventorySize() < 5) {
            t.pickedUpBy(this);
        }
    }
    public void dropItem(Item t) {
        getInventory().remove(t);
        t.drop(this);
        getPosition().addItem(t);
    }
    public void useItem(Item t) {
        t.use(this);
    }
    public void move(Room sz) {
        sz.movedBy(this);
    }
    public void getDamaged() {
        //Professor can't be damaged (It can't die).
    }
}
