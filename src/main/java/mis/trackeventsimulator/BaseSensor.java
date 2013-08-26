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
public class BaseSensor implements Sensor, Serializable{
    
    private String feedName;
    private String sensorName;
    private double probabilityOfSeeingEvent;
    
    public BaseSensor(String feedName, String sensorName, double probabilityOfSeeingEvent){
        this.feedName = feedName;
        this.sensorName = sensorName;
        this.probabilityOfSeeingEvent = probabilityOfSeeingEvent;
    }
    
    public boolean canSense(Position position){
        boolean canSense = true;
        if (Math.random() > this.probabilityOfSeeingEvent) {
            canSense = false;
        } 
        
        return canSense;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public double getProbabilityOfSeeingEvent() {
        return probabilityOfSeeingEvent;
    }

    public void setProbabilityOfSeeingEvent(double probabilityOfSeeingEvent) {
        this.probabilityOfSeeingEvent = probabilityOfSeeingEvent;
    }
}
