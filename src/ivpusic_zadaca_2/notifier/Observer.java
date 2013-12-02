/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ivpusic_zadaca_2.notifier;

/**
 *
 * @author ipusic
 */
public interface Observer {
    public void update(Subject subject);
    public Integer getID();
}