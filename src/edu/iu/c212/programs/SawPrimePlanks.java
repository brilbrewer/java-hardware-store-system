package edu.iu.c212.programs;

import java.util.ArrayList;
import java.util.List;

public class SawPrimePlanks {

    /**
     * this uses sawPlank to saw down a plank and finds a out how many times the new plank length goes into the long plank length
     * @param longPlankLength int
     * @return list of prime number integers where all the values are the same and they add up to the original plank length
     */
    public static List<Integer> getPlankLengths(int longPlankLength) {
        List<Integer> result = new ArrayList<>();
        int sawedPlank = sawPlank(longPlankLength);
        int quantity = longPlankLength / sawedPlank;
        int temp = 0;
        while (temp < quantity) {
            result.add(sawedPlank);
            temp++;
        }
        return result;
    }

    /**
     * this saws the planks down into smaller and smaller planks until the plank length is a prime number
     * @param plankLength int
     * @return int prime number plank length
     */
    public static int sawPlank(int plankLength) {

        if (isPrime(plankLength)) {
            return plankLength;
        }
        else {
            return sawPlank(plankLength / smallestDivisor(plankLength));
        }
    }

    /**
     * this checks if a number is prime or not
     * @param n int
     * @return boolean value
     */
    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * this finds the smallest divisor of any given integer
     * @param n int
     * @return int smallest divisor
     */
    private static int smallestDivisor(int n) {
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                return i;
            }
        }
        return n;
    }
}
