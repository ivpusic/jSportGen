/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ivpusic_zadaca_2.competitors;

import ivpusic_zadaca_2.types.Club;
import java.io.Serializable;

/**
 *
 * @author ipusic
 */
public class ClubNormalCompetitor implements Competitor, Serializable {

    @Override
    public void update(Club context) {
        if (context.getOldPosition() < context.getPosition()) {
            System.out.println("Club " + context.getName() + " is now weak competitor!");
            context.setCompetitorType(new ClubWeakCompetitor());
        }
    }
}