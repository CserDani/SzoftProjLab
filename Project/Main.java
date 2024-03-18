public class Main {
    /*public static void main(String[] args) {
        //Oktató sebzi a hallgatókat belépéskor

        Student s1 = new Student("AAA");
        Student s2 = new Student("CCC");
        Professor p1 = new Professor("BBB");
        Professor p2 = new Professor("DDD");

        Room szoba = new Room(false, false, 5);

        System.out.println(s1.getName() + " - " + s1.getHealth());
        System.out.println(s2.getName() + " - " + s2.getHealth());
        s1.move(szoba);
        s2.move(szoba);
        p1.move(szoba);
        p2.move(szoba);
        System.out.println(s1.getName() + " - " + s1.getHealth());
        System.out.println(s2.getName() + " - " + s2.getHealth());


        //Teleport

        Student s1 = new Student("AAA");
        Professor p1 = new Professor("BBB");
        Professor p2 = new Professor("CCC");

        Transistor t1 = new Transistor("Transistor1");
        Transistor t2 = new Transistor("Transistor2");

        Room szoba1 = new Room("Szoba1", false, false, 5);
        Room szoba2 = new Room("Szoba2", false, false, 10);

        szoba1.addNeighbour(szoba2, false);

        szoba1.addItem(t1);
        szoba1.addItem(t2);

        p1.setProfPosition(szoba2);
        p2.setProfPosition(szoba1);
        s1.setPosition(szoba1);
        System.out.println(s1.getHealth());

        s1.pickUp(t1);
        s1.pickUp(t2);

        s1.useItem(t2);
        s1.useItem(t1);

        s1.dropItem(t1);

        s1.move(szoba1.getNeighbourDoors().get(0));


        System.out.println(s1.getHealth() + " - " + s1.getPosition().getName());

        s1.dropItem(t2);

        System.out.println(s1.getHealth() + " - " + s1.getPosition().getName());


        //OneWay and no OneWay doors

        Room szoba1 = new Room("Szoba1", false, false, 5);
        Room szoba2 = new Room("Szoba2", false, false, 10);
        Room szoba3 = new Room("Szoba3", false, false, 10);

        szoba1.addNeighbour(szoba2, true);
        szoba1.addNeighbour(szoba3, false);

        Student s1 = new Student("Haha");
        s1.setPosition(szoba1);
        System.out.println(s1.getPosition().getName());

        s1.move(s1.getPosition().getNeighbourDoors().get(0));
        System.out.println(s1.getPosition().getName());
        s1.move(s1.getPosition().getNeighbourDoors().get(0));
        System.out.println(s1.getPosition().getName());

    }*/
}
