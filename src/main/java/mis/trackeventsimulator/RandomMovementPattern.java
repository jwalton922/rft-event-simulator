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
public class RandomMovementPattern implements MovementPattern, Serializable{
    private City currentCity;
    private List<City> cityList;
    public RandomMovementPattern(City startCity, List<City> cityList){
        this.currentCity = startCity;
        this.cityList = cityList;
    }
    
    public Position getNextDestination(){
        boolean foundNewCity = false;
        while(!foundNewCity){
            City newCity = cityList.get(Utils.generateRandomIndex(cityList.size()));
            if(!currentCity.equals(newCity)){
                currentCity = newCity;
                foundNewCity = true;
            }
        }
        Position position = new Position(currentCity.lat, currentCity.lon, 0);
        position.setProperty("DESTINATION_CITY", currentCity.name);
        position.setProperty("DESTINATION_COUNTRY", currentCity.countryCode);
        return position;
    }
    
}
