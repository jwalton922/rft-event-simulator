/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author jwalton
 */
public class SimulatorState implements Serializable{

    private static Logger log = Logger.getLogger(SimulatorState.class);
    
    private List<TrackObject> trackObjects;
    private Long currentSimTime;
    private Long lastUpdateTime;
    private List<Sensor> sensors;
    private TrackGenerator trackGenerator;
    private double timeMultiplier;
    private double chanceToTakeOff = 0.005;
    
    public SimulatorState(int numObjects, Long simStartTime, double timeMultiplier) {
        currentSimTime = simStartTime;
        this.timeMultiplier = timeMultiplier;
        init(numObjects, simStartTime);
    }
    
    public void setTakeoffChance(double chance){
        this.chanceToTakeOff = chance;
    }

    private void init(int numObjects, Long simulationStartTime) {
        CityLocationReader cityReader = new CityLocationReader();
        List<City> cities = cityReader.getCitiesList();
        trackGenerator = new TrackGenerator();
        trackObjects = new ArrayList<TrackObject>(numObjects);

        for (int i = 0; i < numObjects; i++) {
            City startingCity = cities.get(Utils.generateRandomIndex(cities.size()));
            TrackObject trackObject = new TrackObject();
            trackObject.setName("RFT LIKE OBJECT "+i);
            trackObject.setCurrentPosition(new Position(startingCity.lat, startingCity.lon, simulationStartTime));
            double randomVal = Math.random();
            if (randomVal < .5) {
                trackObject.setMovementPattern(new RandomMovementPattern(startingCity, cities));
                trackObject.getProperties().put("MOVEMENT_TYPE", "RANDOM");
            } else {
                int cityLoopLength = (int) (10 * Math.random());
                if (cityLoopLength < 3) {
                    cityLoopLength = 3;
                }
                trackObject.setMovementPattern(new FixedCityLoopMovementPattern(Utils.generateCityLoopList(cityLoopLength, cities)));
                trackObject.getProperties().put("MOVEMENT_TYPE", "FIXED_CITY_LOOP");
            }
            trackObject.setState(TrackObjectState.STOPPED);
            
            randomVal = Math.random();
            if(randomVal < .15){
                trackObject.getProperties().put("CATEGORY", "MILITARY");
                trackObject.setSpeed(650*timeMultiplier);
            } else {
                trackObject.getProperties().put("CATEGORY", "CIVILIAN");
                trackObject.setSpeed(500*timeMultiplier);
            }
            trackObjects.add(trackObject);
        }
        
        this.lastUpdateTime = System.currentTimeMillis();

    }
    
    public List<TrackObject> getTrackObjects(){
        return this.trackObjects;
    }
    
    public void updateState(){
        long updateTime = System.currentTimeMillis();
        long timeElapsedSinceLastUpdate = updateTime - this.lastUpdateTime;
//        log.debug("Time since last sim state update (NOTt including multiplier): "+timeElapsedSinceLastUpdate);
//        timeElapsedSinceLastUpdate = (long)(timeElapsedSinceLastUpdate*timeMultiplier);
//        log.debug("Time since last sim state update (including multiplier): "+timeElapsedSinceLastUpdate);
        this.lastUpdateTime = updateTime;
        
        for(int i = 0; i < trackObjects.size(); i++){
            TrackObject trackObject = trackObjects.get(i);
            if(trackObject.getState() == TrackObjectState.STOPPED){
                double randomVal = Math.random();
                if(randomVal < chanceToTakeOff){
                    log.debug("Track Object "+trackObject.getName()+" is now moving");
                    //put object into moving state
                    trackObject.setState(TrackObjectState.MOVING);
                    trackObject.setTrackCount(trackObject.getTrackCount()+1);
                    trackObject.getProperties().put("TRACKID", trackObject.getName()+" "+trackObject.getTrackCount());
                    Position destination = trackObject.getMovementPattern().getNextDestination();
                    trackObject.setDestination(destination);                    
                }
            } else {
                //update position
                Position destination = trackObject.getDestination();
                Position newPosition = trackGenerator.stupidGenerationOfPosition(trackObject.getCurrentPosition(), destination.getLat(), destination.getLon(), timeElapsedSinceLastUpdate, trackObject.getSpeed());
                if(newPosition.getLat() == destination.getLat() && newPosition.getLon() == destination.getLon()){
                    trackObject.setState(TrackObjectState.STOPPED);
                    log.debug("Track Object "+trackObject.getName()+" is now stopped");
                }
                newPosition.getMetadata().putAll(trackObject.getDestination().getMetadata());
                trackObject.setCurrentPosition(newPosition);
            }
        }
        long endUpdateTime = System.currentTimeMillis();
        long timeToUpdate = endUpdateTime - updateTime;
        log.debug("It took "+timeToUpdate+" ms to update sim state");
    }
    
}
