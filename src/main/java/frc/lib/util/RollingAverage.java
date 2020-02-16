
package frc.lib.util;

import java.util.ArrayList;

/**
 * Handles getting the average of a rolling number.
 */
public class RollingAverage {

    ArrayList<Double> numberList = new ArrayList<Double>();

    private int mSize;

    /**
     * @param size the max size of the rolling average number list.
     */
    public RollingAverage(int size) {
        mSize = size;
    }

    /**
     * @param number the number to add to the list.
     */
    public void add(double number) {
        numberList.add(number);

        if (numberList.size() > mSize) {
            numberList.remove(0);
        }
    }

    /**
     * @return the average of all the numbers in the stored rolling average.
     */
    public double getAverage() {
        double total = 0;

        for (double number : numberList) {
            total += number;
        }

        return total / numberList.size();
    }

    /**
     * Clears the stored number list.
     */
    public void clear() {
        numberList.clear();
    }
}