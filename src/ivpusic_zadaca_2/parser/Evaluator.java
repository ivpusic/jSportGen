/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2.parser;

import ivpusic_zadaca_2.types.Club;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ipusic
 */
public class Evaluator implements Expression {

    private Expression syntaxTree;
    private final Map<String, Object> context;

    public Evaluator() {
        context = new HashMap<>();
    }

    public void evaluate(String line) {
        boolean ids = true;
       String idValue = "";
        String nameValue = "";
        for (int i = 0; i < line.length(); i++) {
            char ch = line.toString().charAt(i);
            if (Character.isDigit(ch) && ids) {
                idValue += line.charAt(i);
            } else if (!Character.isDigit(ch) && idValue.length() > 0) {
                nameValue += line.charAt(i);
                ids = false;
            } else {
                System.err.println("Error while parsing file!");
                System.exit(0);
            }
        }

        if (idValue.length() > 5 || nameValue.length() > 20) {
            System.err.println("Error while parsing file! ID can me 5 characters long, and name can be max 20 characters long!");
            System.exit(0);
        }
        
        // setting context
        context.put("club", new Club());
        context.put("id", Integer.parseInt(idValue));
        context.put("name", nameValue);
        syntaxTree = new Identity(new Name());
    }

    @Override
    public Club interpret(Map<String, Object> context) {
        if (context == null) {
            context = this.context;
        }
        return syntaxTree.interpret(context);
    }
}