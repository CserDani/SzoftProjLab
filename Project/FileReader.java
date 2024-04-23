import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileReader {
    private Controller controller;

    public FileReader(String filename) throws IOException {
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
                    i = readDoors1(line, i+1);
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
                controller.getMap().add(new Room(capacity));        //Room konstruktor capacity paraméterrel!!
            } else {
                return i - 1;
            }
        }
        return l.size();
    }
}
