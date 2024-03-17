public class Logarlec extends Item {
    public Logarlec(String name) {
        super(name);
    }
    public void winGame() {
        System.out.println("Game Won!");
    }
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        winGame();
    }
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
    public void drop(Person p) {}
    public void use(Student s) {}
    public void use(Professor p) {}
}
