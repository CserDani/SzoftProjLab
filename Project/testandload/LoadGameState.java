package testandload;

import characters.Cleaner;
import characters.Professor;
import characters.Student;
import guimvc.Game;
import items.*;
import roomanddoor.Room;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * A LoadGameState osztály felelős a játékállapot betöltéséért .txt fájlból, a tesztek
 * elvégzéséhez, illetve a játék kezdő állapotának legenerálásához
 */
public class LoadGameState {
    private Game game;
    private int studID = 0;
    private int profID = 0;
    private int cleanID = 0;
    private int roomID = 0;
    public Game getGame() { return game; }

    /**
     * LoadGameState konstruktor: a megadott g játékot eltárolja, majd a megadott filename
     * alapján beolvassa a megfelelő .txt fájlt
     * @param filename
     * @param g
     * @throws IOException
     */
    public LoadGameState(String filename, Game g) throws IOException {
        this.game = g;
        readFile(filename);
    }

    /**
     * readFile függvény: a megadott filename nevű fájlt beolvassa egy for ciklussal, majd
     * a switch-case-ben specifikált kulcsszavaka alapján generál egy pályát
     * @param filename
     * @throws IOException
     */
    public void readFile(String filename) throws IOException{
        Path path = FileSystems.getDefault().getPath("Resources", filename);
        List<String> lines = Files.readAllLines(path);

        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i).trim();

            switch(line) {
                case "SZOBAK":
                    i = readRooms(lines, i+1);
                    break;
                case "AJTOK1":
                    i = readDoors1(lines, i+1);
                    break;
                case "AJTOK2":
                    i = readDoors2(lines, i+1);
                    break;
                case "HALLGATOK":
                    i = readStudents(lines, i+1);
                    break;
                case "PROFESSZOROK":
                    i = readProfessors(lines, i+1);
                    break;
                case "TAKARITOK":
                    i = readCleaners(lines, i+1);
                    break;
                case "LOGARLEC":
                    i = readLogarlec(lines, i+1);
                    break;
                case "LEGTISZTITO":
                    i = readAirFreshener(lines, i+1);
                    break;
                case "TABLATORLO":
                    i = readBoardCleaner(lines, i+1);
                    break;
                case "CAMEMBERT":
                    i = readCamembert(lines, i+1);
                    break;
                case "FAKEFFP2":
                    i = readFakeFFP2(lines, i+1);
                    break;
                case "FAKELOGARLEC":
                    i = readFakeLogarlec(lines, i+1);
                    break;
                case "FAKETVSZ":
                    i = readFakeTVSZ(lines, i+1);
                    break;
                case "FFP2":
                    i = readFFP2(lines, i+1);
                    break;
                case "HOLYPINT":
                    i = readHolyPint(lines, i+1);
                    break;
                case "TRANZISZTOR":
                    i = readTransistors(lines, i+1);
                    break;
                case "TVSZ":
                    i = readTVSZ(lines, i+1);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * readRooms függvény: a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad szobákat a pályához, ha nincs már több szobát tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readRooms(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+(.*)")){
                String[] ln = line.split(" ");
                int capacity = Integer.parseInt(ln[0]);
                boolean gas = Boolean.parseBoolean(ln[1]);
                boolean curse = Boolean.parseBoolean(ln[2]);
                game.getMap().add(new Room("Szoba" + roomID, gas, curse, capacity));
                roomID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readDoors1 függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad egyirányú ajtókat a pályához, ha nincs már több ajtót tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readDoors1(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+ \\d+")){
                String[] rooms = line.split(" ");
                int r1 = Integer.parseInt(rooms[0]);
                int r2 = Integer.parseInt(rooms[1]);
                game.getMap().get(r1).addNeighbour(game.getMap().get(r2), true);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readDoors2 függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad kétirányú ajtókat a pályához, ha nincs már több ajtót tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readDoors2(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+ \\d+")){
                String[] rooms = line.split(" ");
                int r1 = Integer.parseInt(rooms[0]);
                int r2 = Integer.parseInt(rooms[1]);
                game.getMap().get(r1).addNeighbour(game.getMap().get(r2), false);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readStudents függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad tanulókat a pályához, ha nincs már több tanulót tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readStudents(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Student stud = new Student("Hallgato" + studID);
                stud.setStartPosition(game.getMap().get(position));
                game.getStudents().add(stud);
                studID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readProfessors függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad tanárokat a pályához, ha nincs már több tanárt tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readProfessors(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Professor prof = new Professor("Professzor" + profID);
                prof.setStartPosition(game.getMap().get(position));
                game.getMap().get(position).incProfCount();
                game.getProfessors().add(prof);
                profID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readLogarlec függvény: a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad logarleceket a pályához, ha nincs már több logarlecet tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readLogarlec(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Logarlec log = new Logarlec("Logarlec");
                game.getMap().get(position).addItem(log);
                log.setGame(game);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readCleaners függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad takarítókat a pályához, ha nincs már több takarítót tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readCleaners(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Cleaner cl = new Cleaner("Takarito" + cleanID);
                cl.setStartPosition(game.getMap().get(position));
                game.getCleaners().add(cl);
                cleanID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readAirFreshener függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad légtisztítókat a pályához, ha nincs már több légtisztítót tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readAirFreshener(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                AirFreshener item = new AirFreshener("Legtisztito");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readBoardCleaner függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad táblatörlőket a pályához, ha nincs már több táblatörlőt tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readBoardCleaner(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                BoardCleaner item = new BoardCleaner("Tablatorlo");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readCamembert függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad camemberteket a pályához, ha nincs már több camembertet tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readCamembert(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Camembert item = new Camembert("Camembert");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readFakeFFP2 függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad hamis FFP2 maszkokat a pályához, ha nincs már több maszkot tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readFakeFFP2(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FakeFFP2 item = new FakeFFP2("FakeFFP2");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readFakeLogarlec függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad hamis logarleceket a pályához, ha nincs már több hamis logalecet tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readFakeLogarlec(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FakeLogarlec item = new FakeLogarlec("FakeLogarlec");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readFakeTVSZ függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad hamis TVSZ-et a pályához, ha nincs már több hamis TVSZ-t tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readFakeTVSZ(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FakeTVSZ item = new FakeTVSZ("FakeTVSZ");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readFFP2 függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad FFP2-es maszkokat a pályához, ha nincs már több maszkot tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readFFP2(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FFP2 item = new FFP2("FFP2");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readHolyPint függvény: a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad szent söröskorsókat a pályához, ha nincs már több söröskorsót tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readHolyPint(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                HolyPint item = new HolyPint("HolyPint");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readTransistors függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad tranzisztorokat a pályához, ha nincs már több tranzisztort tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readTransistors(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Transistor item = new Transistor("Tranzisztor");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    /**
     * readTVSZ függvény:  a megadott si sortól kezdve elkezdi olvasni a sorok tartalmát, majd
     * a megadott értékek alapján hozzáad TVSZ-et a pályához, ha nincs már több TVSZ-t tartalmazó sor,
     * akkor visszaadja az utolsó olyan sorszámot, ahol még volt
     * @param l
     * @param si
     * @return
     */
    public int readTVSZ(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                TVSZ item = new TVSZ("TVSZ");
                game.getMap().get(position).addItem(item);
            } else {
                return i - 1;
            }
        }
        return l.size();
    }
}
