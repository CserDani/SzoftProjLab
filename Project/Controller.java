import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {
    public Controller() {
    }
    private static final Scanner scan = new Scanner(System.in);
    private static List<Room> map = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Professor> professors = new ArrayList<>();
    private static List<Cleaner> cleaners = new ArrayList<>();
    private static Student pickedStudent;

    public static void PickUpInput(Student s) {
        System.out.println("Melyik targyat szeretne felvenni? (Szobaban levo targyak, 1.oszlop index alapjan)");
        int size = -1;
        for(Room r : map) {
            if(r.getName().equals(s.getPosition().getName())) {
                size = r.getItems().size();
            }
        }
        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < size; i++) {
                    if(i == sorszam) {
                        s.pickUp(s.getPosition().getItems().get(sorszam));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : s.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : s.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }

    public static void useItemInput(Student s) {
        System.out.println("Melyik targyat szeretne hasznalni (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < s.getInventory().size(); i++) {
                    if(i == sorszam) {
                        s.useItem(s.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }
    }

    public static void dropIteminput(Student s) {
        System.out.println("Melyik targyat szeretne eldobni? (Student-nel levo targyak, 1.oszlop index alapjan)");

        boolean exit = false;
        while(!exit) {
            String line = scan.nextLine();
            int sorszam;
            try {
                sorszam = Integer.parseInt(line);
                for(int i = 0; i < s.getInventory().size(); i++) {
                    if(i == sorszam) {
                        s.dropItem(s.getInventory().get(i));
                        exit = true;
                    }
                }
            } catch (Exception e) {}
        }

        System.out.println("Eszkoztar:");
        for(Item it : s.getInventory()) {
            System.out.println("\t" + it.getName());
        }

        System.out.println("Szobaban levo targyak:");
        for(Item it : s.getPosition().getItems()) {
            System.out.println("\t" + it.getName());
        }
    }

    public static void pairTransByInput(Student s) {
        useItemInput(s);
        useItemInput(s);
    }
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("----------------------------------------");
            System.out.println("Valassz jatekost:");
            String line = scan.nextLine();
            while(Integer.parseInt(line) < 0 || Integer.parseInt(line) > students.size()-1) {
                System.out.println("Hibas valasztas!");
                System.out.println("Valassz jatekost:");
                line = scan.nextLine();
            }
            pickedStudent = students.get(Integer.parseInt(line));
            System.out.println("----------------------------------------");
            System.out.println("0. Exit");
            System.out.println("Szekvenciak:");
            System.out.println("1. Targy felvetele");
            System.out.println("2. Aktiv targy hasznalata");
            System.out.println("3. Targy eldobasa");
            System.out.println("4. Tranzisztor parositas"); // Kérdés: inventory-ban való ellenőrzés típusellenőrzés nélkül.
            System.out.println("5. Jatekos leptetese");
            System.out.println("6. Palya betoltese");
            System.out.println("7. Parancsok beolvasasa es azok kimeneteinek kiirasa egy fajlba");
            System.out.println("8. Palyakeszites");
            System.out.println("9. Szoba osztodas");
            System.out.println("10. Szoba egyesules");
            System.out.println("----------------------------------------");
            line = scan.nextLine();

            switch (Integer.parseInt(line)) {
                case 1:
                    PickUpInput(pickedStudent);
                    break;
                case 2:
                    useItemInput(pickedStudent);
                    break;
                case 3:
                    dropIteminput(pickedStudent);
                    break;
                case 4:

            }
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