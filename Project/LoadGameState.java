import java.io.*;
import java.nio.file.*;
import java.util.*;

public class LoadGameState {
    private Controller controller;
    private int studID = 0;
    private int profID = 0;

    private int logID = 0;

    private int roomID = 0;

    public LoadGameState(String filename) throws IOException {
        this.controller = new Controller();
        readFile(filename);
    }

    public Controller getController(){
        return controller;
    }

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
                case "LOGARLEC":
                    i = readLogarlec(lines, i+1);
                    break;
                default:
                    break;
            }
        }
    }

    public int readRooms(List<String> l, int si){
        for(int i = si; i < l.size(); i++){
            String line = l.get(i).trim();

            if(line.matches("\\d+")){
                int capacity = Integer.parseInt(line);
                controller.getMap().add(new Room("Szoba" + roomID, false, false, capacity));
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
                controller.getMap().get(r1).addNeighbour(controller.getMap().get(r2), true);
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
                controller.getMap().get(r1).addNeighbour(controller.getMap().get(r2), false);
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
                stud.setStartPosition(controller.getMap().get(position));
                controller.getStudents().add(stud);
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
                prof.setStartPosition(controller.getMap().get(position));
                controller.getProfessors().add(prof);
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
                controller.getMap().get(position).addItem(log);
                logID++;
            } else {
                return i - 1;
            }
        }
        return l.size();
    }
}
