/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jwalton
 */
public class Position implements Serializable{
    
    private double lat;
    private double lon;
    private long timestamp;
    
    public Position(double lat, double lon, long timestamp){
        this.lat = lat;
        this.lon = lon;
        this.timestamp = timestamp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString(){
        Date date = new Date();
        date.setTime(timestamp);
        return this.lat+", "+this.lon+" "+date.toString();
    }
}
