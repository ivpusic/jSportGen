/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.uzdiz.jSportGen.competitors;

import org.foi.uzdiz.jSportGen.types.Club;
import java.io.Serializable;

/**
 *
 * @author ipusic
 */
public class ClubNotCompetitor implements Competitor, Serializable {

    public ClubNotCompetitor(Club context) throws InterruptedException {
        System.out.println("Club " + context.getName() + " is no more competitor!");
        context.printClubInfo();
    }
    
    @Override
    public void update(Club context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}