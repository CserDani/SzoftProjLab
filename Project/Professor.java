public class Professor extends Person {
    public Professor(String name) {
        super(name);
    }
    public void pickUp(Item t) {
        if (getInventorySize() < 5) {
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
        //Professor can't be damaged (It can't die).
    }
}
