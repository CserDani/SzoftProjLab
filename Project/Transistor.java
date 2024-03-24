public class Transistor extends Item {
    private Room position;
    private Transistor pair;
    private boolean paired = false;
    private boolean dropped = false;
    private boolean activated = false;
    public Room getPosition() { return position; }
    public void setPair(Transistor t) {
        this.pair = t;
        this.paired = true;
    }
    public Transistor getPair() { return pair; }
    public void setPairedOff() {
        this.paired = false;
        this.pair = null;
    }
    public void setActivated() {
        this.activated = !activated;
    }
    public Transistor(String name) {
        super(name);
        System.out.println("Transistor constructor!");
    }
    public void pickedUpBy(Student s) {
        if(!activated) {
            s.addItemToInventory(this);
            Room r = s.getPosition();
            r.removeItem(this);
        }
    }
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
    public void drop(Student s) {
        if(paired) {
            if (!pair.dropped) {
                position = s.getPosition();
                dropped = true;
            } else {
                Transistor thisPair = this.getPair();
                s.teleport(thisPair.getPosition());
                thisPair.setPairedOff();
                thisPair.setActivated();
                this.setPairedOff();
                this.setActivated();
            }
        }
    }
    public void drop(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
    public void use(Student s) {
        if(!paired) {
            if(s.getUsedTrans() == null) {
                s.setUsedTrans(this);
                this.setActivated();
            } else if(s.getUsedTrans() != this) {
                Transistor first = s.getUsedTrans();
                first.setPair(this);
                this.setPair(first);
                this.setActivated();
                s.setUsedTransNull();
            }
        }
    }
    public void use(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
}