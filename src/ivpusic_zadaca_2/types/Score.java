/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ivpusic_zadaca_2.types;

import java.io.Serializable;

/**
 *
 * @author ipusic
 */
public class Score implements Serializable {
    public String firstClub;
    public String secondClub;
    public int result;
    public int round;
    
    public Score(String firstClub, String secondClub, int result, int round) {
        this.firstClub = firstClub;
        this.secondClub = secondClub;
        this.result = result;
        this.round = round;
    }
    
    public int getRound() {
        return round;
    }
    
    public String getScore() {
        return String.format("%s (%s) %s", firstClub, String.valueOf(result), secondClub);
    }
}