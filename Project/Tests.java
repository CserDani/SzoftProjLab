import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class Tests {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
        game.gameLoad("testMap.txt");
    }

    @Test
    public void testSimpleMove() {
        Student s = game.getStudents().get(0);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);

        assertEquals("Szoba1", s.getPosition().getName());
    }

    @Test
    public void testCantMove() {
        Student s = game.getStudents().get(0);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(1);
        Room next = d.getNextRoom(actual);
        game.move(s, d);

        assertEquals("Szoba0", s.getPosition().getName());
        assertFalse(next.isNotFull());
    }

    @Test
    public void testDamageMove() {
        Student s = game.getStudents().get(0);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        s = game.getStudents().get(0);

        assertEquals("Szoba1", s.getPosition().getName());
        assertEquals(90, s.getHealth());
        assertEquals(2, s.getPosition().getPersons().size());
    }

    @Test
    public void testPickUp() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(0);
        game.pickUp(s, i);

        assertEquals(itemsCount-1, items.size());
        assertEquals(1, s.getInventory().size());
    }

    @Test
    public void testCantPickUp() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item it;
        for(int i = 0; i < 5; i++) {
            it = items.get(0);
            game.pickUp(s, it);
        }
        it = items.get(0);
        game.pickUp(s, it);

        assertEquals(5, s.getInventory().size());
        assertEquals(itemsCount-5, items.size());
    }

    @Test
    public void testDrop() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(0);
        game.pickUp(s, i);
        game.drop(s, i);

        assertEquals(itemsCount, items.size());
        assertEquals(0, s.getInventory().size());
    }

    @Test
    public void testTransistorUse() {
        Student s = game.getStudents().get(0);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item t1 = items.get(0);
        Item t2 = items.get(1);
        game.pickUp(s, t1);
        game.pickUp(s, t2);
        game.use(s, t1);
        game.use(s, t2);
        game.drop(s, t1);
        actual = s.getPosition();
        d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        actual = s.getPosition();
        d = actual.getNeighbourDoors().get(2);
        game.move(s, d);
        game.drop(s, t2);

        assertEquals("Szoba1", s.getPosition().getName());
    }

    @Test
    public void testTVSZ() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(0);
        game.pickUp(s, i);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        actual = s.getPosition();
        Passive p = s.getDamageHelpItems().get(0);
        assertEquals(100, s.getHealth());
        assertEquals(2, p.getDurability());
    }

    @Test
    public void testCamembert() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(2);
        game.pickUp(s, i);
        game.use(s, i);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        actual = s.getPosition();
        assertEquals("Szoba0", s.getPosition().getName());
        assertEquals(0, s.getInventory().size());
    }

    @Test
    public void testFFP2() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(1);
        game.pickUp(s, i);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(2);
        game.move(s, d);
        actual = s.getPosition();
        assertEquals("Szoba3", s.getPosition().getName());
        assertTrue(s.getPosition().getIsGassed());
        assertFalse(s.getNotConscious());
    }

    @Test
    public void testAirFreshener() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i1 = items.get(1);
        game.pickUp(s, i1);
        Item i2 = items.get(3);
        game.pickUp(s, i2);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(2);
        game.move(s, d);
        game.drop(s, i2);
        actual = s.getPosition();
        assertEquals("Szoba3", s.getPosition().getName());
        assertFalse(s.getPosition().getIsGassed());
    }

    @Test
    public void testMergeRooms() {
        Room r1 = game.getMap().get(3);
        Room r2 = game.getMap().get(4);
        game.mergeRooms(r2, r1);
        assertEquals(10, r2.getCapacity());
        assertTrue(r2.getIsGassed());
        assertEquals(2, r2.getItems().size());
    }

    @Test
    public void testSplitRooms() {

    }

    @Test
    public void testBoardCleaner() {

    }

    @Test
    public void testHolyPint() {

    }

    @Test
    public void testCleanerMove() {

    }

    @Test
    public void testSticky() {

    }

    @Test
    public void testFakeFFP2() {

    }

    @Test
    public void testFakeLogar() {

    }

    @Test
    public void testFakeTVSZ() {

    }

    @Test
    public void testLogarlec() {

    }

    @Test
    public void testProfMove() {

    }
}
