public abstract class FakeItems extends Item {
    protected FakeItems(String name) {
        super(name);
    }

    public void joked() {
        System.out.println("Haha it's fake!");
    }

    @Override
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        Room r = s.getPosition();
        r.removeItem(this);
        joked();
    }

    @Override
    public void pickedUpBy(Professor p) {}

    @Override
    public void drop(Student s) {}

    @Override
    public void drop(Professor p) {}

    @Override
    public void use(Student s) {}

    @Override
    public void use(Professor p) {}
}
