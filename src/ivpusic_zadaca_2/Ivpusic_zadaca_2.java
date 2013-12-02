/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2;

import ivpusic_zadaca_2.types.Table;
import ivpusic_zadaca_2.types.Club;
import ivpusic_zadaca_2.notifier.EfficiencyChangedSubject;
import ivpusic_zadaca_2.notifier.Subject;
import ivpusic_zadaca_2.parser.Evaluator;
import ivpusic_zadaca_2.template.Soccer;
import ivpusic_zadaca_2.template.Sport;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ipusic
 */
public class Ivpusic_zadaca_2 {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.CloneNotSupportedException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, CloneNotSupportedException {

        Table table = new Table(0);
        double intervalSeconds = 0;
        int controlInterval = 0;
        Subject effChangedSubject = new EfficiencyChangedSubject();

        if (args.length == 4) {
            try {
                intervalSeconds = Double.parseDouble((args[1]));
                controlInterval = Integer.parseInt(args[2]);
                Worker.prague = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                System.err.println("Error while parsing program arguments. " + e.getMessage());
                return;
            }

        } else {
            System.err.println("Please provide correct arguments!");
            System.exit(0);
        }

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                String line = br.readLine();
                Evaluator ev = new Evaluator();
                while (line != null) {
                    line = line.replaceAll("\\s+", "");
                    ev.evaluate(line);
                    Club c = ev.interpret(null);
                    // TODO: Temporary solution! Find more efficient solution for checking this condition
                    if (!isClubValid(c, table.getTable())) {
                        System.err.println("Club name and Club ID can be defined only once!");
                        System.exit(0);
                    }
                    table.addClub(c);
                    effChangedSubject.addObserver(c);
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        Thread tr;
        Sport sport = new Soccer(table, effChangedSubject, controlInterval);
        Worker rg = new Worker(intervalSeconds, sport);
        tr = new Thread(rg);
        tr.start(); 
        Command cmd = new Command(rg);
        cmd.startAccepting();
    }
    
    public static boolean isClubValid(Club club, List<Club> clubs) {
        for (Club c : clubs) {
            if (c.getID() == club.getID() || c.getName().equals(club.getName())) {
                return false;
            }
        }
        return true;
    }
}