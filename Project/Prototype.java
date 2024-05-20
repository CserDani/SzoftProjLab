import java.util.*;

import Characters.Cleaner;
import Characters.Person;
import Characters.Professor;
import Characters.Student;
import GUIAndMVC.Game;
import Items.Item;
import RoomAndDoor.Door;
import RoomAndDoor.Room;
import TestAndLoad.Tests;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class Prototype {
    private static final Scanner scan = new Scanner(System.in);
    private static Game game = new Game();
    private static Person pickedPerson = null;

    public static void PickUpInput(int size) {
        if(pickedPerson == null) {
            return;
        }

        System.out.println("Melyik targyat szeretne felvenni? (Szobaban levo targyak, 1.oszlop index alapjan)");
        Room actualRoom = pickedPerson.getPosition();
        List<Item> roomItems = actualRoom.getItems();
        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < size; i++) {
                    if(i == sorszam) {
                        Item item = roomItems.get(i);
                        pickedPerson.pickUp(item);
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
        for(Item it : roomItems) {
            System.out.println("\t" + it.getName());
        }
    }
    public static void targyFelvetel() {
        if(pickedPerson == null) {
            return;
        }
        System.out.println("-----------------------------------");
        listItemsInPersonRoom();
        int size = getPersonRoomItemsSize();
        if(size <= 0) {
            System.out.println("Nincs targy a szobaban!");
            return;
        }
        PickUpInput(size);
    }

    public static void useItemInput() {
        if(pickedPerson.getInventory().isEmpty() || pickedPerson == null)
            return;

        System.out.println("Melyik targyat szeretne hasznalni (Characters.Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            List<Item> inventory = pickedPerson.getInventory();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < inventory.size(); i++) {
                    if(i == sorszam) {
                        Item item = inventory.get(i);
                        pickedPerson.useItem(item);
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
        listItemsInPersonInventory();
        useItemInput();
    }

    public static void dropIteminput() {
        if(pickedPerson.getInventory().isEmpty() || pickedPerson == null)
            return;

        System.out.println("Melyik targyat szeretne eldobni? (Characters.Student-nel levo targyak, 1.oszlop index alapjan)");
        List<Item> inventory = pickedPerson.getInventory();
        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < inventory.size(); i++) {
                    if(i == sorszam) {
                        Item item = inventory.get(i);
                        pickedPerson.dropItem(item);
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
        listItemsInPersonInventory();
        dropIteminput();
    }

    public static void idDoorInput(Person p) {
        if(pickedPerson == null) {
            return;
        }

        boolean exit = false;
        int sorszam = -1;
        while(!exit) {
            String line = scan.nextLine();
            Room actualRoom = p.getPosition();
            List<Door> neighbours = actualRoom.getNeighbourDoors();
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < neighbours.size(); i++) {
                    if(i == sorszam) {
                        Door door = neighbours.get(i);
                        Room nextRoom = door.getNextRoom(actualRoom);
                        if(!nextRoom.isNotFull()) {
                            System.out.println("A szoba megtelt!");
                        } else {
                            p.move(door);
                        }
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }

    public static void idDoorInputWhileNotMoved(Person p) {
        if(pickedPerson == null) {
            return;
        }

        System.out.println("Valasszon egy ajtot " + p.getName() + "-nek!");
        listDoorsOfRoom(p.getPosition());
        boolean exit = false;
        int sorszam = -1;
        while(!exit) {
            String line = scan.nextLine();
            Room actualRoom = p.getPosition();
            List<Door> neighbours = actualRoom.getNeighbourDoors();
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < neighbours.size(); i++) {
                    if(i == sorszam) {
                        Door door = neighbours.get(i);
                        Room nextRoom = door.getNextRoom(actualRoom);
                        if(!nextRoom.isNotFull()) {
                            System.out.println("A szoba megtelt!");
                        } else {
                            if(door.canMove(actualRoom)) {
                                p.move(door);
                                exit = true;
                            }
                        }
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
        idDoorInput(pickedPerson);
        System.out.println("Jelenlegi hely: " + pickedPerson.getPosition().getName());
    }

    public static void listDoorsOfRoom(Room r) {
        System.out.println("Szomszedos szobak:");
        int id = 0;
        for(Door d : r.getNeighbourDoors()) {
            Room nextRoom = d.getNextRoom(r);
            System.out.println(id++ + "\t" + nextRoom.getName());
        }
    }
    public static void listItemsInPersonInventory() {
        if(pickedPerson == null) {
            return;
        }

        int j = 0;
        for(Item it : pickedPerson.getInventory()) {
            System.out.println(j++ + "\t" + it.getName());
        }
    }
    public static void listItemsInPersonRoom() {
        if(pickedPerson == null) {
            return;
        }

        int j = 0;
        List<Room> map = game.getMap();
        Room personRoom = pickedPerson.getPosition();
        for(Room r : map) {
            if(r.getName().equals(personRoom.getName())) {
                for(Item it : r.getItems()) {
                    System.out.println(j++ + "\t" + it.getName());
                }
            }
        }
    }
    public static int getPersonRoomItemsSize() {
        if(pickedPerson == null) {
            return -1;
        }

        List<Room> map = game.getMap();
        Room personRoom = pickedPerson.getPosition();
        for(Room r : map) {
            if(r.getName().equals(personRoom.getName())) {
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
            List<Room> map = game.getMap();
            for(int i = 0; i < map.size(); i++) {
                if(i == sorszam) {
                    Room r = map.get(i);
                    if(r.getPersons().isEmpty()) {
                        room = r;
                    }
                }
            }
        } catch (Exception e) {}

        return room;
    }

    public static void listRooms() {
        List<Room> map = game.getMap();
        for(int i = 0; i < map.size(); i++) {
            Room room = map.get(i);
            String str = room.getPersons().isEmpty() ? "Lehet" : "Nem lehet";
            System.out.println(i + ". " + room.getName() + " " + str);
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
                boolean mergeResult = r1.mergeRooms(r2);
                if(mergeResult) {
                    game.getMap().remove(r2);
                }
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

    public static void cleanerRandomOffMove() {
        if(pickedPerson == null) {
            return;
        }

        System.out.println("-----------------------------------");
        System.out.println("Jelenlegi hely: " + pickedPerson.getPosition().getName());
        listDoorsOfRoom(pickedPerson.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");

        boolean exit = false;
        int sorszam = -1;
        while(!exit) {
            String line = scan.nextLine();
            Room actualRoom = pickedPerson.getPosition();
            List<Person> actualRoomPersons = actualRoom.getPersons();
            List<Door> neighbours = actualRoom.getNeighbourDoors();
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < neighbours.size(); i++) {
                    if(i == sorszam) {
                        Door door = neighbours.get(i);
                        Room nextRoom = door.getNextRoom(actualRoom);
                        List<Person> personList = new ArrayList<>(nextRoom.getPersons());
                        if(!nextRoom.isNotFull()) {
                            System.out.println("A szoba megtelt!");
                        } else {
                            if (door.canMove(actualRoom)) {
                                actualRoomPersons.remove(pickedPerson);

                                if (nextRoom.getIsGassed()) {
                                    for (Person p : personList) {
                                        if (!p.getNotConscious()) {
                                            idDoorInputWhileNotMoved(p);
                                        }
                                    }

                                    nextRoom.setGas();
                                }

                                pickedPerson.setPosition(nextRoom);
                                nextRoom.getPersons().add(pickedPerson);

                                if (nextRoom.getCleaned() && nextRoom.getAfterCleanCount() > 0) {
                                    nextRoom.decrAfterCleanCount();
                                }

                                if (!nextRoom.getCleaned()) {
                                    nextRoom.setCleaned();
                                }
                            }
                        }

                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }

    public static void divideRoomWithoutRandom() {
        int divideable = 0;
        List<Room> map = game.getMap();
        for(Room r : map) {
            if(r.getPersons().isEmpty()) {
                divideable++;
            }
        }

        if(divideable > 0) {
            Room r = null;
            listRooms();
            while(r == null) {
                r = roomInput();
            }
            String line;
            boolean gassed = false;
            boolean cursed = false;
            int newCap = 0;
            if (r.getIsGassed()) {
                System.out.println("Az eredeti szoba gazos, az uj is legyen? (true/false)");
                boolean gasInputEnded = false;
                while(!gasInputEnded) {
                    try {
                        line = scan.nextLine();
                        gassed = Boolean.parseBoolean(line);
                        gasInputEnded = true;
                    } catch (Exception e) {}
                }
            }
            if(r.getIsCursed()) {
                System.out.println("Az eredeti szoba atkozott, az uj is legyen? (true/false)");
                boolean curseInputEnded = false;
                while(!curseInputEnded) {
                    try {
                        line = scan.nextLine();
                        cursed = Boolean.parseBoolean(line);
                        curseInputEnded = true;
                    } catch (Exception e) {}
                }
            }

            System.out.println("Mekkora kapacitasa legyen az uj szobanak? Max: " + (r.getCapacity() - 1));
            boolean capInputEnded = false;
            while(!capInputEnded) {
                try {
                    line = scan.nextLine();
                    newCap = Integer.parseInt(line);
                    if(newCap < r.getCapacity() && newCap > 0) {
                        capInputEnded = true;
                    }
                } catch (Exception e) {}
            }
            String name = "New RoomAndDoor.Room" + game.getMap().size();
            Room newRoom = new Room(name, gassed, cursed, newCap);
            Door newDoor = new Door(r, newRoom, false);
            listDoorsOfRoom(r);
            System.out.println("Melyik ajtok tartozzanak az uj szobahoz? ('-' a kilepeshez)");
            boolean doorInputEnded = false;
            int doorIdx;
            List<Door> originalDoors = r.getNeighbourDoors();
            while(!doorInputEnded) {
                try {
                    line = scan.nextLine();
                    if(line.equals("-")) {
                        doorInputEnded = true;
                    } else {
                        doorIdx = Integer.parseInt(line);
                        Door door = originalDoors.get(doorIdx);
                        originalDoors.remove(door);
                        door.setRoom(r, newRoom);
                        newRoom.getNeighbourDoors().add(door);
                    }
                } catch (Exception e) {}
            }
            r.getNeighbourDoors().add(newDoor);
            int originalCap = r.getCapacity();
            r.setCapacity(originalCap - newCap);
            newRoom.getNeighbourDoors().add(newDoor);
            map.add(newRoom);
        } else {
            System.out.println("Nincs szoba amit szetosztani lehetne!");
        }
    }

    public static void main(String[] args) {
        boolean randomOn = true;
        while (true) {
            System.out.println("-----------------------------------");
            if(game.getStudents().isEmpty() && game.getProfessors().isEmpty() && game.getCleaners().isEmpty()) {
                System.out.println("Nincs karakter!");
            } else {
                System.out.println("Hallgatok szama: " + game.getStudents().size());
                System.out.println("Oktatok szama: " + game.getProfessors().size());
                System.out.println("Takaritok szama: " + game.getCleaners().size());
                String kivJatekos = pickedPerson != null ? pickedPerson.getName() : "Nincs kivalasztott";
                System.out.println("Kivalasztott karakter: " + kivJatekos);
            }

            System.out.println("Random kapcsolasa: " + randomOn);

            System.out.println("-----------------------------------");

            System.out.println("0. Exit");
            System.out.println("Szekvenciak:");
            System.out.println("1. Targy felvetele");
            System.out.println("2. Targy hasznalata");
            System.out.println("3. Targy eldobasa");
            System.out.println("4. Karakter leptetese");
            System.out.println("5. Palya betoltese");
            System.out.println("6. A jatek allapotanak kiiratasa");
            System.out.println("7. Hallgato valasztasa");
            System.out.println("8. Oktato valasztasa");
            System.out.println("9. Takarito valasztasa");
            System.out.println("10. Szoba egyesules");
            System.out.println("11. Szoba osztodas");
            System.out.println("12. Random kapcsolasa");
            System.out.println("13. Tesztek futtatasa");

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
                        if(randomOn || !game.getCleaners().contains(pickedPerson)) {
                            lepes();
                        } else {
                            cleanerRandomOffMove();
                        }
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
                        if(randomOn) {
                            divideRoom();
                        } else {
                            divideRoomWithoutRandom();
                        }
                        break;
                    case 12:
                        randomOn = !randomOn;
                        break;
                    case 13:
                        Result result = JUnitCore.runClasses(Tests.class);

                        for (Failure failure : result.getFailures()) {
                            System.out.println(failure.toString());
                        }

                        if (result.wasSuccessful()) {
                            System.out.println("All tests passed successfully!");
                        } else {
                            System.out.println("Test.Tests failed.");
                        }

                        System.out.println("To check the tests, check Test.Tests class in the project!");
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
        List<Room> map = game.getMap();
        List<Student> students = game.getStudents();
        List<Professor> professors = game.getProfessors();
        List<Cleaner> cleaners = game.getCleaners();
        for(int i = 0; i < map.size(); i++){
            Room room = map.get(i);
            System.out.println("\tSzoba neve: " + room.getName() + ", Szoba index: " + i
                    + ", Szoba kapacitas: " + room.getCapacity()
                    + ", Szoba gazos: " + room.getIsGassed()
                    + ", Szoba atkozott: " + room.getIsCursed()
                    + ", Szoba kitakaritott: " + room.getCleaned()
                    + ", Szoba letszama: " + room.getPersons().size());
            System.out.println("\t\tSzomszedai: ");
            for(Door door : room.getNeighbourDoors()){
                Room nextRoom = door.getNextRoom(room);
                System.out.println("\t\t\t" + nextRoom.getName());
            }
            System.out.println("\t\tTargyai: ");
            for(Item item : room.getItems()){
                System.out.println("\t\t\t" + item.getName());
            }
        }
        System.out.println("Karakterek:");
        System.out.println("\tHallgatok:");
        for(Student student : students){
            System.out.println("\t\tNeve: " + student.getName() + ", helye: " + student.getPosition().getName()
                    + ", eletereje: " + student.getHealth() + ", kabult: " + student.getNotConscious());
        }
        System.out.println("\tProfesszorok:");
        for (Professor professor : professors) {
            System.out.println("\t\tNeve: " + professor.getName() + ", helye: " + professor.getPosition().getName()
                    + ", kabult: " + professor.getNotConscious());
        }
        System.out.println("\tTakaritok:");
        for (Cleaner cleaner : cleaners) {
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