public class Logarlec extends Item {
    public Logarlec(String name) {
        super(name);
        System.out.println("Logarlec constructor!");
    }
    public void winGame() {
        System.out.println("Game Won!");
    }
    public void pickedUpBy(Student s) {
        s.addItemToInventory(this);
        Room r = s.getPosition();
        r.removeItem(this);
        winGame();
    }
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
    public void drop(Student s) {}
    public void drop(Professor p) {}
    public void use(Student s) {}
    public void use(Professor p) {}
}
