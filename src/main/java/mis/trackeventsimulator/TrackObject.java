/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author jwalton
 */
public class TrackObject implements Serializable{
    
    private TrackObjectState state;
    private MovementPattern movementPattern;
    private Position currentPosition;
    private Position destination;
    private double speed;
    private String name;
    private int trackCount = 0;
    private Map<String,Object> properties = new HashMap<String,Object>();

    public TrackObjectState getState() {
        return state;
    }

    public void setState(TrackObjectState state) {
        this.state = state;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Position getDestination() {
        return destination;
    }

    public void setDestination(Position destination) {
        this.destination = destination;
    }

    public MovementPattern getMovementPattern() {
        return movementPattern;
    }

    public void setMovementPattern(MovementPattern movementPattern) {
        this.movementPattern = movementPattern;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }
    
    
    
}
