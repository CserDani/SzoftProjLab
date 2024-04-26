public class FFP2 extends Passive {
    public FFP2(String name) {
        super(name, 10);
    }

    @Override
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        s.getGasHelpItems().add(this);
        Room r = s.getPosition();
        r.removeItem(this);
    }

    @Override
    public void pickedUpBy(Professor p) {
        p.addItemToInventory(this);
        p.getGasHelpItems().add(this);
        Room r = p.getPosition();
        r.removeItem(this);
    }

    @Override
    public void drop(Student s) {
        s.getGasHelpItems().remove(this);
    }

    @Override
    public void drop(Professor p) {
        p.getGasHelpItems().remove(this);
    }
}
