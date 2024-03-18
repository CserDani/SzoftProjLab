public abstract class Item {
    private String name;

    protected Item(String name) {
        this.name = name;
        System.out.println("Item constructor!");
    }

    public String getName() { return name; }
    public abstract void pickedUpBy(Student s);
    public abstract void pickedUpBy(Professor p);
    public abstract void drop(Student s);
    public abstract void drop(Professor p);
    public abstract void use(Student s);
    public abstract void use(Professor p);
}
