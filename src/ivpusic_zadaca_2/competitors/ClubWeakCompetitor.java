/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2.competitors;

import ivpusic_zadaca_2.types.Club;
import ivpusic_zadaca_2.Worker;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ipusic
 */
public class ClubWeakCompetitor implements Competitor, Serializable {

    private int weakRoundsCount;

    public ClubWeakCompetitor() {
        weakRoundsCount = 0;
    }

    @Override
    public void update(Club context) {
        if (context.getOldPosition() <= context.getPosition()) {
            weakRoundsCount++;
            if (weakRoundsCount == Worker.prague) {
                try {
                    context.setCompetitorType(new ClubNotCompetitor(context));
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClubWeakCompetitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Club " + context.getName() + " is still weak competitor!");
            }
        } else {
            context.setCompetitorType(new ClubNormalCompetitor());
            System.out.println("Club " + context.getName() + " is now normal competitor!");
        }
    }
}
