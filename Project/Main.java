public class Main {
    public static void main(String[] args) {
        //Oktató sebzi a hallgatókat belépéskor
        /*
        Student s1 = new Student("AAA");
        Student s2 = new Student("CCC");
        Professor p1 = new Professor("BBB");
        Professor p2 = new Professor("DDD");

        Room szoba = new Room(false, 5);

        System.out.println(s1.getName() + " - " + s1.getHealth());
        System.out.println(s2.getName() + " - " + s2.getHealth());
        s1.move(szoba);
        s2.move(szoba);
        p1.move(szoba);
        p2.move(szoba);
        System.out.println(s1.getName() + " - " + s1.getHealth());
        System.out.println(s2.getName() + " - " + s2.getHealth());
        */

        //Teleport
        Student s1 = new Student("AAA");
        Professor p1 = new Professor("BBB");

        Transistor t1 = new Transistor("Transistor1");
        Transistor t2 = new Transistor("Transistor2");

        s1.pickUp(t1);
        s1.pickUp(t2);

        Room szoba1 = new Room(false, 5);
        Room szoba2 = new Room(false, 10);
        System.out.println(szoba1.getId());
        System.out.println(szoba2.getId());

        p1.move(szoba2);
        s1.move(szoba1);

        s1.useItem(t1);
        s1.useItem(t2);
        s1.dropItem(t1);

        s1.move(szoba2);
        s1.useItem(t2);
        System.out.println(s1.getHealth() + " - " + s1.getPosition().getId());
    }
}
