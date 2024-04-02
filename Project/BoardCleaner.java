public class BoardCleaner extends Active {
    public BoardCleaner(String name) {
        super(name);
    }

    @Override
    public void pickedUpBy(Professor p) {}

    @Override
    public void use(Student s) {
        Room r = s.getPosition();
        for(Person p : r.getPersons()) {
            p.boardCleanerConscious();
        }
        s.getInventory().remove(this);
    }
}
