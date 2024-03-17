public class Transistor extends Item {
    private Room position;
    private Transistor pair;
    private boolean wasUsed = false;
    private boolean paired = false;
    private boolean dropped = false;
    public Transistor(String name) {
        super(name);
    }
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
    }
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
    public void drop(Person p) {
        if(paired) {
            position = p.getPosition();
            dropped = true;
        }
    }
    public void use(Student s) {
        if(!wasUsed && !paired) {
            wasUsed = true;
            if(s.getTu() == Student.TransistorsUsed.NONEUSED) {
                s.setTransONEUSED();
                s.setUsedTrans(this);
            } else if(s.getTu() == Student.TransistorsUsed.ONEUSED && (s.getUsedTrans() != this)) {
                Transistor first = s.getUsedTrans();
                first.paired = true;
                first.pair = this;
                first.wasUsed = false;
                this.paired = true;
                this.pair = first;
                this.wasUsed = false;
                s.setTransNONE();
                s.setUsedTransNone();
            }
        }

        if(paired && pair.dropped) {
            s.setPosition(pair.position);
            pair.paired = false;
            this.paired = false;
        }
    }
    public void use(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
}
