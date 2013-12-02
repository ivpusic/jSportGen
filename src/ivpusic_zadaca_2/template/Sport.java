/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2.template;

import ivpusic_zadaca_2.memory.Caretaker;
import ivpusic_zadaca_2.types.Table;

/**
 *
 * @author ipusic
 */
public abstract class Sport {

    protected final Caretaker caretakerTable;
    protected final Caretaker caretakerScore;

    protected Table table;
    protected Table oldTable;
    protected int roundCount;

    protected Sport() {
        // internal init
        caretakerTable = new Caretaker();
        caretakerScore = new Caretaker();
        roundCount = 0;
    }
    
    public Table getTable() {
        return table;
    }

    public Caretaker getScoreCaretaker() {
        return caretakerScore;
    }

    public Caretaker getTableCaretaker() {
        return caretakerTable;
    }

    protected abstract void generateOpponentsAndResults(int roundCount);

    protected abstract void updateTable(Table oldTable);

    protected abstract void updateEfficiency();

    public boolean playRound() {
        // generate opponents and scores
        generateOpponentsAndResults(roundCount);

        // update clubs positions, etc.
        updateTable(oldTable);

        // update club effifiency
        updateEfficiency();
        return true;
    }
}
