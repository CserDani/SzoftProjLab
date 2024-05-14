import java.util.Random;

public class HolyPint extends Active {
    private final int time;
    private final Random r = new Random();
    private boolean wasUsed = false;
    public HolyPint(String name) {
        super(name);
        this.time = 10;
    }

    @Override
    public void pickedUpBy(Professor p) {
        //Professor can't pick it up, it overrides default function in Active as Professor
        //can pick up other items
    }

    @Override
    public void use(Student s) {
        s.addImmunityCounter(time);
        s.startImmunityTimer();
        s.getInventory().remove(this);
        if(s.getInventorySize() > 0) {
            int idx;
            do {
                idx = r.nextInt(s.getInventorySize());
            } while (s.getInventory().get(idx) == this);
            Item item = s.getInventory().get(idx);
            s.removeItem(item);
        }
    }

    @Override
    public void drop(Student s) {
        //Nothing happens if it's dropped
    }
}
