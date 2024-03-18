public class Transistor extends Item {
    private Room position;
    private Transistor pair;
    private boolean paired = false;
    private boolean dropped = false;
    private boolean activated = false;
    private TransStrategy strategy;
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
                this.strategy = new BothUsed();
                strategy.execute(s, this);
            }
        }
    }
    public void drop(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
    public void use(Student s) {
        if(!paired) {
            if(s.getTu() == Student.TransistorsUsed.NONEUSED) {
                this.strategy = new NoneUsed();
                strategy.execute(s, this);
            } else if(s.getTu() == Student.TransistorsUsed.ONEUSED && (s.getUsedTrans() != this)) {
                this.strategy = new OneUsed();
                strategy.execute(s, this);
            }
        }
    }
    public void use(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
}