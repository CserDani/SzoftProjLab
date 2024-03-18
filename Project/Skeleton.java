import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Skeleton {
    private static final Scanner scan = new Scanner(System.in);
    private static final List<Room> map = new ArrayList<>();
    private static Student studentActor;
    private static Professor professorActor;

    public static void buildOneRoomMapAndPlayer() {
        map.clear();
        studentActor = null;
        professorActor = null;
        //A Room nem gázosított és nem elátkozott, 1 kapacitású
        Room r = new Room("Szoba1", false, false, 1);
        map.add(r);

        studentActor = new Student("Hallgato");
        studentActor.setPosition(r);
    }

    public static void buildTwoRoomMapAndPlayer() {
        map.clear();
        studentActor = null;
        professorActor = null;

        Room r1 = new Room("Szoba1", false, false, 1);
        Room r2 = new Room("Szoba2", false, false, 1);
        r1.addNeighbour(r2, false);

        studentActor = new Student("Hallgato");
        studentActor.setPosition(r1);
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
    public static int idInput() {
        boolean exit = false;
        int sorszam = -1;
        while(!exit) {
            String line = scan.nextLine();
            
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
        return sorszam;
    }







    public static void targyFelvetel() {
        buildOneRoomMapAndPlayer();
        Transistor t1 = new Transistor("Transistor1");
        addItemToStudentRoom(t1);
        System.out.println("-----------------------------------");
        listItemsInStudentsRoom();
        int size = getStudentsRoomItemsSize();
        PickUpInput(size);
    }

    public static void logarlecFelvetel() {
        buildOneRoomMapAndPlayer();
        Logarlec l = new Logarlec("Logarlec1");
        addItemToStudentRoom(l);
        System.out.println("-----------------------------------");
        listItemsInStudentsRoom();
        int size = getStudentsRoomItemsSize();
        PickUpInput(size);
    }

    public static void targyEldobas() {
        buildOneRoomMapAndPlayer();
        Active a = new Active("Active1");
        studentActor.addItemToInventory(a);
        System.out.println("-----------------------------------");
        listItemsInStudentsInventory();
        dropIteminput();
    }

    public static void pairTransistorsByUser() {
        buildOneRoomMapAndPlayer();
        Transistor t1 = new Transistor("Transistor1");
        Transistor t2 = new Transistor("Transistor2");
        studentActor.addItemToInventory(t1);
        studentActor.addItemToInventory(t2);
        System.out.println("-----------------------------------");
        listItemsInStudentsInventory();
        pairTransByInput();
        try {
            if (t1.getPair().getName().equals(t2.getName())) {
                System.out.println("Sikeres parositas!");
            }
        } catch (Exception e) {
            System.out.println("Sikertelen parositas! Ugyanazt a tranzisztort nem lehet!");
        }
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
        pairTransByInput();
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Dobjon el egy Tranzisztort!");
        listItemsInStudentsInventory();
        dropIteminput();
        listDoorsOfRoom(studentActor.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");
        studentActor.move(studentActor.getPosition().getNeighbourDoors().get(idInput()));
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
        System.out.println("Dobjon el egy Tranzisztort!");
        listItemsInStudentsInventory();
        dropIteminput();
        System.out.println("Jelenlegi hely: " + studentActor.getPosition().getName());
    }

    public static void main(String[] args) {
        boolean exit = false;

        while(!exit) {
            System.out.println("-----------------------------------");
            System.out.println("0. Exit");
            System.out.println("Szekvenciak:");
            System.out.println("1. Targy (tranzisztor) felvetel");
            System.out.println("2. Targy (logarlec) felvetel");
            System.out.println("3. Targy (aktiv) eldobas");
            System.out.println("4. Tranzisztor parositas");
            System.out.println("5. Tranzisztor parositas es  hasznalat");
            System.out.println("-----------------------------------");
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
                pairTransistorsByUser();
            } else if(line.equals("5")) {
                pairAndTeleportByTransistorsByUser();
            }
        }
    }
}
