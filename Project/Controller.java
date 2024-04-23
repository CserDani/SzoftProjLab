import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Controller() {
    }

    private List<Room> map = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Professor> professors = new ArrayList<>();
    private List<Cleaner> cleaners = new ArrayList<>();


    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {

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