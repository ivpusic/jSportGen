/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2;

import ivpusic_zadaca_2.memory.OriginatorScore;
import ivpusic_zadaca_2.memory.OriginatorTable;
import ivpusic_zadaca_2.template.Sport;
import ivpusic_zadaca_2.backup.ClubBackup;
import ivpusic_zadaca_2.types.Score;
import ivpusic_zadaca_2.backup.TableBackup;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ipusic
 */
public class Worker implements Runnable {

    private final OriginatorTable originatorTable;
    private final OriginatorScore originatorScore;
    private final Sport sport;
    private final double intervalSeconds;
    
    private boolean end;
    public static int prague;


    public Worker(double intervalSeconds, Sport sport) throws CloneNotSupportedException {
        // save arguments
        this.intervalSeconds = intervalSeconds;
        this.sport = sport;
        
        // internal init
        originatorTable = new OriginatorTable();
        originatorScore = new OriginatorScore();
    }

    public int getCaretakerCount() {
        return sport.getTableCaretaker().count();
    }

    public String getOutOfBoundsMessage() {
        return "Error while getting caretaker element. Possible reason: ";
    }

    public void printClubCaretakerElement(int element) {
        try {
            originatorTable.restoreFromMemento(sport.getTableCaretaker().getMemento(element));
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
            originatorTable.restoreFromMemento(sport.getTableCaretaker().getMemento(element));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(getOutOfBoundsMessage() + e.getMessage());
            return;
        }

        int round = originatorTable.get().getRound();

        for (int i = 0; i < sport.getScoreCaretaker().count(); i++) {
            originatorScore.restoreFromMemento(sport.getScoreCaretaker().getMemento(i));
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
        for (int i = 0; i < sport.getScoreCaretaker().count(); i++) {
            originatorScore.restoreFromMemento(sport.getScoreCaretaker().getMemento(i));
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

    public void setEnd() {
        end = true;
    }

    public boolean isEnd() {
        return end;
    }

    @Override
    public synchronized void run() {

        while (true) {

            if (!sport.playRound()) {
                break;
            }

            try {
                long interval = (long) (intervalSeconds * 1000);
                this.wait(interval);
            } catch (InterruptedException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("-----------------------------------------------------");

            if (end) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        originatorTable.set(new TableBackup(sport.getTable().getRound(), sport.getTable().makeClubBackup()));
        sport.getTableCaretaker().addMemento(originatorTable.saveToMemento());
        
        setEnd();
        System.out.println("Thread finished execution!");
        ivpusic_zadaca_2.Command.printHelp();
        Thread.currentThread().interrupt();
    }
}