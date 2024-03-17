public class Student extends Person {
    public Student(String name) {
        super(name);
    }
    public void pickUp(Item t) {
        t.pickedUpBy(this);
    }
}
