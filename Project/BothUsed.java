public class BothUsed implements TransStrategy {
    public void execute(Student s, Transistor t) {
        Transistor pair = t.getPair();
        s.teleport(pair.getPosition());
        pair.setPairedOff();
        pair.setActivated();
        t.setPairedOff();
        t.setActivated();
    }
}