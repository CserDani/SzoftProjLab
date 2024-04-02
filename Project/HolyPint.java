import java.util.Random;

public class HolyPint extends Active {
    private int time;
    private boolean wasUsed = false;
    private Random r = new Random();
    public HolyPint(String name) {
        super(name);
        this.time = 10;
    }

    @Override
    public void pickedUpBy(Professor p) {}

    @Override
    public void use(Student s) {
        if(!wasUsed) {
            s.setImmunityCounter(time);
            s.startImmunityTimer();
            if(s.getInventorySize() > 1) {
                int idx;
                do {
                    idx = r.nextInt(s.getInventorySize());
                } while (s.getInventory().get(idx) == this);
                s.dropItem(s.getInventory().get(idx));
            }
        }
    }

    @Override
    public void drop(Student s) {
        if(s.getImmunityCounter() > 0) {
            s.setImmunityCounter(0);
            s.stopImmunityTimer();
        }
        s.getPosition().removeItem(this);
    }
}
