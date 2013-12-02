/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.jSportGen.template;

import org.foi.uzdiz.jSportGen.Worker;
import org.foi.uzdiz.jSportGen.types.Table;
import org.foi.uzdiz.jSportGen.types.Club;
import org.foi.uzdiz.jSportGen.types.Result;
import org.foi.uzdiz.jSportGen.memory.OriginatorScore;
import org.foi.uzdiz.jSportGen.memory.OriginatorTable;
import org.foi.uzdiz.jSportGen.notifier.Subject;
import org.foi.uzdiz.jSportGen.backup.ClubBackup;
import org.foi.uzdiz.jSportGen.types.Score;
import org.foi.uzdiz.jSportGen.backup.TableBackup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ipusic
 */
public class Soccer extends Sport {

    private List<Club> tableForGeneration;
    private final Subject effChangedSubject;
    private final OriginatorTable originatorTable;
    private final OriginatorScore originatorScore;
    private final int controlInterval;
    
    private int currentInterval;
    public static int prague;

    public Soccer(Table table, Subject effChangedSubject, int controlInterval) throws CloneNotSupportedException {
        // save arguments to instance
        this.controlInterval = controlInterval;
        this.effChangedSubject = effChangedSubject;
        this.table = table;
        
        // make internal init
        currentInterval = 0;
        originatorTable = new OriginatorTable();
        originatorScore = new OriginatorScore();

        // make initial backup -> start competition
        TableBackup currentTable = new TableBackup(table.getRound(), table.makeClubBackup());
        originatorTable.set(currentTable);
        caretakerTable.addMemento(originatorTable.saveToMemento());
    }

    public int getCaretakerCount() {
        return caretakerTable.count();
    }

    public String getOutOfBoundsMessage() {
        return "Error while getting caretaker element. Possible reason: ";
    }

    public void printClubCaretakerElement(int element) {
        try {
            originatorTable.restoreFromMemento(caretakerTable.getMemento(element));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(getOutOfBoundsMessage() + e.getMessage());
            return;
        }

        TableBackup t = originatorTable.get();
        System.out.println("Table for round: " + t.getRound());
        for (ClubBackup club : t.getClubs()) {
            club.printClubInfo();
        }
    }

    public void printScoreCaretakerElement(int element) {
        try {
            originatorTable.restoreFromMemento(caretakerTable.getMemento(element));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(getOutOfBoundsMessage() + e.getMessage());
            return;
        }

        int round = originatorTable.get().getRound();

        for (int i = 0; i < caretakerScore.count(); i++) {
            originatorScore.restoreFromMemento(caretakerScore.getMemento(i));
            Score s = originatorScore.get().get(0);
            if (round == s.getRound()) {
                List<Score> scores = originatorScore.get();
                for (Score score : scores) {
                    System.out.println(score.getScore());
                }
                break;
            }
        }
    }

    public void printClubScores(String clubName) {
        boolean foundClub = false;
        for (int i = 0; i < caretakerScore.count(); i++) {
            originatorScore.restoreFromMemento(caretakerScore.getMemento(i));
            List<Score> scores = originatorScore.get();
            for (Score score : scores) {
                if (score.firstClub.equals(clubName) || score.secondClub.equals(clubName)) {
                    System.out.println(score.getScore());
                    foundClub = true;
                }
            }
        }
        if (!foundClub) {
            System.out.println("Please provide correct club name!");
        }
    }

    public class CustomComparator implements Comparator<Club> {

        @Override
        public int compare(Club o1, Club o2) {
            double score1 = o1.getScore();
            double score2 = o2.getScore();

            if (score1 == score2) {
                return 0;
            } else if (score1 > score2) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public void updateClubPositions(Table t) {
        for (int i = 0; i < t.getTable().size(); i++) {
            Club club = t.getTable().get(i);
            if (i != 0 && club.getScore() == t.getTable().get(i - 1).getScore()) {
                club.setPosition(t.getTable().get(i - 1).getPosition());
            } else {
                club.setPosition(i + 1);
            }
        }
    }

    @Override
    public void updateTable(Table oldTable) {
        Collections.sort(table.getTable(), new CustomComparator());
        boolean eq = Arrays.equals(table.getClubNamesAsArray(), oldTable.getClubNamesAsArray());

        if (!eq) {
            updateClubPositions(table);
            originatorTable.set(new TableBackup(table.getRound(), table.makeClubBackup()));
            caretakerTable.addMemento(originatorTable.saveToMemento());
        }
        updateClubPositions(table);
    }

    @Override
    public void updateEfficiency() {
        for (Club club : table.getTable()) {
            double oldEff = club.getEfficiency();
            club.setEfficiency((double)club.getScore() / club.getCurrentRound());
            if (oldEff != club.getEfficiency()) {
                double diff = club.getEfficiency() - oldEff;
                effChangedSubject.setState(club.getID(), String.valueOf(diff));
            }
        }
    }

    public void resolveOddClubState() {
        for (Club club : tableForGeneration) {
            if (!club.isPaused()) {
                club.setPaused(true);
                tableForGeneration.remove(club);
                return;
            }
        }

        // we need to reset isPaused club state
        for (Club club : tableForGeneration) {
            club.setPaused(false);
        }
        resolveOddClubState();
    }

    @Override
    public void generateOpponentsAndResults(int roundCount) {

        Club first = null, second = null;
        Random rand = new Random();
        List<Score> scores = new ArrayList<>();

        // generating opponents
        if (tableForGeneration.size() % 2 != 0) {
            resolveOddClubState();
        }

        while (!tableForGeneration.isEmpty()) {
            int number = rand.nextInt(tableForGeneration.size());
            if (first == null) {
                first = tableForGeneration.get(number);
            } else if (second == null) {
                second = tableForGeneration.get(number);
            }
            tableForGeneration.remove(number);

            if (first != null && second != null) {
                // generating results
                int result = rand.nextInt(3);
                scores.add(new Score(first.getName(), second.getName(), result, roundCount));
                switch (result) {
                    case 0:
                        first.setScoreByResult(Result.REMI);
                        second.setScoreByResult(Result.REMI);
                        break;
                    case 1:
                        first.setScoreByResult(Result.WIN);
                        second.setScoreByResult(Result.LOSE);
                        break;
                    case 2:
                        first.setScoreByResult(Result.LOSE);
                        second.setScoreByResult(Result.WIN);
                        break;
                    default:
                        System.err.println("Error while generating club result! Program will exit now...");
                        System.exit(0);
                }
                first = null;
                second = null;
            }
        }

        // save scores to archive
        originatorScore.set(scores);
        caretakerScore.addMemento(originatorScore.saveToMemento());
    }

    public boolean checkState() {
        /**
         * STATE CHECKING
         */
        if (currentInterval == controlInterval) {
            System.out.println("Control in round " + (table.getRound()));
            currentInterval = 0;
            List<Club> clubsForRemove = new ArrayList<>();
            for (Club club : table.getTable()) {
                if (club.updateState()) {
                    clubsForRemove.add(club);
                }
            }

            for (Club club : clubsForRemove) {
                table.getTable().remove(club);
            }
            if (table.getTable().size() <= 2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean playRound() {

        // clone existing table
        try {
            oldTable = table.deepClone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }

        // check state
        if (!checkState()) {
            return false;
        }

        // internal game update
        table.setRound(++roundCount);
        tableForGeneration = table.cloneClubs();
        currentInterval++;

        super.playRound();
        
        return true;
    }
}