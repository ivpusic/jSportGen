/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ivpusic_zadaca_2.parser;

import ivpusic_zadaca_2.types.Club;
import java.util.Map;

/**
 *
 * @author ipusic
 */
public class Name implements Expression {

    @Override
    public Club interpret(Map<String, Object> context) {
        Club club = (Club) context.get("club");
        club.setName((String)context.get("name"));
        return club;
    }
}