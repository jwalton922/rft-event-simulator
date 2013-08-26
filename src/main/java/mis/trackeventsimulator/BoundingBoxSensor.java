/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.io.Serializable;

/**
 *
 * @author jwalton
 */
public class BoundingBoxSensor extends BaseSensor implements Serializable{

    private BoundingBox box;

    public BoundingBoxSensor(String feedName, String sensorName, double probabilityOfSeeingEvent, BoundingBox box) {
        super(feedName, sensorName, probabilityOfSeeingEvent);
        this.box = box;
    }

    @Override
    public boolean canSense(Position position) {
        boolean canSense = box.contains(position);
        if (Math.random() > super.getProbabilityOfSeeingEvent()) {
            canSense = false;
        } 
        
        return canSense;
    }
}
