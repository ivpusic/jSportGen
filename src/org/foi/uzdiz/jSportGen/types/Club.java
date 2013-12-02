/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.jSportGen.types;

import java.io.Serializable;
import org.foi.uzdiz.jSportGen.competitors.ClubNormalCompetitor;
import org.foi.uzdiz.jSportGen.competitors.ClubNotCompetitor;
import org.foi.uzdiz.jSportGen.competitors.Competitor;
import org.foi.uzdiz.jSportGen.notifier.Observer;
import org.foi.uzdiz.jSportGen.notifier.Subject;

/**
 *
 * @author ipusic
 */
public class Club implements Observer, Serializable {

    private Competitor competitorType;
    private String name;
    private Integer id;
    private int oldPosition;
    private int position;
    private int currentRound;
    private int score;
    private double efficiency;
    private boolean paused;

    public Club() {
        name = "";
        score = 0;
        oldPosition = 1;
        position = 1;
        currentRound = 0;
        paused = false;
        competitorType = new ClubNormalCompetitor();
    }

    public String getName() {
        return name;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getOldPosition() {
        return oldPosition;
    }

    public int getPosition() {
        return position;
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public Integer getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public void setCompetitorType(Competitor competitorType) {
        this.competitorType = competitorType;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public void setScoreByResult(Result result) {
        currentRound++;
        switch (result) {
            case WIN:
                score += 3;
                break;
            case LOSE:
                break;
            case REMI:
                score += 1;
                break;
            default:
                System.err.println("Error while setting club score! Program will exit now...");
                System.exit(0);
        }
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean updateState() {
        competitorType.update(this);
        oldPosition = position;
        return competitorType instanceof ClubNotCompetitor;
    }

    @Override
    public void update(Subject subject) {
        String out = String.format("OK, I received message! Club %s. Diff is: %s", getName(), subject.getState());
        System.out.println(out);
    }

    public void printClubInfo() {
        String out = String.format("%s: %s in round %s. Current Score: %s.", getPosition(), getName(), getCurrentRound(), getScore());
        System.err.println(out);
    }
}
