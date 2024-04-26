import java.util.*;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class Prototype {
    public Prototype() {}

    public Prototype(Prototype c) {
        this.game = c.game;
    }
    private static final Scanner scan = new Scanner(System.in);
    private static Game game = new Game();
    private static Person pickedPerson = null;

    private static Tests tests = new Tests();

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
                        pickedPerson.pickUp(pickedPerson.getPosition().getItems().get(sorszam));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : pickedPerson.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : pickedPerson.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }
    public static void targyFelvetel() {
        if(pickedPerson == null) {
            return;
        }
        System.out.println("-----------------------------------");
        listItemsInStudentsRoom();
        int size = getStudentsRoomItemsSize();
        if(size == 0) {
            System.out.println("Nincs targy a szobaban!");
            return;
        }
        PickUpInput(size);
    }

    public static void useItemInput() {
        if(pickedPerson.getInventory().size() == 0)
            return;
        System.out.println("Melyik targyat szeretne hasznalni (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < pickedPerson.getInventory().size(); i++) {
                    if(i == sorszam) {
                        pickedPerson.useItem(pickedPerson.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }
    public static void targyHasznalat() {
        if(pickedPerson == null) {
            return;
        }
        System.out.println("-----------------------------------");
        listItemsInStudentsInventory();
        useItemInput();
    }

    public static void dropIteminput() {
        if(pickedPerson.getInventory().size() == 0)
            return;
        System.out.println("Melyik targyat szeretne eldobni? (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < pickedPerson.getInventory().size(); i++) {
                    if(i == sorszam) {
                        pickedPerson.dropItem(pickedPerson.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : pickedPerson.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : pickedPerson.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }
    public static void targyEldobas() {
        if(pickedPerson == null) {
            return;
        }
        System.out.println("-----------------------------------");
        listItemsInStudentsInventory();
        dropIteminput();
    }

    public static void idDoorInput() {
        boolean exit = false;
        int sorszam = -1;
        while(!exit) {
            String line = scan.nextLine();
            List<Door> neighbours = pickedPerson.getPosition().getNeighbourDoors();
            Room actualRoom = pickedPerson.getPosition();
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < neighbours.size(); i++) {
                    if(i == sorszam) {
                        Door door = neighbours.get(i);
                        if(!door.getNextRoom(actualRoom).isNotFull()) {
                            System.out.println("A szoba megtelt!");
                        } else {
                            pickedPerson.move(door);
                        }
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }
    public static void lepes() {
        if(pickedPerson == null) {
            return;
        }
        System.out.println("-----------------------------------");
        System.out.println("Jelenlegi hely: " + pickedPerson.getPosition().getName());
        listDoorsOfRoom(pickedPerson.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");
        idDoorInput();
        System.out.println("Jelenlegi hely: " + pickedPerson.getPosition().getName());
    }

    public static void listDoorsOfRoom(Room r) {
        System.out.println("Szomszedos szobak:");
        int id = 0;
        for(Door d : r.getNeighbourDoors()) {
            System.out.println(id++ + "\t" + d.getNextRoom(r).getName());
        }
    }
    public static void listItemsInStudentsInventory() {
        int j = 0;
        for(Item it : pickedPerson.getInventory()) {
            System.out.println(j++ + "\t" + it.getName());
        }
    }
    public static void listItemsInStudentsRoom() {
        int j = 0;
        for(Room r : game.getMap()) {
            if(r.getName().equals(pickedPerson.getPosition().getName())) {
                for(Item it : r.getItems()) {
                    System.out.println(j++ + "\t" + it.getName());
                }
            }
        }
    }
    public static int getStudentsRoomItemsSize() {
        for(Room r : game.getMap()) {
            if(r.getName().equals(pickedPerson.getPosition().getName())) {
                return r.getItems().size();
            }
        }
        return -1;
    }

    public static Room roomInput() {
        int sorszam = -1;
        String line = scan.nextLine();
        Room room = null;
        try {
            sorszam = Integer.parseInt(line);
            for(int i = 0; i < game.getMap().size(); i++) {
                if(i == sorszam) {
                    if(game.getMap().get(i).getPersons().isEmpty()) {
                        room = game.getMap().get(i);
                    }
                }
            }
        } catch (Exception e) {}

        return room;
    }

    public static void listRooms() {
        for(int i = 0; i < game.getMap().size(); i++) {
            String str = game.getMap().get(i).getPersons().isEmpty() ? "Lehet" : "Nem lehet";
            System.out.println(i + ". " + game.getMap().get(i).getName() + " " + str);
        }
    }

    public static void mergeRooms() {
        int mergeable = 0;
        for(Room r : game.getMap()) {
            if(r.getPersons().isEmpty()) {
                mergeable++;
            }
        }

        if(mergeable >= 2) {
            listRooms();
            Room r1 = null;
            Room r2 = null;
            while (r1 == null)
                r1 = roomInput();
            while (r2 == null)
                r2 = roomInput();
            if(r1 != r2) {
                r1.mergeRooms(r2);
                game.getMap().remove(r2);
            }
        } else {
            System.out.println("Nem lehet 2 szobat mergeolni, nincs 2 ures!");
        }
    }
    public static void divideRoom() {
        int divideable = 0;
        for(Room r : game.getMap()) {
            if(r.getPersons().isEmpty()) {
                divideable++;
            }
        }

        if(divideable > 0) {
            Room r = null;
            listRooms();
            while (r == null)
                r = roomInput();
            game.divideRoom(r);
        } else {
            System.out.println("Nincs szoba amit szetosztani lehetne!");
        }
    }

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("-----------------------------------");
            if(game.getStudents().isEmpty()) {
                System.out.println("Nincs jatekos!");
            } else {
                System.out.println("Jatekosok szama: " + game.getStudents().size());
                String kivJatekos = pickedPerson != null ? pickedPerson.getName() : "Nincs kivalasztott";
                System.out.println("Kivalasztott jatekos: " + kivJatekos);
            }
            System.out.println("-----------------------------------");

            System.out.println("0. Exit");
            System.out.println("Szekvenciak:");
            System.out.println("1. Targy felvetele");
            System.out.println("2. Targy hasznalata");
            System.out.println("3. Targy eldobasa");
            System.out.println("4. Jatekos leptetese");
            System.out.println("5. Palya betoltese");
            System.out.println("6. A jatek allapotanak kiiratasa");
            System.out.println("7. Jatekos valasztasa");
            System.out.println("8. Oktato valasztasa");
            System.out.println("9. Takarito valasztasa");
            System.out.println("10. Szoba egyesules");
            System.out.println("11. Szoba osztodas");
            System.out.println("12. Tesztek futtatasa");

            System.out.println("-----------------------------------");
            String line = scan.nextLine();
            try
            {
                int sorszam = Integer.parseInt(line);
                switch (sorszam) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        targyFelvetel();
                        break;
                    case 2:
                        targyHasznalat();
                        break;
                    case 3:
                        targyEldobas();
                        break;
                    case 4:
                        lepes();
                        break;
                    case 5:
                        System.out.println("Adja meg a fajl nevet: ");
                        String filename = scan.nextLine();
                        palyaBetoltes(filename);
                        break;
                    case 6:
                        jatekAllapot();
                        break;
                    case 7:
                        List<Person> studentToPerson = new ArrayList<>(game.getStudents());
                        karakterValasztas(studentToPerson);
                        break;
                    case 8:
                        List<Person> profToPerson = new ArrayList<>(game.getProfessors());
                        karakterValasztas(profToPerson);
                        break;
                    case 9:
                        List<Person> cleanerToPerson = new ArrayList<>(game.getCleaners());
                        karakterValasztas(cleanerToPerson);
                        break;
                    case 10:
                        mergeRooms();
                        break;
                    case 11:
                        divideRoom();
                        break;
                    case 12:
                        Result result = JUnitCore.runClasses(Tests.class);

                        for (Failure failure : result.getFailures()) {
                            System.out.println(failure.toString());
                        }

                        if (result.wasSuccessful()) {
                            System.out.println("All tests passed successfully!");
                        } else {
                            System.out.println("Tests failed.");
                        }
                        break;
                    default:
                        System.out.println("Ismeretlen parancs!\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Hibas sorszam valasztas!");
            }
        }
    }

    public static void palyaBetoltes(String filename){
        game.gameLoad(filename);
    }

    public static void jatekAllapot(){
        System.out.println("Palya:");
        for(int i = 0; i < game.getMap().size(); i++){
            System.out.println("\tSzoba neve: " + game.getMap().get(i).getName() + " Szoba index: " + i + ", Szoba kapacitas: " + game.getMap().get(i).getCapacity());
            System.out.println("\t\tSzomszedai: ");
            for(Door door : game.getMap().get(i).getNeighbourDoors()){
                System.out.println("\t\t\t" + door.getNextRoom(game.getMap().get(i)).getName());
            }
            System.out.println("\t\tTargyai: ");
            for(Item item : game.getMap().get(i).getItems()){
                System.out.println("\t\t\t" + item.getName());
            }
        }
        System.out.println("Karakterek:");
        System.out.println("\tHallgatok:");
        for(Student student : game.getStudents()){
            System.out.println("\t\tNeve: " + student.getName() + ", helye: " + student.getPosition().getName()
                    + ", eletereje: " + student.getHealth() + ", kabult: " + student.getNotConscious());
        }
        System.out.println("\tProfesszorok:");
        for (Professor professor : game.getProfessors()) {
            System.out.println("\t\tNeve: " + professor.getName() + ", helye: " + professor.getPosition().getName()
                    + ", kabult: " + professor.getNotConscious());
        }
        System.out.println("\tTakaritok:");
        for (Cleaner cleaner : game.getCleaners()) {
            System.out.println("\t\tNeve: " + cleaner.getName() + ", helye: " + cleaner.getPosition().getName());
        }
    }

    public static void karakterValasztas(List<Person> lista){
        System.out.println("Jatekosok: ");
        for(int i = 0; i < lista.size(); i++){
            System.out.println(i + ". " + lista.get(i).getName() + "\n");
        }
        System.out.println("Jatekos sorszama: ");
        int choice = Integer.parseInt(scan.nextLine());
        if(choice >= 0 && choice < lista.size()){
            pickedPerson = lista.get(choice);
        } else {
            System.out.println("Ervenytelen valasztas!");
        }
    }
}