/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import backtype.storm.Config;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import storm.trident.operation.TridentCollector;
import storm.trident.spout.IBatchSpout;

/**
 *
 * @author jwalton
 */
public class TrackEventSimulatorSpout implements IBatchSpout {

    private static Logger log = Logger.getLogger(TrackEventSimulatorSpout.class);
    private SimulatorState simState;
    private List<BaseSensor> sensors;
    private long eventCount = 0;
    private int batchesEmitted = 0;
    private long timeOfLastEmit = System.currentTimeMillis();

    public TrackEventSimulatorSpout(SimulatorState simState, List<BaseSensor> sensors) {
        this.simState = simState;
        this.sensors = sensors;
    }

    public void open(Map map, TopologyContext tc) {
       System.out.println("TrackEventSimulatorSpout.open called");
       log.info("TrackEventSimulatorSpout.open called");
    }

    public void emitBatch(long batchId, TridentCollector collector) {
        log.info("emitBatch called");
        System.out.println("emitBatch called");
        long currentTime = System.currentTimeMillis();
        if((currentTime - timeOfLastEmit) < 500){
            return;
        }
        simState.updateState();
        List<TrackObject> trackObjects = simState.getTrackObjects();
        for (TrackObject trackObject : trackObjects) {
            if (trackObject.getState() == TrackObjectState.MOVING) {                
                for (BaseSensor sensor : sensors) {
                    if(sensor.canSense(trackObject.getCurrentPosition())){
                        Map<String, Object> event = createEvent(trackObject, sensor);
                        collector.emit(new Values(event));
                        eventCount++;
                    }
                }
            }
        }
        batchesEmitted++;
        log.debug("Spout has emitted: "+eventCount+" events so far. batches: "+batchesEmitted);
        timeOfLastEmit = currentTime;
    }

    private Map<String, Object> createEvent(TrackObject object, BaseSensor sensor) {
        Map<String, Object> event = new HashMap<String, Object>();
        event.put("LATITUDE", object.getCurrentPosition().getLat());
        event.put("LONGITUDE", object.getCurrentPosition().getLon());
        event.put("TIME", object.getCurrentPosition().getTimestamp());
        event.put("HEADING", object.getCurrentPosition().getHeading());
        event.put("FEEDNAME", sensor.getFeedName());
        event.put("SENSOR_NAME", sensor.getSensorName());
        event.put("OBJECTID", object.getName());
        event.put("DATA_SOURCE", "SIMULATOR");
        event.putAll(object.getProperties());
        event.putAll(object.getCurrentPosition().getMetadata());
        return event;
    }

    public void ack(long l) {
    }

    public void close() {
    }

    public Map getComponentConfiguration() {
        return new Config();
    }

    @Override
    public Fields getOutputFields() {
        return new Fields("event");
    }
}
