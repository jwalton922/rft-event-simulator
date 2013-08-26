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
public class BoundingBox implements Serializable{
    
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;
    
    public BoundingBox(double minLon, double minLat, double maxLon, double maxLat){
        this.minLat = minLat;
        this.maxLat = maxLat;
        this.minLon = minLon;
        this.maxLon = maxLon;
    }
    
    public boolean contains(Position position){
        return contains(position.getLat(), position.getLon());
    }
    
    public boolean contains(double lat, double lon){
        if(lat >= minLat && lat <= maxLat && lon >= minLon && lon <= maxLon){
            return true;
        } else {
            return false;
        }
    }
}
