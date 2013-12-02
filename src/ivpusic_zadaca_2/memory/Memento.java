/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ivpusic_zadaca_2.memory;

/**
 *
 * @author ipusic
 */
public class Memento {
    
    private final Object state;
    
    public Memento(Object state) {
        this.state = state;
    }
    
    public Object getSavedState() {
        return state;
    }
}