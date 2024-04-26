import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.gameLoad("testMap.txt");
    }

    @Test
    void testSimpleMove() {
        Student s = game.getStudents().get(0);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(0);
        game.move(s, d);

        assertEquals("Szoba1", s.getPosition().getName());
    }

    @Test
    void testCantMove() {
        Student s = game.getStudents().get(0);
        Room actual = s.getPosition();
        Door d = actual.getNeighbourDoors().get(1);
        Room next = d.getNextRoom(actual);
        game.move(s, d);

        assertEquals("Szoba0", s.getPosition().getName());
        assertFalse(next.isNotFull());
    }

    @Test
    void testDamageMove() {
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
    void testPickUp() {
        Student s = game.getStudents().get(0);
        List<Item> items = s.getPosition().getItems();
        int itemsCount = items.size();
        Item i = items.get(0);
        game.pickUp(s, i);

        assertEquals(itemsCount-1, items.size());
        assertEquals(1, s.getInventory().size());
    }

    @Test
    void testCantPickUp() {
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
}
