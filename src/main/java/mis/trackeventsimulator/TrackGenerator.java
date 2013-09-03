/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mis.trackeventsimulator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jwalton
 */
public class TrackGenerator implements Serializable {

//    public Track generateTrack(City start, City end, long startTime) {
//        List<Position> positions = new ArrayList<Position>();
//        String name = "Track " + start.name + " - " + end.name;
//
//        Position p = new Position(start.lat, start.lon, startTime);
//        positions.add(p);
//        boolean done = false;
////        double maxDeltaLat = end.lat - start.lat;
////        double maxDeltaLon = end.lon - start.lon;
//        int count = 0;
//        while (!done) {
//            long timeSinceLastPoint = getTimeDeltaOfNextPoint();
//            p = stupidGenerationOfPosition(p, end.lat, end.lon, timeSinceLastPoint, aircraftSpeedKnots);
//            double deltaLat = Math.abs(p.getLat() - end.lat);
//            double deltaLon = Math.abs(p.getLon() - end.lon);
//            //make sure the max error is greater than possible distance traveled in one step
//            if (deltaLat < 0.1) {
//                p.setLat(end.lat);
//            }
//
//            if (deltaLon < 0.1) {
//                p.setLon(end.lon);
//            }
//            positions.add(p);
//            if (p.getLat() == end.lat && p.getLon() == end.lon) {
//                done = true;
//            }
//
//            count++;
//            if (count > 10000) {
//                done = true;
//            }
//        }
//
//        Track track = new Track(name, positions);
//        track.setStartTime(startTime);
//        track.setEndTime(positions.get(positions.size() - 1).getTimestamp());
//        
//        return track;
//    }

    /**
     * calculates bearing in degrees
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public double calcBearing(double lat1, double lon1, double lat2, double lon2) {
//        lat1 = toRads(lat1);
//        lon1 = toRads(lon1);
//        lat2 = toRads(lat2);
//        lon2 = toRads(lon2);
        double dLon = lon2 - lon1;
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2)
                - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double brng = Math.atan2(y, x) / Math.PI * 180.0;

        return brng;
    }

    public double toRads(double deg) {
        return deg * Math.PI / 180.0;
    }

    public double toDegs(double rad) {
        return rad * 180.0 / Math.PI;
    }

    public Position stupidGenerationOfPosition(Position current, double endLat, double endLon, long timeElapsed, double speedInKnots) {

        double speedInMs = speedInKnots * (1 / 3600000.0);
        double d = speedInMs * timeElapsed;

        double R = 360 * 60; //number of nm (based on nm = 1 minute of a degree)
        double lat1 = toRads(current.getLat());
        double lon1 = toRads(current.getLon());
//        double lat2 = endLat;
//        double lon2 = endLon;
        double brngDegs = calcBearing(lat1, lon1, toRads(endLat), toRads(endLon));
        double brng = toRads(brngDegs);

        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
        double lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

        lat2 = toDegs(lat2);
        lon2 = toDegs(lon2);
        double deltaLat = Math.abs(lat2 - endLat);
            double deltaLon = Math.abs(lon2 - endLon);
            //make sure the max error is greater than possible distance traveled in one step
            if (deltaLat < 0.1) {
                lat2 = endLat;
            }

            if (deltaLon < 0.1) {
                lon2 = endLon;
            }
       
        
        Position p = new Position(lat2, lon2, current.getTimestamp() + timeElapsed);
        p.setHeading(brngDegs);
        return p;
    }

//    public static void main(String[] args) {
//
//        City start = new City("FAKE", "START", 10.0, 10.0);
//        City end = new City("FAKE 2", "END", -22.0, -22.0);
//
//        TrackGenerator generator = new TrackGenerator();
//
//        Track track = generator.generateTrack(start, end, System.currentTimeMillis());
//        int index = 1;
//        for (Position p : track.getPositions()) {
//            System.out.println(index + ": " + p.toString());
//            index++;
//        }
//
//    }
}
