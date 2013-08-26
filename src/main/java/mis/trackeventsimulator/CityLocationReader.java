/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jwalton
 */
public class CityLocationReader {

    public final static String cityLocationFile = "GeoLiteCity-Location.csv";
    private List<City> cities = new ArrayList<City>();

    public CityLocationReader(){
        readFile();
    }
    
    public List<City> getCitiesList() {
        return this.cities;
    }
    
    public Map<String,City> getCitiesMap(){
        Map<String,City> cityMap = new HashMap<String,City>();
        for(City city: cities){
            cityMap.put(city.countryCode.toUpperCase()+"_"+city.name.toUpperCase(), city);
        }
        
        return cityMap;
    }

    private void readFile() {
        try {
            System.out.println("Reading in city location file");
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + cityLocationFile)));
            String line = br.readLine();
            br.readLine();
            //first line is copyright, second line is headers
            while ((line = br.readLine()) != null) {
                //System.out.println("LINE = " + line);
                String[] lineSplit = line.split(",");
                if (lineSplit.length >= 7) {
                    try {
                        String countryCode = processStringValue(lineSplit[1]);

                        String cityName = processStringValue(lineSplit[3]);
                        double lat = Double.parseDouble(lineSplit[5]);
                        double lon = Double.parseDouble(lineSplit[6]);
                        City city = new City(countryCode, cityName, lat, lon);
                        cities.add(city);
                    } catch (Exception e) {
                        System.out.println("error processing city: " + line);
                        e.printStackTrace();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String processStringValue(String value) {
        if (value.indexOf("\"") >= 0) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }

    public static void main(String[] args) {
        CityLocationReader reader = new CityLocationReader();
        Map<String,City> cityMap = reader.getCitiesMap();
        
        City tokyo = cityMap.get("FR_PARIS");
        System.out.println("City: "+tokyo.name);
    }
}
