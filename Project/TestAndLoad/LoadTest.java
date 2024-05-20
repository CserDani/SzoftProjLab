package TestAndLoad;

import Characters.Cleaner;
import Characters.Professor;
import Characters.Student;
import GUIAndMVC.Game;
import Items.Item;
import RoomAndDoor.Door;
import RoomAndDoor.Room;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class LoadTest {
    private Game game;

    public LoadTest(String filename, Game g) throws IOException {
        this.game = g;
        readFile(filename);
    }

    public void readFile(String filename) throws IOException{
        Path path = FileSystems.getDefault().getPath("Resources/tests", filename);
        List<String> lines = Files.readAllLines(path);

        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i).trim();

            switch(line) {
                case "MOVE":
                    i = moveCharacter(lines, i+1);
                    break;
                case "PICKUP":
                    i = pickupItem(lines, i+1);
                    break;
                case "DROP":
                    i = dropItem(lines, i+1);
                    break;
                case "USE":
                    i = useItem(lines, i+1);
                    break;
                case "DIVIDE":
                    i = divideRoom(lines, i+1);
                    break;
                case "MERGE":
                    i = mergeRooms(lines, i+1);
                    break;
                default:
                    break;
            }
        }
    }

    public int moveCharacter(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("(.*)+\\d+ \\d+")) {
                    String[] ln = line.split(" ");
                    String person = ln[0];
                    int personId = Integer.parseInt(ln[1]);
                    int doorId = Integer.parseInt(ln[2]);
                    if (person.equals("STUDENT")) {
                        Student s = game.getStudents().get(personId);
                        Room sRoom = s.getPosition();
                        Door d = sRoom.getNeighbourDoors().get(doorId);

                        game.move(s, d);
                    } else if (person.equals("PROFESSOR")) {
                        Professor p = game.getProfessors().get(personId);
                        Room pRoom = p.getPosition();
                        Door d = pRoom.getNeighbourDoors().get(doorId);

                        game.move(p, d);
                    } else if (person.equals("CLEANER")) {
                        Cleaner c = game.getCleaners().get(personId);
                        Room cRoom = c.getPosition();
                        Door d = cRoom.getNeighbourDoors().get(doorId);

                        game.move(c, d);
                    }
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int pickupItem(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("(.*)+\\d+ \\d+")) {
                    String[] ln = line.split(" ");
                    String person = ln[0];
                    int personId = Integer.parseInt(ln[1]);
                    int itemId = Integer.parseInt(ln[2]);
                    if (person.equals("STUDENT")) {
                        Student s = game.getStudents().get(personId);
                        Room sRoom = s.getPosition();
                        Item it = sRoom.getItems().get(itemId);

                        game.pickUp(s, it);
                    } else if (person.equals("PROFESSOR")) {
                        Professor p = game.getProfessors().get(personId);
                        Room pRoom = p.getPosition();
                        Item it = pRoom.getItems().get(itemId);

                        game.pickUp(p, it);
                    } else if (person.equals("CLEANER")) {
                        Cleaner c = game.getCleaners().get(personId);
                        Room cRoom = c.getPosition();
                        Item it = cRoom.getItems().get(itemId);

                        game.pickUp(c, it);
                    }
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int dropItem(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("(.*)+\\d+ \\d+")) {
                    String[] ln = line.split(" ");
                    String person = ln[0];
                    int personId = Integer.parseInt(ln[1]);
                    int itemId = Integer.parseInt(ln[2]);
                    if (person.equals("STUDENT")) {
                        Student s = game.getStudents().get(personId);
                        Item it = s.getInventory().get(itemId);

                        game.drop(s, it);
                    } else if (person.equals("PROFESSOR")) {
                        Professor p = game.getProfessors().get(personId);
                        Item it = p.getInventory().get(itemId);

                        game.drop(p, it);
                    } else if (person.equals("CLEANER")) {
                        Cleaner c = game.getCleaners().get(personId);
                        Item it = c.getInventory().get(itemId);

                        game.drop(c, it);
                    }
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int useItem(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("(.*)+\\d+ \\d+")) {
                    String[] ln = line.split(" ");
                    String person = ln[0];
                    int personId = Integer.parseInt(ln[1]);
                    int itemId = Integer.parseInt(ln[2]);
                    if (person.equals("STUDENT")) {
                        Student s = game.getStudents().get(personId);
                        Item it = s.getInventory().get(itemId);

                        game.use(s, it);
                    } else if (person.equals("PROFESSOR")) {
                        Professor p = game.getProfessors().get(personId);
                        Item it = p.getInventory().get(itemId);

                        game.use(p, it);
                    } else if (person.equals("CLEANER")) {
                        Cleaner c = game.getCleaners().get(personId);
                        Item it = c.getInventory().get(itemId);

                        game.use(c, it);
                    }
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int divideRoom(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+")) {
                    String[] ln = line.split(" ");
                    int roomIdx = Integer.parseInt(ln[0]);

                    List<Room> map = game.getMap();
                    Room r1 = map.get(roomIdx);

                    game.divideRoom(r1);
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int mergeRooms(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("(.*)+\\d+ \\d+")) {
                    String[] ln = line.split(" ");
                    int room1Idx = Integer.parseInt(ln[0]);
                    int room2Idx = Integer.parseInt(ln[1]);

                    List<Room> map = game.getMap();
                    Room r1 = map.get(room1Idx);
                    Room r2 = map.get(room2Idx);

                    game.mergeRooms(r1, r2);
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }
}
