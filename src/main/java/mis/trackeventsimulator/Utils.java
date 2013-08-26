/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jwalton
 */
public class Utils {
 
    public static int generateRandomIndex(int indexLength) {
        int index = (int) (Math.floor(Math.random() * indexLength));

        return index;
    }
    
    public static List<City> generateCityLoopList(int length, List<City> cities){
        List<City> cityLoopList = new ArrayList<City>();
        
        Set<City> citiesInLoop = new HashSet<City>();
        while(citiesInLoop.size() < length){
            City newCity = cities.get(generateRandomIndex(cities.size()));
            citiesInLoop.add(newCity);
        }
        cityLoopList.addAll(citiesInLoop);
        return cityLoopList;
    }
}
