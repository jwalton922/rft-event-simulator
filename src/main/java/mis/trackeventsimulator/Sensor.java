/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

/**
 *
 * @author jwalton
 */
public interface Sensor {
    
    /**
     * returns true if sensor can detect event
     * 
     * @param p
     * @return 
     */
    public boolean canSense(Position position);
    
}
