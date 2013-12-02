/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.jSportGen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author ipusic
 */
public class Command {

    private final Worker rg;
    private boolean firstPrint;

    public Command(Worker rg) {
        this.rg = rg;
        firstPrint = true;
    }

    public static void printHelp() {
        System.out.println("######################## HELP ########################");
        System.out.println("stop                 -> Stop thread");
        System.out.println("count                -> Count of archived table states");
        System.out.println("print table  [index] -> Print table on some index");
        System.out.println("print scores [index] -> Print scores on some index");
        System.out.println("print club   [name]  -> Print scores of some club");
        System.out.println("exit                 -> Exit program");
        System.out.println("help                 -> Help");
        System.out.println("#######################################################");
    }

    public void startAccepting() throws IOException {
        while (true) {
            String command[];
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            System.out.print("> ");
            command = in.readLine().split(" ");

            switch (command[0]) {
                case "stop":
                    synchronized (rg) {
                        rg.setEnd();
                        rg.notifyAll();
                        //handleCommand(command);
                        if (firstPrint) {
                            Command.printHelp();
                            firstPrint = false;
                        }

                    }
                    break;
                default:
                    if (rg.isEnd()) {
                        handleCommand(command);
                    } else {
                        System.out.println("First you must type 'stop' to stop thread for generating results!");
                    }
                    break;
            }
        }
    }

    public String getMissingArgumentCountMsg(int requiredArgc) {
        return "You must provide at least " + requiredArgc + " arguments!";
    }

    public String getWrongParameterTypeMsg(String which, String type) {
        return which + " parameter must be " + type;
    }

    public void handlePrint(String command[]) {
        int element;
        switch (command[1]) {
            case "table":
                try {
                    element = Integer.parseInt(command[2]);
                    rg.printClubCaretakerElement(element);
                } catch (NumberFormatException nfe) {
                    System.err.println(getWrongParameterTypeMsg("Third", "integer"));
                }
                break;
            case "scores":
                try {
                    element = Integer.parseInt(command[2]);
                    rg.printScoreCaretakerElement(element);
                } catch (NumberFormatException nfe) {
                    System.err.println(getWrongParameterTypeMsg("Third", "integer"));
                }
                break;
            case "club":
                rg.printClubScores(command[2]);
                break;
            default:
                System.out.println("Unknown command!");
        }
    }

    public void handleCommand(String command[]) {
        switch (command[0]) {
            case "count":
                System.out.println("Saved states count: " + rg.getCaretakerCount());
                break;
            case "print":
                if (command.length == 3) {
                    handlePrint(command);
                } else {
                    System.err.println(getMissingArgumentCountMsg(3));
                }
                break;
            case "exit":
                System.exit(0);
                break;
            case "help":
                Command.printHelp();
                break;
            default:
                System.out.println("Unknown command!");

        }
    }
}
