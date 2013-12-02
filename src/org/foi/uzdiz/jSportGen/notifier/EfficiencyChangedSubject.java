/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.uzdiz.jSportGen.notifier;

import java.util.HashMap;

/**
 *
 * @author ipusic
 */
public class EfficiencyChangedSubject implements Subject {

    private final HashMap<Integer, Observer> observers;
    public String diff;
    
    public EfficiencyChangedSubject() {
        observers = new HashMap<>();
    }
    
    @Override
    public void setState(Integer observerID, String diff) {
        this.diff = diff;
        notifyObserver(observerID);
    }
    
    @Override
    public String getState() {
        return diff;
    }
    
    public void notifyObserver(Integer id) {
        observers.get(id).update(this);
    }
    
    @Override
    public void addObserver(Observer o) {
        observers.put(o.getID(), o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o.getID());
    }
    
}