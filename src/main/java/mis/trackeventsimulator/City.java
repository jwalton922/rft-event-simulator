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
public class City implements Serializable {

    public String countryCode;
    public String name;
    public double lat;
    public double lon;

    public City(String countryCode, String name, double lat, double lon) {
        this.countryCode = countryCode;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
    
    public Position getPosition(){
        return new Position(lat,lon, 0L);
    }

    @Override
    public boolean equals(Object o) {
        boolean isEqual = false;
        if (o instanceof City) {
            City test = (City) o;
            if (this.countryCode.equalsIgnoreCase(test.countryCode) && this.name.equalsIgnoreCase(test.name)) {
                isEqual = true;
            }
        }
        return isEqual;
    }
}
