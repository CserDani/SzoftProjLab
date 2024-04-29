import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class Tests {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        game.gameLoad("testMap.txt");
    }

    public void readExpected(String filename) throws IOException {
        Path path = FileSystems.getDefault().getPath("Resources/tests", filename);
        List<String> lines = Files.readAllLines(path);

        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i).trim();

            switch(line) {
                case "STUDENTPOS":
                    i = studentPos(lines, i+1);
                    break;
                case "STUDENTHEALTH":
                    i = studentHealth(lines, i+1);
                    break;
                case "STUDENTINVENTORYSIZE":
                    i = studentInventorySize(lines, i+1);
                    break;
                case "STUDENTNOTCONSCIOUS":
                    i = studentNotConscious(lines, i+1);
                    break;
                case "PROFESSORPOS":
                    i = professorPos(lines, i+1);
                    break;
                case "CLEANERPOS":
                    i = cleanerPos(lines, i+1);
                    break;
                case "ROOMITEMSSIZE":
                    i = roomItemsSize(lines, i+1);
                    break;
                case "ROOMISGASSED":
                    i = roomIsGassed(lines, i+1);
                    break;
                case "ROOMCAPACITY":
                    i = roomCapacity(lines, i+1);
                    break;
                case "MAPSIZE":
                    i = mapSize(lines, i+1);
                    break;
                case "NUMOFDOORS":
                    i = numOfDoors(lines, i+1);
                    break;
                case "NEIGHBOURS":
                    i = neighbours(lines, i+1);
                    break;
                case "GAMEWON":
                    i = gameWon(lines, i+1);
                    break;
                default:
                    break;
            }
        }
    }

    public int studentPos(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int studId = Integer.parseInt(ln[0]);
                    Student s = game.getStudents().get(studId);
                    String roomName = ln[1];

                    assertEquals(roomName, s.getPosition().getName());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int cleanerPos(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int cleanId = Integer.parseInt(ln[0]);
                    Cleaner c = game.getCleaners().get(cleanId);
                    String roomName = ln[1];

                    assertEquals(roomName, c.getPosition().getName());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int professorPos(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int profId = Integer.parseInt(ln[0]);
                    Professor p = game.getProfessors().get(profId);
                    String roomName = ln[1];

                    assertEquals(roomName, p.getPosition().getName());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int studentHealth(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int studId = Integer.parseInt(ln[0]);
                    Student s = game.getStudents().get(studId);
                    int health = Integer.parseInt(ln[1]);

                    assertEquals(health, s.getHealth());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int studentInventorySize(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int studId = Integer.parseInt(ln[0]);
                    Student s = game.getStudents().get(studId);
                    int inventorySize = Integer.parseInt(ln[1]);

                    assertEquals(inventorySize, s.getInventorySize());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int roomItemsSize(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int roomId = Integer.parseInt(ln[0]);
                    Room r = game.getMap().get(roomId);
                    int itemsSize = Integer.parseInt(ln[1]);

                    assertEquals(itemsSize, r.getItems().size());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int roomIsGassed(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int roomId = Integer.parseInt(ln[0]);
                    Room r = game.getMap().get(roomId);
                    int isGassed = Integer.parseInt(ln[1]);

                    assertEquals(isGassed, r.getIsGassed() ? 1 : 0);
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int roomCapacity(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int roomId = Integer.parseInt(ln[0]);
                    Room r = game.getMap().get(roomId);
                    int capacity = Integer.parseInt(ln[1]);

                    assertEquals(capacity, r.getCapacity());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int studentNotConscious(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int studId = Integer.parseInt(ln[0]);
                    Student s = game.getStudents().get(studId);
                    int isNotConscious = Integer.parseInt(ln[1]);

                    assertEquals(isNotConscious, s.getNotConscious() ? 1 : 0);
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int mapSize(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int size = Integer.parseInt(ln[0]);

                    assertEquals(size, game.getMap().size());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int numOfDoors(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int roomId = Integer.parseInt(ln[0]);
                    Room r = game.getMap().get(roomId);
                    int num = Integer.parseInt(ln[0]);

                    assertEquals(num, r.getNeighbourDoors().size());
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    public int neighbours(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int roomId = Integer.parseInt(ln[0]);
                    Room r = game.getMap().get(roomId);
                    int doorId = Integer.parseInt(ln[1]);
                    Door d = r.getNeighbourDoors().get(doorId);
                    String neighbourName = ln[3];

                    assertEquals(neighbourName, d.getNextRoom(r));
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }
    public int gameWon(List<String> l, int si) {
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            try {
                if (line.matches("\\d+ (.*)")) {
                    String[] ln = line.split(" ");
                    int gameWon = Integer.parseInt(ln[0]);

                    assertEquals(gameWon, game.getWon() ? 1 : 0);
                } else {
                    return i - 1;
                }
            } catch (Exception e) { System.out.println("Hibas test fajl beolvasas!"); }
        }

        return l.size();
    }

    @Test
    public void testSimpleMove() {
        game.testLoad("testMove/input.txt");
        try {
            readExpected("testMove/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testCantMove() {
        game.testLoad("testCantMove/input.txt");
        try {
            readExpected("testCantMove/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testDamageMove() {
        game.testLoad("testDamageMove/input.txt");
        try {
            readExpected("testDamageMove/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testPickUp() {
        game.testLoad("testPickUp/input.txt");
        try {
            readExpected("testPickUp/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testCantPickUp() {
        game.testLoad("testCantPickUp/input.txt");
        try {
            readExpected("testCantPickUp/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testDrop() {
        game.testLoad("testDrop/input.txt");
        try {
            readExpected("testDrop/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testTransistorUse() {
        game.testLoad("testTransistorUse/input.txt");
        try {
            readExpected("testTransistorUse/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testTVSZ() {
        game.testLoad("testTVSZ/input.txt");
        try {
            readExpected("testTVSZ/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testCamembert() {
        game.testLoad("testCamembert/input.txt");
        try {
            readExpected("testCamembert/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testFFP2() {
        game.testLoad("testFFP2/input.txt");
        try {
            readExpected("testFFP2/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testAirFreshener() {
        game.testLoad("testAirFreshener/input.txt");
        try {
            readExpected("testAirFreshener/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testMergeRooms() {
        game.testLoad("testMergeRooms/input.txt");
        try {
            readExpected("testMergeRooms/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testSplitRooms() {
        game.testLoad("testSplitRooms/input.txt");
        try {
            readExpected("testSplitRooms/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testBoardCleaner() {
        game.testLoad("testBoardCleaner/input.txt");
        try {
            readExpected("testBoardCleaner/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testHolyPint() {
        game.testLoad("testHolyPint/input.txt");
        try {
            readExpected("testHolyPint/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testCleanerMove() {
        game.testLoad("testCleanerMove/input.txt");
        try {
            readExpected("testCleanerMove/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testSticky() {
        game.testLoad("testSticky/input.txt");
        try {
            readExpected("testSticky/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testFakeFFP2() {
        game.testLoad("testFakeFFP2/input.txt");
        try {
            readExpected("testFakeFFP2/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testFakeLogar() {
        game.testLoad("testFakeLogar/input.txt");
        try {
            readExpected("testFakeLogar/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testFakeTVSZ() {
        game.testLoad("testFakeTVSZ/input.txt");
        try {
            readExpected("testFakeTVSZ/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testLogarlec() {
        game.testLoad("testLogarlec/input.txt");
        try {
            readExpected("testLogarlec/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testProfMove() {
        game.testLoad("testProfMove/input.txt");
        try {
            readExpected("testProfMove/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testCantMoveVanish() {
        game.testLoad("testCantMoveVanish/input.txt");
        try {
            readExpected("testCantMoveVanish/expected.txt");
        } catch (IOException e) {}
    }

    @Test
    public void testOneWay() {
        game.testLoad("testOneWay/input.txt");
        try {
            readExpected("testOneWay/expected.txt");
        } catch (IOException e) {}
    }
}
