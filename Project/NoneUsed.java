public class NoneUsed implements TransStrategy {
    public void execute(Student s, Transistor t) {
        s.setTransONEUSED();
        s.setUsedTrans(t);
        t.setActivated();
    }
}
