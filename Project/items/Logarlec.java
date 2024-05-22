package items;

import characters.Professor;
import characters.Student;
import guimvc.Game;
import roomanddoor.Room;

/**
 *  A Logarléc objektum, ahogy a neve is mutatja, a játék főeleme. Ez a tárgy az, amivel a játékos meg tudja nyerni a játékot.
 * Az objektum felel a játék megnyeréséért.
 */
public class Logarlec extends Item {
    private Game game;
    /**
     * logarléc konstruktor: a megadott névvel létre hoz egy logarlécet
     * @param name ezzel a névvel hozza létre a logarlécet
     */
    public Logarlec(String name) {
        super(name);
        this.game = null;
    }
    public void setGame(Game g) {
        this.game = g;
    }

    /**
     * winGame függvény: feladata levezényelni a játék végét, miután a játékosok nyernek
     */
    public void winGame() {
        if(game != null) {
            game.setWon();
        }
    }

    /**
     * pickedUpBy absztrakt függvény: a tárgyak tanulók általi felvételéért felelős
     * @param s a tanuló, aki felveszi ezt a logarlécet
     */
    public void pickedUpBy(Student s) {
        Room r = s.getPosition();
        s.addItemToInventory(this);
        r.removeItem(this);
        winGame();
    }
    /**
     * pickedUpBy absztrakt függvény: a tárgyak tanárok általi felvételéért felelős
     * @param p a tanár, aki felveszi ezt a logarlécet
     */
    public void pickedUpBy(Professor p) {
        //Nothing here, Professor can't pick up this item
    }
    /**
     * drop absztrakt függvény: a tárgyak tanuló általi eldobásáért felel
     * @param s a tanuló, aki eldobja a logarlécet
     */
    public void drop(Student s) {
        //If student picked it up the game is ended hence he can't drop it
    }
    /**
     * drop absztrakt függvény: a tárgyak tanárok általi eldobásáért felel
     * @param p a tanár, aki eldobja a logarlécet
     */
    public void drop(Professor p) {
        //Professor can't pick it up, so it can't drop it
    }
    /**
     * use absztrakt függvény: a tárgyak hatásainak kiváltásáért felel
     * @param s a tanuló, akire a tárgy hat
     */
    public void use(Student s) {
        //It can't be used
    }
    /**
     * use absztrakt függvény: a tárgyak hatásainak kiváltásáért felel
     * @param p a tanár, akire a tárgy hat
     */
    public void use(Professor p) {
        //It can't be used
    }
}
