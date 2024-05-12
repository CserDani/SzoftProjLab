import java.util.Random;

public class HolyPint extends Active {
    private final int time;
    private boolean wasUsed = false;
    private final Random r = new Random();
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
        if(!wasUsed && s.getImmunityCounter() == 0) {
            s.setImmunityCounter(time);
            s.startImmunityTimer();
            if(s.getInventorySize() > 1) {
                int idx;
                do {
                    idx = r.nextInt(s.getInventorySize());
                } while (s.getInventory().get(idx) == this);
                s.dropItem(s.getInventory().get(idx));
            }

            wasUsed = true;
        }
    }

    @Override
    public void drop(Student s) {
        if(s.getImmunityCounter() > 0 && wasUsed) {
            s.setImmunityCounter(0);
            s.stopImmunityTimer();
        }
        s.getPosition().removeItem(this);
    }
}
