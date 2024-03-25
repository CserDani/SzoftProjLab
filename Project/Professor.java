import java.awt.event.ActionEvent;

public class Professor extends Person {
    public Professor(String name) {
        super(name);
        System.out.println("Professor constructor!");
    }

    public void setProfPosition(Room r) {
        this.setPosition(r);
        r.incProfCount();
    }
    public void pickUp(Item t) {
        if (!getNotConscious()) {
            if (getInventorySize() < 5) {
                t.pickedUpBy(this);
            }
        }
    }
    public void dropItem(Item t) {
        if(!getNotConscious()) {
            getInventory().remove(t);
            t.drop(this);
            getPosition().addItem(t);
        }
    }
    public void useItem(Item t) {
        if (!getNotConscious()) {
            t.use(this);
        }
    }
    public void move(Door d) {
        if(!getNotConscious()) {
            d.movedBy(this);
        }
    }
    public void teleport(Room r) {
        //Professor can't pick up Transistor, so can't teleport.
    }
    public void getDamaged() {
        //Professor can't be damaged (It can't die).
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getKnockOutTimer()) {
            decrCooldown();
            if (getCooldown() == 0) {
                stopTimer();
                setNotConsciousFalse();
                getPosition().damageAll();
                getPosition().incProfCount();
            }
        }
    }
}
