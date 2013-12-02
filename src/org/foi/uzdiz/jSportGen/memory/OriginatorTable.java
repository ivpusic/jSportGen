/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.jSportGen.memory;

import org.foi.uzdiz.jSportGen.backup.TableBackup;

/**
 *
 * @author ipusic
 */
public class OriginatorTable {

    private TableBackup state;

    public void set(TableBackup state) {
        this.state = state;
    }

    public TableBackup get() {
        return (TableBackup)state;
    }

    public Memento saveToMemento() {
        return new Memento(state);
    }

    public void restoreFromMemento(Memento m) {
        state = (TableBackup)m.getSavedState();
    }
}
