package guimvc;

import characters.*;
import roomanddoor.*;
import items.Item;
import testandload.LoadTest;
import testandload.LoadGameState;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements ActionListener {
    private final List<Room> map = new ArrayList<>();
    private int roomNameNumber;
    private final List<Student> students = new ArrayList<>();
    private final List<Professor> professors = new ArrayList<>();
    private final List<Cleaner> cleaners = new ArrayList<>();
    private final Timer roomActionTimer = new Timer(1000, this);
    private int roomActionCounter = 60;
    private boolean gameWon = false;
    private GameView viewObserver = null;
    private int gameTime = 600;
    private final Timer gameTimer = new Timer(1000,this);
    private final Timer profMoveTimer = new Timer(1000, this);
    private int profMoveCounter = 15;
    private final Timer cleanerMoveTimer = new Timer(1000, this);
    private int cleanerMoveCounter = 25;
    private final Random rand = new Random();

    public int getGameTime() { return gameTime; }
    public List<Student> getStudents() { return students; }
    public List<Professor> getProfessors() { return professors; }
    public List<Cleaner> getCleaners() { return cleaners; }
    public List<Room> getMap() { return map; }
    public Room getRandomRoom() {
        return map.get(rand.nextInt(map.size()));
    }
    public Door getRandomDoor(Room r) {
        List<Door> neighbours = r.getNeighbourDoors();
        return neighbours.get(rand.nextInt(neighbours.size()));
    }

    public Game() {
        /* To initialize Game, the Game object is always made after gameLoad */
    }

    public void setViewObserver(GameView viewObserver) {
        this.viewObserver = viewObserver;
    }

    public void setGameObservers() {
        for(Student student : students) {
            student.setGameObserver(this);
        }
    }

    public void setVanishObservers() {
        for(Room room : map) {
            room.setGameObserver(this);
        }
    }

    public void gameLoad(String filename){
        try{
            new LoadGameState(filename, this);
            roomNameNumber = map.size();
        } catch (IOException e){
            Logger logger = Logger.getLogger(Game.class.getName());
            logger.log(Level.WARNING, "IOException when tried to load a Game", e);
        }
    }

    public void testLoad(String filename){
        try{
            new LoadTest(filename, this);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(Game.class.getName());
            logger.log(Level.WARNING, "IOException when tried to load a test", e);
        }
    }

    public void setWon() {
        gameWon = true;
        if(viewObserver != null) {
            stopTimers();
            viewObserver.notifyWin();
        }
    }
    public boolean getWon() { return gameWon; }

    public void move(Person p, Door d) {
        p.move(d);

        int deadStudents = 0;
        for (Student currentStudent : students) {
            if (currentStudent.getHealth() <= 0) {
                if (currentStudent.getPosition() != null) {
                    Room room = currentStudent.getPosition();
                    room.getPersons().remove(currentStudent);
                    currentStudent.setPosNull();
                }
                deadStudents++;
            }
        }

        if(deadStudents == students.size()) {
            stopTimers();
            if(viewObserver != null) {
                viewObserver.notifyLose();
            }
        }

        notifyGameData();
    }
    public void pickUp(Person p, Item i) {
        p.pickUp(i);

        notifyGameData();
    }
    public void drop(Person p, Item i) {
        p.dropItem(i);

        notifyGameData();
    }
    public void use(Person p, Item i) {
        p.useItem(i);

        notifyGameData();
    }
    public void divideRoom(Room r1) {
        Room r2 = r1.roomDivision();
        if(r2 != null) {
            String newRoomName = "Szoba" + roomNameNumber;
            roomNameNumber++;
            r2.setName(newRoomName);
            map.add(r2);

            notifyGameData();
        }
    }
    public void mergeRooms(Room r1, Room r2) {
        boolean mergeResult = r1.mergeRooms(r2);
        if(mergeResult) {
            map.remove(r2);

            notifyGameData();
        }
    }

    public void notifyGameData() {
        if(viewObserver != null) {
            viewObserver.updateUI(students);
        }
    }

    public void startTimers() {
        gameTimer.start();
        roomActionTimer.start();
        profMoveTimer.start();
        cleanerMoveTimer.start();
    }

    public void stopTimers() {
        gameTimer.stop();
        roomActionTimer.stop();
        profMoveTimer.stop();
        cleanerMoveTimer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == roomActionTimer) {
            roomActionCounter--;
            if(roomActionCounter == 0) {
                roomActionCounter = 60;
                Room r1 = getRandomRoom();
                Room r2 = getRandomDoor(r1).getNextRoom(r1);
                mergeRooms(r1, r2);
                divideRoom(getRandomRoom());
            }
        }

        if(e.getSource() == profMoveTimer) {
            profMoveCounter--;
            if(profMoveCounter == 0) {
                profMoveCounter = 15;
                if(!professors.isEmpty()) {
                    Professor p = professors.get(rand.nextInt(professors.size()));
                    Door d = getRandomDoor(p.getPosition());
                    move(p, d);
                    List<Item> itemsInRoom = p.getPosition().getItems();
                    if (!itemsInRoom.isEmpty()) {
                        Item i = itemsInRoom.get(rand.nextInt(itemsInRoom.size()));
                        pickUp(p, i);
                    }
                }
            }
        }

        if(e.getSource() == cleanerMoveTimer) {
            cleanerMoveCounter--;
            if(cleanerMoveCounter == 0) {
                cleanerMoveCounter = 25;

                if(!cleaners.isEmpty()) {
                    Cleaner c = cleaners.get(rand.nextInt(cleaners.size()));
                    Door d = getRandomDoor(c.getPosition());
                    move(c, d);
                }
            }
        }

        if(e.getSource() == gameTimer) {
            gameTime--;

            if(gameTime == 0) {
                stopTimers();
            }

            if(viewObserver != null) {
                viewObserver.notifyGameTime(gameTime);
            }
        }
    }
}
