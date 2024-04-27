/**
 * Az Ajtó objektum a szobák közti átjárhatóságot írja. A tulajdonságai közé tartozik,
 * hogy honnan hova vezet az ajtó, illetve, hogy egyirányú-e vagy sem, esetleg elérhető-e
 * vagy nem (lsd. eltűnő ajtók).
 */
public class Door {
    private Room firstRoom;
    private Room secondRoom;
    private boolean vanished = false;
    private boolean oneWay;

    public void setVanish() { vanished = !vanished; }
    public boolean getVanish() { return vanished; }

    public boolean canMove(Room r) {
        if(!oneWay) {
            return true;
        } else {
            if(r == firstRoom) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * getNextRoom függvény: megadja a másik szobát, amelyik nem az ahonnen jövünk
     * @param from ez az a szoba ahonnan közelítjük meg az ajtót
     * @return ez az a szoba ahova érkezünk, ha átmegyünk az ajtón
     */
    public Room getNextRoom(Room from) {
        if(from == firstRoom) {
            return secondRoom;
        }

        return firstRoom;
    }

    /**
     * setRoom függvény: az első paraméterként kapott szobát beállítja a második paraméterként kapott szobára
     * @param bef az első szoba amit átírunk
     * @param now a szoba amit az első helyére teszünk
     */
    public void setRoom(Room bef, Room now) {
        if(bef == firstRoom) {
            firstRoom = now;
        } else {
            secondRoom = now;
        }
    }

    /**
     * isOneWay függvény: megadja, hogy az ajtó egy irányú-e
     * @return az ajtó egy irányú-e
     */
    public boolean isOneWay() {
        return oneWay;
    }

    /**
     * Ajtó konstruktor: létrehoz egy új ajtót a megadott szobák között, a megadott járhatósággal
     * @param firstRoom ha az ajtó egyirányú, akkor innen indul, egyébként ez az egyik szoba amit a másikkal összeköt
     * @param secondRoom ha az ajtó egyirányú, akkor ide hoz, egyébként ez a másik szoba amit az egyikkel összeköt
     * @param oneWay megadja hogy a létrehozandó szoba egyirányú-e
     */
    public Door(Room firstRoom, Room secondRoom, boolean oneWay) {
        this.firstRoom = firstRoom;
        this.secondRoom = secondRoom;
        this.oneWay = oneWay;
    }

    /**
     * movedBy függvény: átviszi s tanulót az egyik szobából a másikba, ha a szituáció a feltételeknek megfelel
     * @param s a tanuló, akit át akarunk vinni az ajtón
     */
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

    /**
     * movedBy függvény: átviszi p tanárt az egyik szobából a másikba, ha a szituáció a feltételeknek megfelel
     * @param p a tanár, akit át akarunk vinni az ajtón
     */
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

    public void movedBy(Cleaner c) {
        if(!vanished) {
            if(!oneWay) {
                if(c.getPosition() == firstRoom) {
                    secondRoom.moveRoom(c);
                } else {
                    firstRoom.moveRoom(c);
                }
            } else {
                if(c.getPosition() == firstRoom) {
                    secondRoom.moveRoom(c);
                }
            }
        }
    }
}
