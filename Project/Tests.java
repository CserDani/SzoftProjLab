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
        int roomNumber = game.getMap().size();
        int maxIndex = game.getMap().size() - 1;
        Room r = game.getMap().get(5);

        game.divideRoom(r);
        Room newR = game.getMap().get(maxIndex+1);

        assertEquals(roomNumber+1, game.getMap().size());
        assertEquals(1, newR.getNeighbourDoors().size());
        assertEquals(r, newR.getNeighbourDoors().get(0).getNextRoom(newR));
    }

    @Test
    public void testBoardCleaner() {
        Student s = game.getStudents().get(0);
        Room actual = s.getPosition();
        Item it = actual.getItems().get(8);
        game.pickUp(s, it);
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        game.use(s, s.getInventory().get(0));

        assertTrue(s.getPosition().getPersons().get(0).getNotConscious());
    }

    @Test
    public void testHolyPint() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        Item i = items.get(7);
        game.pickUp(s, i);
        game.use(s, i);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        actual = s.getPosition();
        assertEquals(100, s.getHealth());
    }

    @Test
    public void testCleanerMove() {
        Cleaner c = game.getCleaners().get(0);
        Room actual = c.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(c, d);

        assertEquals("Szoba1", c.getPosition().getName());
    }

    @Test
    public void testSticky() {
        Cleaner c = game.getCleaners().get(0);
        Room actual = c.getPosition();
        Door d = actual.getNeighbourDoors().get(2);
        game.move(c, d);

        Student s = game.getStudents().get(0);
        actual = s.getPosition();
        d = actual.getNeighbourDoors().get(2);
        for(int i = 0; i<5; i++) {
            game.move(s, d);
            game.move(s, s.getPosition().getNeighbourDoors().get(0));
        }
        game.move(s, d);
        List<Item> items = s.getPosition().getItems();
        Item i = items.get(0);
        game.pickUp(s, i);

        assertEquals(1, items.size());
        assertEquals(0, s.getInventory().size());
    }

    @Test
    public void testFakeFFP2() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(6);
        game.pickUp(s, i);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(2);
        game.move(s, d);
        actual = s.getPosition();
        assertEquals("Szoba3", s.getPosition().getName());
        assertTrue(s.getPosition().getIsGassed());
        assertTrue(s.getNotConscious());
    }

    @Test
    public void testFakeLogar() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        Item i = items.get(9);
        int itemsCount = items.size();
        game.pickUp(s, i);

        assertEquals(itemsCount-1, items.size());
        assertEquals(1, s.getInventory().size());
        assertFalse(game.getWon());
    }

    @Test
    public void testFakeTVSZ() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(5);
        game.pickUp(s, i);
        int newCount = items.size();
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);
        actual = s.getPosition();

        assertEquals(90, s.getHealth());
        assertEquals(2, actual.getPersons().size());
        assertEquals(itemsCount-1, newCount);
        assertEquals("Szoba1", actual.getName());
    }

    @Test
    public void testLogarlec() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        Item i = items.get(3);
        int itemsCount = items.size();
        game.pickUp(s, i);

        assertEquals(itemsCount-1, items.size());
        assertEquals(1, s.getInventory().size());
        assertTrue(game.getWon());
    }

    @Test
    public void testProfMove() {
        Professor p = game.getProfessors().get(0);
        Room actual = p.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(p, d);

        assertEquals("Szoba0", p.getPosition().getName());
    }

    @Test
    public void testCantMoveVanish() {
        Student s = game.getStudents().get(0);
        Room actualRoom = s.getPosition();
        Door cursedRoomDoor = actualRoom.getNeighbourDoors().get(3);
        Room cursedRoom = cursedRoomDoor.getNextRoom(actualRoom);
        if(!cursedRoomDoor.getVanish()) {
            cursedRoomDoor.setVanish();
        }

        assertEquals("Szoba0", s.getPosition().getName());
    }

    @Test
    public void testOneWay() {
        Student s = game.getStudents().get(0);
        Room actualRoom = s.getPosition();
        Door oneWayDoor = actualRoom.getNeighbourDoors().get(4);
        game.move(s, oneWayDoor);
        game.move(s, oneWayDoor);
        actualRoom = s.getPosition();

        assertTrue(oneWayDoor.isOneWay());
        assertEquals("Szoba7", actualRoom.getName());
    }
}
