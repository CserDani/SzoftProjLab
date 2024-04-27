import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LoadGameState {
    private Game game;
    private int studID = 0;
    private int profID = 0;

    private int cleanID = 0;

    private int logID = 0;

    private int roomID = 0;
    public Game getGame() { return game; }

    public LoadGameState(String filename, Game g) throws IOException {
        this.game = g;
        readFile(filename);
    }

    /*public Controller getController(){
        return controller;
    }*/

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

    public int readLogarlec(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Logarlec log = new Logarlec("Logarlec" + logID);
                game.getMap().get(position).addItem(log);
                log.setGame(game);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

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

    public int readAirFreshener(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                AirFreshener item = new AirFreshener("Legtisztito" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readBoardCleaner(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                BoardCleaner item = new BoardCleaner("Tablatorlo" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readCamembert(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Camembert item = new Camembert("Camembert" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readFakeFFP2(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FakeFFP2 item = new FakeFFP2("FakeFFP2" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readFakeLogarlec(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FakeLogarlec item = new FakeLogarlec("FakeLogarlec" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readFakeTVSZ(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FakeTVSZ item = new FakeTVSZ("FakeTVSZ" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readFFP2(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                FFP2 item = new FFP2("FFP2" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readHolyPint(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                HolyPint item = new HolyPint("HolyPint" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readTransistors(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                Transistor item = new Transistor("Tranzisztor" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }

    public int readTVSZ(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int position = Integer.parseInt(line);
                TVSZ item = new TVSZ("TVSZ" + logID);
                game.getMap().get(position).addItem(item);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }
}
