public class Door {
    private Room firstRoom;
    private Room secondRoom;
    private boolean vanished = false;
    private boolean oneWay;
    public Room getNextRoom(Room from) {
        if(from == firstRoom) {
            return secondRoom;
        }

        return firstRoom;
    }
    public Door(Room firstRoom, Room secondRoom, boolean oneWay) {
        this.firstRoom = firstRoom;
        this.secondRoom = secondRoom;
        this.oneWay = oneWay;
        System.out.println("Door constructor!");
    }

    public void movedBy(Student s) {
        if(!vanished) {
            if(!oneWay) {
                if(s.getPosition() == firstRoom) {
                    secondRoom.moveRoom(s);
                } else {
                    firstRoom.moveRoom(s);
                }
            } else {
                if(s.getPosition() == firstRoom) {
                    secondRoom.moveRoom(s);
                }
            }
        }
    }

    public void movedBy(Professor p) {
        if(!vanished) {
            if(!oneWay) {
                if(p.getPosition() == firstRoom) {
                    secondRoom.moveRoom(p);
                } else {
                    firstRoom.moveRoom(p);
                }
            } else {
                if(p.getPosition() == firstRoom) {
                    secondRoom.moveRoom(p);
                }
            }
        }
    }
}
