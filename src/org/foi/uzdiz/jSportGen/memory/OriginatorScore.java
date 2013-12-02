/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.jSportGen.memory;

import org.foi.uzdiz.jSportGen.types.Score;
import java.util.List;

/**
 *
 * @author ipusic
 */
public class OriginatorScore {

    private List<Score> state;

    public void set(List<Score> state) {
        this.state = state;
    }

    public List<Score> get() {
        return state;
    }

    public Memento saveToMemento() {
        return new Memento(state);
    }

    public void restoreFromMemento(Memento m) {
         state = (List<Score>)m.getSavedState();
    }
}