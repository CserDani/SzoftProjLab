import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    public Controller() {}

    public Controller(Controller c) {
        map = c.getMap();
        students = c.getStudents();
        professors = c.getProfessors();
        cleaners = c.getCleaners();
    }
    private static final Scanner scan = new Scanner(System.in);
    private static List<Room> map = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Professor> professors = new ArrayList<>();
    private static List<Cleaner> cleaners = new ArrayList<>();
    private static Student pickedStudent = null;

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
                        pickedStudent.pickUp(pickedStudent.getPosition().getItems().get(sorszam));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : pickedStudent.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : pickedStudent.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }
    public static void targyFelvetel() {
        if(pickedStudent == null) {
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
        System.out.println("Melyik targyat szeretne hasznalni (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < pickedStudent.getInventory().size(); i++) {
                    if(i == sorszam) {
                        pickedStudent.useItem(pickedStudent.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }
    public static void targyHasznalat() {
        if(pickedStudent == null) {
            return;
        }
        System.out.println("-----------------------------------");
        listItemsInStudentsInventory();
        useItemInput();
    }

    public static void dropIteminput() {
        System.out.println("Melyik targyat szeretne eldobni? (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < pickedStudent.getInventory().size(); i++) {
                    if(i == sorszam) {
                        pickedStudent.dropItem(pickedStudent.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : pickedStudent.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : pickedStudent.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }
    public static void targyEldobas() {
        if(pickedStudent == null) {
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

            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < pickedStudent.getPosition().getNeighbourDoors().size(); i++) {
                    if(i == sorszam) {
                        pickedStudent.move(pickedStudent.getPosition().getNeighbourDoors().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }
    public static void lepes() {
        if(pickedStudent == null) {
            return;
        }
        System.out.println("-----------------------------------");
        System.out.println("Jelenlegi hely: " + pickedStudent.getPosition().getName());
        listDoorsOfRoom(pickedStudent.getPosition());
        System.out.println("Valasszon egy ajto sorszamot!");
        idDoorInput();
        System.out.println("Jelenlegi hely: " + pickedStudent.getPosition().getName());
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
        for(Item it : pickedStudent.getInventory()) {
            System.out.println(j++ + "\t" + it.getName());
        }
    }
    public static void listItemsInStudentsRoom() {
        int j = 0;
        for(Room r : map) {
            if(r.getName().equals(pickedStudent.getPosition().getName())) {
                for(Item it : r.getItems()) {
                    System.out.println(j++ + "\t" + it.getName());
                }
            }
        }
    }
    public static int getStudentsRoomItemsSize() {
        for(Room r : map) {
            if(r.getName().equals(pickedStudent.getPosition().getName())) {
                return r.getItems().size();
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("----------------------------------------");
            if(students.size() == 0) {
                System.out.println("Nincs jatekos!");
            } else {
                System.out.println("Jatekosok szama: " + students.size());
                String kivJatekos = pickedStudent != null ? pickedStudent.getName() : "Nincs kivalasztott";
                System.out.println("Kivalasztott jatekos: " + kivJatekos);
            }
            System.out.println("----------------------------------------");

            System.out.println("0. Exit");
            System.out.println("Szekvenciak:");
            System.out.println("1. Targy felvetele");
            System.out.println("2. Targy hasznalata");
            System.out.println("3. Targy eldobasa");
            //System.out.println("4. Tranzisztor parositas"); // Kérdés: inventory-ban való ellenőrzés típusellenőrzés nélkül.
            System.out.println("5. Jatekos leptetese");
            System.out.println("6. Palya betoltese");
            System.out.println("7. Parancsok beolvasasa es azok kimeneteinek kiirasa egy fajlba");
            //System.out.println("8. Palyakeszites");
            System.out.println("8. Palyakeszites fajlbol");
            System.out.println("9. Szoba osztodas");
            System.out.println("10. Teszt: A jatek allapotanak kiiratasa");
            System.out.println("11. Jatekos valasztasa");
            System.out.println("X. Szoba egyesules");
            System.out.println("Y. Jatekos valtasa");

            System.out.println("----------------------------------------");
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
                        break;
                    case 5:
                        lepes();
                        break;
                    case 8:
                        System.out.println("Adja meg a fajl nevet: ");
                        String filename = scan.nextLine();
                        palyaBetoltes(filename);
                        break;
                    case 10:
                        jatekAllapot();
                        break;
                    case 11:
                        jatekosValasztas();
                        break;
                    default:
                        System.out.println("Ismeretlen parancs!\n");
                }
            } catch (Exception e) {
                System.out.println("Hibas sorszam valasztas!");
            }
        }
    }

    public static void palyaBetoltes(String filename){
        try{
            LoadGameState newMap = new LoadGameState(filename);
            map = newMap.getController().getMap();
            students = newMap.getController().getStudents();
            professors = newMap.getController().getProfessors();
            cleaners = newMap.getController().getCleaners();
            System.out.println("Sikeres palya es parameter beolvasas");
        } catch (IOException e){
            System.out.println("Sikertelen beolvasas: " + e.getMessage());
        }
    }

    public static void jatekAllapot(){
        System.out.println("Palya:\n");
        for(int i = 0; i < map.size(); i++){
            System.out.println("\tSzoba index: " + i + ", Szoba kapacitas: " + map.get(i).getCapacity() +"\n");
            System.out.println("\t\tSzomszedai: ");
            for(Door door : map.get(i).getNeighbourDoors()){
                System.out.println("\t\t\t" + door.getNextRoom(map.get(i)).getName());
            }
            System.out.println("\n");
            System.out.println("\t\tTargyai: ");
            for(Item item : map.get(i).getItems()){
                System.out.println("\t\t\t" + item.getName());
            }
            System.out.println("\n");
        }
        System.out.println("\t\tHallgatok:\n");
        for(Student student : students){
            System.out.println("\t\t\tNeve: " + student.getName() + ", helye: " + student.getPosition().getName() + ", eletereje: " + student.getHealth() + "\n");
        }
        System.out.println("\t\tProfesszorok:");
        for (Professor professor : professors) {
            System.out.println("\t\t\tNeve: " + professor.getName() + ", helye: " + professor.getPosition().getName() + "\n");
        }
    }

    public static void jatekosValasztas(){
        System.out.println("Jatekosok: ");
        for(int i = 0; i < students.size(); i++){
            System.out.println(i + ". " + students.get(i).getName() + "\n");
        }
        System.out.println("Jatekos sorszama: ");
        int choice = Integer.parseInt(scan.nextLine());
        if(choice >= 0 && choice < students.size()){
            pickedStudent = students.get(choice);
        } else {
            System.out.println("Ervenytelen valasztas!");
        }
    }
    public List<Room> getMap() {
        return map;
    }

    void setMap(List<Room> r) {
        map = r;
    }

    public List<Student> getStudents() {
        return students;
    }

    void setStudents(List<Student> s) {
        students = s;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    void setProfessors(List<Professor> p) {
        professors = p;
    }

    public List<Cleaner> getCleaners() {
        return cleaners;
    }

    void setCleaners(List<Cleaner> c) {
        cleaners = c;
    }
}