public abstract class Item {
    private String name;

    protected Item(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public abstract void pickedUpBy(Student s);
    public abstract void pickedUpBy(Professor p);
}
