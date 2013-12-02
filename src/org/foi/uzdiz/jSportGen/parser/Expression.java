/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.uzdiz.jSportGen.parser;

import org.foi.uzdiz.jSportGen.types.Club;
import java.util.Map;

/**
 *
 * @author ipusic
 */
public interface Expression {
    public Club interpret(Map<String, Object> context);
}
