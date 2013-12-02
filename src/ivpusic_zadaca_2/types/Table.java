/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2.types;

import ivpusic_zadaca_2.backup.ClubBackup;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ipusic
 */
public class Table implements Cloneable, Serializable {

    private List<Club> clubs;
    private int round;

    public Table(int round) {
        this.round = round;
        clubs = new ArrayList<>();
    }

    public void addClub(Club club) {
        clubs.add(club);
    }

    public List<Club> getTable() {
        return clubs;
    }

    public int getRound() {
        return round;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public Table deepClone() throws CloneNotSupportedException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Table) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public final List<Club> cloneClubs() {
        List<Club> clonedList = new ArrayList<>(clubs.size());
        for (Club club : clubs) {
            clonedList.add(club);
        }
        return clonedList;
    }

    public String[] getClubNamesAsArray() {
        String clubNames[] = new String[clubs.size()];
        for (int i = 0; i < clubs.size(); i++) {
            Club c = clubs.get(i);
            clubNames[i] = c.getPosition() + c.getName();
        }
        return clubNames;
    }
    
    public List<ClubBackup> makeClubBackup() {
        List<ClubBackup> cb = new ArrayList<>();
        for (Club c : clubs) {
            cb.add(new ClubBackup(c.getName(), c.getPosition()));
        }
        return cb;
    }
}
