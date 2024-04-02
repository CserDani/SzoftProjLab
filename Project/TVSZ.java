public class TVSZ extends Passive {
    public TVSZ(String name) {
        super(name, 3);
        System.out.println("TVSZ contructor!");
    }

    @Override
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        s.getDamageHelpItems().add(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }

    @Override
    public void pickedUpBy(Professor p) {
        p.addItemToInventory(this);
        p.getDamageHelpItems().add(this);
        Room r = p.getPosition();
        r.removeItem(this);
    }
}
