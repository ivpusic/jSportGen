/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ivpusic_zadaca_2.memory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ipusic
 */
public class Caretaker {
    
    private final List<Memento> savedStates;
    
    public Caretaker() {
        savedStates = new ArrayList<>();
    }
    
    public void addMemento(Memento m) {
        savedStates.add(m);
    }
    
    public Memento getMemento(int index) {
        return savedStates.get(index);
    }
    
    public int count() {
        return savedStates.size();
    }
}