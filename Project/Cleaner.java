public class Cleaner extends Person {
    protected Cleaner(String name) {
        super(name);
    }
    public void pickUp(Item t) {
        //Can't pick up items
    }
    public void dropItem(Item t) {
        //Can't pick up items, so can't drop it
    }
    public void useItem(Item t) {
        //Can't pick up item, so can' use it
    }
    public void move(Door d) {
        d.movedBy(this);
    }
    public void teleport(Room r) {
        //Can't teleport
    }

    public void getDamaged() {
        //Cleaner can't be damaged, can't die
    }
}