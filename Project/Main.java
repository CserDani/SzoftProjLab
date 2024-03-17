public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Student1");
        Professor p1 = new Professor("Professor1");
        Item a1 = new Active("Active1");
        Item logar = new Logarlec("Logarlec!");

        s1.pickUp(a1);
        s1.pickUp(logar);
        System.out.println(s1.getName() + ": " + s1.getInventorySize());

        p1.pickUp(a1);
        p1.pickUp(logar);
        System.out.println(p1.getName() + ": " + p1.getInventorySize());
    }
}
