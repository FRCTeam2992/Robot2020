package frc.lib.util;

import java.util.ArrayList;

/**
 * Handles creating list of speeds and distances.
 * <p>
 * This class allows you to create list of speeds and distances for use with
 * shooters. It can find the closest value for either speed or distance.
 */
public class ShooterSpeeds {

    private ArrayList<Double> distanceList = new ArrayList<Double>();
    private ArrayList<Integer> speedList = new ArrayList<Integer>();

    public ShooterSpeeds() {

    }

    /**
     * @param distance the distance to the target.
     * @param speed    the desired speed for at the distance.
     */
    public void addSetpoint(double distance, int speed) {
        distanceList.add(distance);
        speedList.add(speed);
    }

    /**
     * @param distance the distance to the target.
     * @return the desired speed for at the distance.
     */
    public int getShooterSpeed(double distance) {
        int index = 0;
        double smallestDifference = 0;

        for (int i = 0; i < distanceList.size(); i++) {
            double difference = Math.abs(distanceList.get(i) - distance);

            if (difference < smallestDifference) {
                index = i;
                smallestDifference = difference;
            }
        }

        return speedList.get(index);
    }

    /**
     * @param speed the desired speed for at the distance.
     * @return the distance to the target.
     */
    public double getShooterDistance(int speed) {
        int index = 0;
        int smallestDifference = 0;

        for (int i = 0; i < distanceList.size(); i++) {
            int difference = Math.abs(speedList.get(i) - speed);

            if (difference < smallestDifference) {
                index = i;
                smallestDifference = difference;
            }
        }

        return distanceList.get(index);
    }
}