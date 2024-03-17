public class OneUsed implements TransStrategy {
    public void execute(Student s, Transistor t) {
        Transistor first = s.getUsedTrans();
        first.setPair(t);
        t.setPair(first);
        t.setActivated();
        s.setTransNONE();
        s.setUsedTransNone();
    }
}
