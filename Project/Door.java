public class Door {
    private Room nextRoom;
    private boolean vanished = false;
    public Door(Room nextRoom) {
        this.nextRoom = nextRoom;
    }

    public void movedBy(Student s) {
        if(!vanished) {
            nextRoom.moveRoom(s);
        }
    }
    public void movedBy(Professor p) {
        if(!vanished) {
            nextRoom.moveRoom(p);
        }
    }
}
