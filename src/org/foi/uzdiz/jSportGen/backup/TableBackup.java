/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.jSportGen.backup;

import java.util.List;

/**
 *
 * @author ipusic
 */
public class TableBackup {

    private final List<ClubBackup> clubs;
    private final int round;

    public TableBackup(int round, List<ClubBackup> clubs) {
        this.round = round;
        this.clubs = clubs;
    }

    public List<ClubBackup> getClubs() {
        return clubs;
    }

    public int getRound() {
        return round;
    }
}
