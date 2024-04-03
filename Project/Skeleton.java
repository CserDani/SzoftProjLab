import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Skeleton {
    private static final Scanner scan = new Scanner(System.in);
    private static final List<Room> map = new ArrayList<>();
    private static Student studentActor = new Student("Hallgato");
    private static Professor professorActor = new Professor("Oktato");

    public static void buildOneRoomMapAndPlayer() {
        map.clear();

        //A Room nem gázosított és nem elátkozott, 1 kapacitású
        Room r = new Room("Szoba1", false, false, 1);
        map.add(r);

        studentActor.setPosition(r);
    }

    public static void buildTwoRoomMapAndPlayer() {
        map.clear();

        Room r1 = new Room("Szoba1", false, false, 2);
        Room r2 = new Room("Szoba2", false, false, 1);
        r1.addNeighbour(r2, false);
        map.add(r1);
        map.add(r2);

        studentActor.setPosition(r1);
    }

    public static void buildTwoRoomMapAndPlayerAndProf() {
        map.clear();

        Room r1 = new Room("Szoba1", false, false, 1);
        Room r2 = new Room("Szoba2", false, false, 2);
        r1.addNeighbour(r2, false);
        map.add(r1);
        map.add(r2);

        studentActor.setPosition(r1);
        professorActor.setProfPosition(r2);
    }

    public static void buildTwoRoomMapAndPlayerSecondGas() {
        map.clear();

        Room r1 = new Room("Szoba1", false, false, 1);
        Room r2 = new Room("Szoba2", true, false, 1);
        r1.addNeighbour(r2, false);
        map.add(r1);
        map.add(r2);

        studentActor.setPosition(r1);
    }

    public static void buildTwoWithItemsRoomNoCharacter() {
        map.clear();
        Room r1 = new Room("Szoba1", false, false, 1);
        Room r2 = new Room("Szoba2", true, false, 5);
        r1.addNeighbour(r2, false);
        map.add(r1);
        map.add(r2);
        Transistor t1 = new Transistor("t1");
        Transistor t2 = new Transistor("t2");
        Transistor t3 = new Transistor("t3");
        Transistor t4 = new Transistor("t4");
        r1.addItem(t1);
        r1.addItem(t2);
        r2.addItem(t3);
        r2.addItem(t4);
    }

    public static void buildOneWithItemsRoomNoCharacter() {
        map.clear();
        Room r1 = new Room("Szoba1", true, false, 5);
        map.add(r1);
        Transistor t1 = new Transistor("t1");
        Transistor t2 = new Transistor("t2");
        Transistor t3 = new Transistor("t3");
        Transistor t4 = new Transistor("t4");
        r1.addItem(t1);
        r1.addItem(t2);
        r1.addItem(t3);
        r1.addItem(t4);
    }
    public static void addItemToStudentRoom(Item t) {
        studentActor.getPosition().addItem(t);
    }
    public static void PickUpInput(int size) {
        System.out.println("Melyik targyat szeretne felvenni? (Szobaban levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < size; i++) {
                    if(i == sorszam) {
                        studentActor.pickUp(studentActor.getPosition().getItems().get(sorszam));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : studentActor.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : studentActor.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }
    public static void dropIteminput() {
        System.out.println("Melyik targyat szeretne eldobni? (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < studentActor.getInventory().size(); i++) {
                    if(i == sorszam) {
                        studentActor.dropItem(studentActor.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : studentActor.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : studentActor.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }
    public static void useItemInput() {
        System.out.println("Melyik targyat szeretne hasznalni (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < studentActor.getInventory().size(); i++) {
                    if(i == sorszam) {
                        studentActor.useItem(studentActor.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }
    public static void listItemsInStudentsRoom() {
        int j = 0;
        for(Room r : map) {
            if(r.getName().equals(studentActor.getPosition().getName())) {
                for(Item it : r.getItems()) {
                    System.out.println(j++ + "\t" + it.getName());
                }
            }
        }
    }
    public static int getStudentsRoomItemsSize() {
        for(Room r : map) {
            if(r.getName().equals(studentActor.getPosition().getName())) {
                return r.getItems().size();
            }
        }
        return -1;
    }
    public static void listItemsInStudentsInventory() {
        int j = 0;
        for(Item it : studentActor.getInventory()) {
            System.out.println(j++ + "\t" + it.getName());
        }
    }
    public static void pairTransByInput() {
        useItemInput();
        useItemInput();
    }
    public static void listDoorsOfRoom(Room r) {
        System.out.println("Szomszedos szobak:");
        int id = 0;
        for(Door d : r.getNeighbourDoors()) {
            System.out.println(id++ + "\t" + d.getNextRoom(r).getName());
        }
    }

    public static void idDoorInput() {
        boolean exit = false;
        int sorszam = -1;
        while(!exit) {
            String line = scan.nextLine();

            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < studentActor.getPosition().getNeighbourDoors().size(); i++) {
                    if(i == sorszam) {
                        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }







    public static void targyFelvetel() {
        buildOneRoomMapAndPlayer();
        Transistor t1 = new Transistor("Transistor1");
        addItemToStudentRoom(t1);
        System.out.println("-----------------------------------");
        listItemsInStudentsRoom();
        int size = getStudentsRoomItemsSize();
        PickUpInput(size);
        studentActor.getInventory().clear();
    }

    public static void logarlecFelvetel() {
        buildOneRoomMapAndPlayer();
        Logarlec l = new Logarlec("Logarlec1");
        addItemToStudentRoom(l);
        System.out.println("-----------------------------------");
        listItemsInStudentsRoom();
        int size = getStudentsRoomItemsSize();
        PickUpInput(size);
        studentActor.getInventory().clear();
    }

    public static void targyEldobas() {
        buildOneRoomMapAndPlayer();
        Transistor t = new Transistor("t");
        studentActor.addItemToInventory(t);
        System.out.println("-----------------------------------");
        listItemsInStudentsInventory();
        dropIteminput();
        studentActor.getInventory().clear();
    }

    public static void lepes() {
        buildTwoRoomMapAndPlayer();
        System.out.println("-----------------------------------");
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        listDoorsOfRoom(studentActor.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");
        idDoorInput();
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
    }

    public static void lepesOktatohoz() {
        buildTwoRoomMapAndPlayerAndProf();
        System.out.println("-----------------------------------");
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Elet: " + studentActor.getHealth());
        listDoorsOfRoom(studentActor.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");
        idDoorInput();
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Elet: " + studentActor.getHealth());
    }

    public static void pairTransistorsByUser() {
        buildOneRoomMapAndPlayer();
        Transistor t1 = new Transistor("Transistor1");
        Transistor t2 = new Transistor("Transistor2");
        studentActor.addItemToInventory(t1);
        studentActor.addItemToInventory(t2);
        System.out.println("-----------------------------------");
        listItemsInStudentsInventory();
        do {
            pairTransByInput();
            if(t1.getPair() != t2) {
                System.out.println("Sikertelen parositas, ugyanazt a tranzisztort nem lehet!");
            }
        } while(t1.getPair() != t2);
        if (t1.getPair() == t2) {
            System.out.println("Sikeres parositas!");
        }

        studentActor.getInventory().clear();
    }

    public static void pairAndTeleportByTransistorsByUser() {
        buildTwoRoomMapAndPlayer();
        Transistor t1 = new Transistor("Transistor1");
        Transistor t2 = new Transistor("Transistor2");
        studentActor.addItemToInventory(t1);
        studentActor.addItemToInventory(t2);
        System.out.println("-----------------------------------");
        System.out.println("Parositja a tranzisztorokat!");
        listItemsInStudentsInventory();
        do {
            pairTransByInput();
            if(t1.getPair() == null) {
                System.out.println("Sikertelen parositas!");
            }
        } while(t1.getPair() != t2);
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Dobjon el egy Tranzisztort!");
        listItemsInStudentsInventory();
        dropIteminput();
        listDoorsOfRoom(studentActor.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");
        idDoorInput();
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Dobjon el egy Tranzisztort!");
        listItemsInStudentsInventory();
        dropIteminput();
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        studentActor.getInventory().clear();
    }

    public static void mergeRooms() {
        buildTwoWithItemsRoomNoCharacter();
        System.out.println("-----------------------------------");
        System.out.println("Szobak:");
        for(Room r : map) {
            System.out.println("\t" + r.getName());
            System.out.println("\t\t" + "Kapacitas: " + r.getCapacity());
            System.out.println("\t\t" + "Gaz: " + r.getIsGassed());
            System.out.println("\t\t" + "Targyak:");
            for(Item i : r.getItems()) {
                System.out.println("\t\t\t" + i.getName());
            }
        }

        map.get(0).mergeRooms(map.get(1));
        map.remove(1);
        System.out.println("Szobak az osszeolvasztas utan: (a szobat szukseges meg kivenni a palyabol, ezt megtettuk)");
        for(Room r : map) {
            if(r != null) {
                System.out.println("\t" + r.getName());
                System.out.println("\t\t" + "Kapacitas: " + r.getCapacity());
                System.out.println("\t\t" + "Gaz: " + r.getIsGassed());
                System.out.println("\t\t" + "Targyak:");
                for (Item i : r.getItems()) {
                    System.out.println("\t\t\t" + i.getName());
                }
            }
        }
    }

    public static void divideRooms() {
        buildOneWithItemsRoomNoCharacter();
        System.out.println("-----------------------------------");
        System.out.println("Szobak:");
        for(Room r : map) {
            System.out.println("\t" + r.getName());
            System.out.println("\t\t" + "Kapacitas: " + r.getCapacity());
            System.out.println("\t\t" + "Gaz: " + r.getIsGassed());
            System.out.println("\t\t" + "Targyak:");
            for(Item i : r.getItems()) {
                System.out.println("\t\t\t" + i.getName());
            }
        }

        map.get(0).roomDivision();
        List<Door> neighbDoors = map.get(0).getNeighbourDoors();
        Room nRoom = neighbDoors.get(neighbDoors.size()-1).getNextRoom(map.get(0));
        map.add(nRoom);

        for(Room r : map) {
            System.out.println("\t" + r.getName());
            System.out.println("\t\t" + "Kapacitas: " + r.getCapacity());
            System.out.println("\t\t" + "Gaz: " + r.getIsGassed());
            System.out.println("\t\t" + "Targyak:");
            for(Item i : r.getItems()) {
                System.out.println("\t\t\t" + i.getName());
            }
        }
    }

    public static void kabulas() {
        buildTwoRoomMapAndPlayerSecondGas();
        Transistor t1 = new Transistor("t1");
        Transistor t2 = new Transistor("t2");
        studentActor.addItemToInventory(t1);
        studentActor.addItemToInventory(t2);
        System.out.println("-----------------------------------");
        System.out.println("Kabulas: " + studentActor.getNotConscious());
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Eszkoztar:");
        for(Item i : studentActor.getInventory()) {
            System.out.println("\t" + i.getName());
        }
        System.out.println("Szobaban levo targyak:");
        for(Item i : studentActor.getPosition().getItems()) {
            System.out.println("\t" + i.getName());
        }
        listDoorsOfRoom(studentActor.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");
        idDoorInput();
        System.out.println("Kabulas: " + studentActor.getNotConscious());
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Eszkoztar:");
        for(Item i : studentActor.getInventory()) {
            System.out.println("\t" + i.getName());
        }
        System.out.println("Szobaban levo targyak:");
        for(Item i : studentActor.getPosition().getItems()) {
            System.out.println("\t" + i.getName());
        }
    }




    public static void camembertUse() {
        buildOneRoomMapAndPlayer();
        Camembert camembert = new Camembert("Camembert");
        studentActor.addItemToInventory(camembert);
        System.out.println("-----------------------------------");
        studentActor.setStartPosition(map.get(0));
        System.out.println("Eszkoztar:");
        for(Item i : studentActor.getInventory()) {
            System.out.println("\t" + i.getName());
        }

        studentActor.useItem(studentActor.getInventory().get(0));
        System.out.println("Elet: " + studentActor.getHealth());
        System.out.println("Kabulas: " + studentActor.getNotConscious());
        System.out.println("Eszkoztar:");
        for(Item i : studentActor.getInventory()) {
            System.out.println("\t" + i.getName());
        }

        System.out.println(studentActor.getPosition().getIsGassed());

        studentActor.getInventory().clear();
    }

    public static void cleanerTry() {
        buildTwoRoomMapAndPlayer();
        System.out.println("-----------------------------------");
        System.out.println(map.get(0).getName() + " ------- " + map.get(1).getName());
        Cleaner cleaner = new Cleaner("Cleaner");
        cleaner.setStartPosition(map.get(1));
        studentActor.setStartPosition(map.get(0));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println(studentActor.getPosition().getAfterCleanCount());
        cleaner.move(map.get(1).getNeighbourDoors().get(0));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        studentActor.move(map.get(1).getNeighbourDoors().get(0));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println(studentActor.getPosition().getAfterCleanCount());
    }

    public static void TVSZTry() {
        buildTwoRoomMapAndPlayerAndProf();
        TVSZ tvsz = new TVSZ("tvsz");
        map.get(0).addItem(tvsz);
        studentActor.pickUp(tvsz);
        System.out.println("-----------------------------------");
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Elet: " + studentActor.getHealth());
        professorActor.setStartPosition(map.get(1));
        studentActor.setStartPosition(map.get(0));
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Elet: " + studentActor.getHealth());
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Elet: " + studentActor.getHealth());
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Elet: " + studentActor.getHealth());
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Elet: " + studentActor.getHealth());
    }

    public static void FFP2Try() {
        buildTwoRoomMapAndPlayerSecondGas();
        FFP2 mask = new FFP2("FFP2");
        Transistor t1 = new Transistor("t1");
        map.get(0).addItem(mask);
        map.get(0).addItem(t1);
        System.out.println("-----------------------------------");
        studentActor.setStartPosition(map.get(0));
        studentActor.pickUp(mask);
        studentActor.pickUp(t1);
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
    }

    public static void boardCleanerTry() {
        buildTwoRoomMapAndPlayerAndProf();
        BoardCleaner bc = new BoardCleaner("BoardCleaner");
        map.get(0).addItem(bc);
        System.out.println("-----------------------------------");
        professorActor.setStartPosition(map.get(1));
        studentActor.setStartPosition(map.get(0));

        studentActor.pickUp(bc);

        studentActor.move(map.get(0).getNeighbourDoors().get(0));
        System.out.println("Elet: " + studentActor.getHealth());
        System.out.println("Eszkoztar:");
        listItemsInStudentsInventory();
        System.out.println("Prof conscious: " + professorActor.getNotConscious());
        studentActor.useItem(studentActor.getInventory().get(0));
        studentActor.move(map.get(0).getNeighbourDoors().get(0));
        studentActor.move(map.get(0).getNeighbourDoors().get(0));
        System.out.println("Elet: " + studentActor.getHealth());
        System.out.println("Eszkoztar:");
        listItemsInStudentsInventory();
        System.out.println("Prof conscious: " + professorActor.getNotConscious());
    }

    public static void holyPintTry() {
        buildTwoRoomMapAndPlayerAndProf();
        HolyPint hp = new HolyPint("HolyPint");
        FFP2 mask = new FFP2("FFP2");
        map.get(0).addItem(hp);
        map.get(0).addItem(mask);
        System.out.println("-----------------------------------");
        professorActor.setStartPosition(map.get(1));
        studentActor.setStartPosition(map.get(0));

        studentActor.pickUp(hp);
        studentActor.pickUp(mask);
        System.out.println("Elet: " + studentActor.getHealth());
        System.out.println("Eszkoztar:");
        listItemsInStudentsInventory();
        studentActor.useItem(studentActor.getInventory().get(0));
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        System.out.println("Elet: " + studentActor.getHealth());
        System.out.println("Eszkoztar:");
        listItemsInStudentsInventory();
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        studentActor.dropItem(studentActor.getInventory().get(0));
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(0));
        System.out.println("Elet: " + studentActor.getHealth());
        System.out.println("Eszkoztar:");
        listItemsInStudentsInventory();
    }

    public static void fakeItemsTry() {
        buildOneRoomMapAndPlayer();
        FakeLogarlec fakelogar = new FakeLogarlec("FakeLogar");
        FakeFFP2 fakeff = new FakeFFP2("FakeFFP2");
        FakeTVSZ faketvsz = new FakeTVSZ("FakeTVSZ");
        FakeTVSZ faketvsz2 = new FakeTVSZ("FakeTVSZ");
        FakeTVSZ faketvsz3 = new FakeTVSZ("FakeTVSZ");
        FakeTVSZ faketvsz4 = new FakeTVSZ("FakeTVSZ");
        FakeTVSZ faketvsz5 = new FakeTVSZ("FakeTVSZ");
        System.out.println("-----------------------------------");
        map.get(0).addItem(fakelogar);
        map.get(0).addItem(fakeff);
        map.get(0).addItem(faketvsz);

        studentActor.setStartPosition(map.get(0));
        studentActor.pickUp(fakelogar);
        studentActor.pickUp(fakeff);
        studentActor.pickUp(faketvsz);
        System.out.println("Eszkoztar:");
        listItemsInStudentsInventory();
    }

    public static void main(String[] args) {
        boolean exit = false;
        //camembertUse();
        //cleanerTry();
        //TVSZTry();
        //FFP2Try();
        //boardCleanerTry();
        //holyPintTry();
        //fakeItemsTry();

        while(!exit) {
            System.out.println("----------------------------------------");
            System.out.println("0. Exit");
            System.out.println("Szekvenciak:");
            System.out.println("1. Targy (tranzisztor) felvetel");
            System.out.println("2. Targy (logarlec) felvetel");
            System.out.println("3. Targy (tranzisztor) eldobas");
            System.out.println("4. Lepes felhasznalo altal");
            System.out.println("5. Lepes felhasznalo altal, professzor a szobaban");
            System.out.println("6. Tranzisztor parositas");
            System.out.println("7. Tranzisztor parositas es  hasznalat");
            System.out.println("8. Szoba osszeolvasztas");
            System.out.println("9. Szoba osztodas");
            System.out.println("10. Kabulas");
            System.out.println("----------------------------------------");
            String line = scan.nextLine();

            if(line.equals("0")) {
                exit = true;
            } else if(line.equals("1")) {
                targyFelvetel();
            } else if(line.equals("2")) {
                logarlecFelvetel();
            } else if(line.equals("3")) {
                targyEldobas();
            } else if(line.equals("4")) {
                lepes();
            } else if(line.equals("5")) {
                lepesOktatohoz();
            } else if(line.equals("6")) {
                pairTransistorsByUser();
            } else if(line.equals("7")) {
                pairAndTeleportByTransistorsByUser();
            } else if(line.equals("8")) {
                mergeRooms();
            } else if(line.equals("9")) {
                divideRooms();
            } else if(line.equals("10")) {
                kabulas();
            }
        }
    }
}
