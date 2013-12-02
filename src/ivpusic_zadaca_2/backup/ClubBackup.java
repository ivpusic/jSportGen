/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2.backup;

/**
 *
 * @author ipusic
 */
public class ClubBackup {

    protected String name;
    protected int position;

    public ClubBackup(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void printClubInfo() {
        String out = String.format("%s: %s", getPosition(), getName());
        System.err.println(out);
    }
}