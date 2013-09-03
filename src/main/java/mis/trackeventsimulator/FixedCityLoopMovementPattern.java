/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jwalton
 */
public class FixedCityLoopMovementPattern implements MovementPattern, Serializable{
    
    private int currentCityIndex = 0;
    private List<City> cityLoopList;
    
    public FixedCityLoopMovementPattern(List<City> cityLoopList){
        this.cityLoopList = cityLoopList;
    }

    public Position getNextDestination() {
        currentCityIndex++;
        if(currentCityIndex >= cityLoopList.size()){
            currentCityIndex = 0;
        }
        
        City nextCity = cityLoopList.get(currentCityIndex);
        Position position = new Position(nextCity.lat, nextCity.lon, 0);
        position.setProperty("DESTINATION_CITY", nextCity.name);
        position.setProperty("DESTINATION_COUNTRY", nextCity.countryCode);
        return position;
    }       
    
}
