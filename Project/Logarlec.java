public class Logarlec extends Item {
    public Logarlec(String name) {
        super(name);
    }
    public void winGame() {
        System.out.println("Game Won!");
    }
    public void pickedUpBy(Student s) {
        if(s.getInventorySize() < 5) {
            s.addToInventory(this);
            winGame();
        }
    }
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this Item
    }
}
